package controller;



/*import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.*;*/
import java.util.*;

import dao.VencimientoDao;
import dao.VencimientoDaoImpl;
import model.Vencimiento;
import ui.View;

/**
 * @author acerNacho
 */


public class Controller {
	private static View vto_view;
	private static VencimientoDao vencimientoDao;
	
	public Controller(List<Vencimiento> listaVencimientos, View vto_view) {
		Controller.vto_view = vto_view;
		vencimientoDao = new VencimientoDaoImpl(listaVencimientos);
	}
	
	public void run() {
		vencimientoDao.getListaVencimientos();
		vto_view.cargarDatosEnTabla();
	}
	
	/*public static void guardarVencimiento() {

		OutputStream ops = null;
		ObjectOutputStream objOps = null;
		try {
			ops = new FileOutputStream("archivoVencimientos.txt");
			objOps = new ObjectOutputStream(ops);
			objOps.writeObject(vto_model);
			objOps.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objOps != null)
					objOps.close();
			} catch (Exception ex) {

			}
		}

	}*/

	/*public void mostrarVencimiento() {
		for (int j = 0; j < vto_model.size(); j++) {
			for (Vencimiento v : vto_model) {
				vto_view.getData()[j][0] = v.getServicio();
				vto_view.getData()[j][1] = v.getFechavencimiento();
				vto_view.getData()[j][2] = (Integer) v.getMonto();
				vto_view.getData()[j][3] = v.isPagado();
			}
		}
		vto_view.getTable().repaint();
	}*/
	/*
	public static List<Vencimiento> getListaVencimientos() {

		InputStream fileIs = null;
		ObjectInputStream objIs = null;
		List<Vencimiento> ven = null;
		try {
			fileIs = new FileInputStream("archivoVencimientos.txt");
			objIs = new ObjectInputStream(fileIs);
			ven = (ArrayList<Vencimiento>) objIs.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objIs != null) {
					objIs.close();
				}
			} catch (Exception ex) {

			}
		}
		return ven;
	}*/

}