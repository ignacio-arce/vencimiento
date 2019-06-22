package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.VencimientoDao;
import java.awt.TrayIcon;
import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Vencimiento;
import model.VencimientoTableModel;
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
	}

	@Override
	public void run() {
		if (view.getIconoNotificacion() != null) {
			view.agregarListenersNotificacion(new IconoNotificacionListener());
			notificarVencimientos();
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
				view.changePanel("scrollPane");
				view.getTableModel().updateModel();
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
				java.util.List<Vencimiento> listaVencimientos = vencimientoDao.getListaVencimientos();
				int filasElegidas[] = (view.getTable().getSelectedRows().length <= listaVencimientos.size()) ? view.getTable().getSelectedRows() : null;
				for (int i = filasElegidas.length-1; i>=0; i--) { // TODO mejorar esto de arriba
					vencimientoDao.borrarVencimiento(vencimientoDao.getListaVencimientos().get(filasElegidas[i]));
				}
				view.getTableModel().updateModel();
				view.getTable().clearSelection();
				view.repaint();
				break;
			case "Buscar":
				
				break;
			case "Autor": {

				JOptionPane.showMessageDialog(null,
						"<html>Creado por <a href=\"http://github.com/acerNacho\">Ignacio Arce</a> para " + CLIENTE
								+ "</html>");
				if (System.getProperty("os.name").toLowerCase().contains("windows")) {
					try {
						Runtime.getRuntime().exec(String.format("cmd.exe /c start http://github.com/acerNacho"));

					} catch (IOException ex) {
						Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
				break;
			}
			default:
				System.out.println("Error desconocido");
				break;
			}
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
