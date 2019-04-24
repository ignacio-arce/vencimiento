package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.VencimientoDao;
import model.Vencimiento;
import ui.View;

/**
 * @author acerNacho
 */

public class Controller {
	private View view;
	private VencimientoDao vencimientoDao;

	protected Controller(VencimientoDao vencimientoDao, View view) {
		this.view = view;
		this.vencimientoDao = vencimientoDao;
	}

	protected void run() {
		view.agregarListeners(new MenuListener());
		cargarDatosEnTabla();
	}

	private class BotonCargarDatosListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().getClass() == JButton.class) {
				if (view.getFecha() != null) {
					Vencimiento vencimiento = new Vencimiento(view.getFecha(), view.getTipo(), view.getLote());
					vencimientoDao.guardarVencimientos(vencimiento);
					cargarDatosEnTabla();
					view.showPanelTabla();
					view.repaint();
					view.validate();
				}
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

	private class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Agregar":
				view.showPanelAgregarVencimiento();
				view.agregarListenersPanelAgregarVencimiento(new BotonCargarDatosListener());
				view.agregarListenersTextoFecha(new TextoFechaListener());
				break;
			case "Quitar":
				if (view.getTable().getSelectedRow() > 0) {
					if (JOptionPane.showConfirmDialog(view, "Confirmacion",
							"Desea borrar el item seleccionado?", 0) == 0) { // Si
						vencimientoDao.borrarVencimiento(vencimientoDao.getVencimiento(view.getTable().getSelectedRow()));
						
						cargarDatosEnTabla();
						view.showPanelTabla();
						/*view.repaint();*/
						view.revalidate();
					}
				} else {
					JOptionPane.showMessageDialog(view, "No se han seleccionado items", "Error", 0);
				}
				break;
				default:
					break;
			}
		}
		
	}
	
	/*
	 * Carga los datos a la tabla
	 */
	
	public void cargarDatosEnTabla() {
		view.getTableModel().setRowCount(0);
		for (Vencimiento v : vencimientoDao.getListaVencimientos()) {
			Object data[] = { v.getTipo(), v.getFechaVencimiento() , v.getLote() , isExpired(v.getFechaVencimiento()) };
			view.getTableModel().addRow(data);
		}
	}

	
	/**
	 * Controla si los items estan vencidos
	 */
	
	private String isExpired(LocalDate fechaVencimiento) {
		if (fechaVencimiento.isBefore(LocalDate.now())) {
			return "Vencido";
		} else {
			return "En fecha";
		}
		
	}

}