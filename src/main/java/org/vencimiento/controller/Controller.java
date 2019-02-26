package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

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
		if (vencimientoDao.getListaVencimientos() != null) {
			cargarDatosEnTabla();
		}
	}

	private class BotonCargarDatosListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (view.getFecha() != null) {
				Vencimiento vencimiento = new Vencimiento(view.getFecha(), view.getMonto(), false,
						view.getTipo());
				vencimientoDao.guardarVencimientos(vencimiento);
				view.limpiarContenedor();
				cargarDatosEnTabla();
				view.crearPanelTabla();
				view.repaint();
				view.validate();
			}

		}
	}

	private class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Agregar":
				view.limpiarContenedor();
				view.crearPanelAgregarVencimiento();
				view.agregerListenersPanelAgregarVencimiento(new BotonCargarDatosListener());
				break;
			case "Quitar":
				if (JOptionPane.showConfirmDialog(view, "Confirmacion",
						"Desea borrar el item seleccionado?", 0) == 0) { // Si
					vencimientoDao.borrarVencimiento(vencimientoDao.getVencimiento(view.getTable().getSelectedRow()));
					view.clearData();
					cargarDatosEnTabla();
					view.limpiarContenedor();
					view.crearPanelTabla();
					view.repaint();
					view.revalidate();
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
		int j = 0;
		for (Vencimiento v : vencimientoDao.getListaVencimientos()) {
			view.getData()[j][0] = v.getServicio();
			view.getData()[j][1] = v.getFechavencimiento();
			view.getData()[j][2] = (Integer) v.getMonto();
			view.getData()[j][3] = v.isPagado();
			j++;
		}
	}

}