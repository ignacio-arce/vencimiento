package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.VencimientoDao;
import java.awt.TrayIcon;
import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Vencimiento;
import ui.View;

/**
 * @author acerNacho
 */
public class Controller extends TimerTask {

	private static final int CHECK_DAYS_AFTER_EXPIRY = 15;
        private static final String CLIENTE = "BioEliga";

	private View view;
	private VencimientoDao vencimientoDao;
	private int cuantosHayVencidos = 0;

	protected Controller(VencimientoDao vencimientoDao, View view) {
		this.view = view;
		this.vencimientoDao = vencimientoDao;
	}

	protected void init() {
		view.agregarListenersMenu(new MainMenuListener());
		view.agregarListenersPanelAgregarVencimiento(new BotonCargarDatosListener());
		view.agregarListenersTextoFecha(new TextoFechaListener());
		cargarDatosEnTabla(vencimientoDao.getListaVencimientos());

	}

	@Override
	public void run() {
		if (view.getIconoNotificacion() != null) {
			view.agregarListenersNotificacion(new IconoNotificacionListener());
			notificarVencimientos();
		} else {
			view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}

	private class IconoNotificacionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Abrir":
				view.setVisible(true);
				break;
			case "Salir":
				System.exit(0);
				break;
			default:
				System.out.println("Error desconocido");
				break;
			}
		}
	}

	private class BotonCargarDatosListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (view.getFecha() != null) {
				Vencimiento vencimiento = new Vencimiento(view.getFecha(), view.getTipo(), view.getLote());
				vencimientoDao.guardarVencimientos(vencimiento);
				cargarDatosEnTabla(vencimientoDao.getListaVencimientos());
				view.changePanel("scrollPane");
			}

		}
	}

	private class TextoFechaListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			String texto = ((JTextField) e.getSource()).getText();
			if (texto.equals("DD") || texto.equals("MM") || texto.equals("YYYY")) {
				((JTextField) e.getSource()).setText("");
			}
		}

		@Override
		public void focusLost(FocusEvent e) {

		}

	}

	private class MainMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Agregar":
				view.changePanel("panelVencimiento");
				break;
			case "Quitar":
				if (view.getTable().getSelectedRow() > -1) {
					if (JOptionPane.showConfirmDialog(view, "Confirmacion", "Desea borrar el item seleccionado?",
							0) == 0) { // Si
						int filasElegidas[] = view.getTable().getSelectedRows();
						for (int i = 0; i < filasElegidas.length; i++) {
							Object o[] = armarFilaElegida(view.getTableModel(), filasElegidas[i]);

							Vencimiento vencimientoElegido = new Vencimiento(((String) o[1]).split("-"), (String) o[0],
									(String) o[2]);

							for (Vencimiento v : vencimientoDao.getListaVencimientos()) {
								if (vencimientoElegido.compareTo(v) == 0) {
									vencimientoDao.borrarVencimiento(v);
									break;
								}
							}
						}

						cargarDatosEnTabla(vencimientoDao.getListaVencimientos());
					}
				} else {
					JOptionPane.showMessageDialog(view, "No se han seleccionado items", "Error", 0);
				}
				break;
			case "Buscar":
				String cadenaBuscada = JOptionPane.showInputDialog("Introduzca el lote/tipo de insumo a buscar");
				ArrayList<Vencimiento> vencimientosCoincidentes = new ArrayList<>();

				vencimientoDao.getListaVencimientos().forEach(v -> {
					if (v.getTipo().contains(cadenaBuscada) || v.getLote().contains(cadenaBuscada)) {
						vencimientosCoincidentes.add(v);
					}
				});

				if (!vencimientosCoincidentes.isEmpty()) {
					cargarDatosEnTabla(vencimientosCoincidentes);
				} else {
					JOptionPane.showMessageDialog(null,
							"No se han encontrado coincidencias para \"" + cadenaBuscada + "\"", "Buscador", 0);
				}

				break;
                        case "Autor":
                        {
                            
                            JOptionPane.showMessageDialog(null, "<html>Creado por <a href=\"http://github.com/acerNacho\">Ignacio Arce</a> para " + CLIENTE + "</html>");
                            try {
                                Runtime.getRuntime().exec(String.format("cmd.exe /c start http://github.com/acerNacho"));
                            } catch (IOException ex) {
                                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        }
			default:
				System.out.println("Error desconocido");
				break;
			}
		}

		private Object[] armarFilaElegida(DefaultTableModel t, int filaElegida) {
			Object[] o = new String[t.getColumnCount() - 1]; // -1 avoid the status of expiry date
			for (int j = 0; j < t.getRowCount(); j++) {
				for (int i = 0; i < t.getColumnCount() - 1; i++) {
					if (j == filaElegida) {
						o[i] = t.getValueAt(j, i);
					}
				}
			}
			return o;
		}

	}

	/*
	 * Carga los datos a la tabla
	 */
	public void cargarDatosEnTabla(List<Vencimiento> list) {
		// Reinicia el table model
		view.getTableModel().setRowCount(0);

		// Ordenar por vencimiento próximo
		Collections.sort(list);

		// Agrega los vencimientos a la tabla
		for (Vencimiento v : list) {
			String estado = isExpired(v.getFechaVencimiento(), CHECK_DAYS_AFTER_EXPIRY);
			Object data[] = { v.getTipo(), v.getFechaVencimiento().toString(), v.getLote(), estado };
			view.getTableModel().addRow(data);
			cuantosHayVencidos = (estado.equals("Vencido")) ? cuantosHayVencidos + 1 : cuantosHayVencidos;
		}

	}

	/**
	 * Controla si los items estan vencidos
	 */
	private String isExpired(LocalDate fechaVencimiento, int diasAntes) {
		if (LocalDate.now().isAfter(fechaVencimiento)) { // (fechaVencimiento, +inf)
			return "Vencido";
		} else if (LocalDate.now().isAfter(fechaVencimiento.minusDays(diasAntes))) { // (fechaVencimiento-diasAntes,
																						// +inf)
			return "Proximo a vencer";
		} else {
			return "En fecha";
		}

	}

	/*
	 * Notifica items vencidos
	 */
	private void notificarVencimientos() {
		if (cuantosHayVencidos > 0 && view.getIconoNotificacion() != null) {
			view.getIconoNotificacion().mostrarNotificacion("Hay " + cuantosHayVencidos + " items vencidos", "Aviso",
					TrayIcon.MessageType.WARNING);
		}

	}

}
