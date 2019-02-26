package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import model.Vencimiento;

public class VencimientoDaoImpl implements VencimientoDao {

	private List<Vencimiento> listaVencimientos;

	public VencimientoDaoImpl() {
		this.listaVencimientos = (listaVencimientos == null && archivo.exists()) ? getListaVencimientos() : listaVencimientos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vencimiento> getListaVencimientos() {
		InputStream fileIs = null;
		ObjectInputStream objIs = null;
		List<Vencimiento> ven = null;
		try {
			fileIs = new FileInputStream(archivo);
			objIs = new ObjectInputStream(fileIs);
			ven = (ArrayList<Vencimiento>) objIs.readObject();
		} catch (FileNotFoundException e) {
			System.err.println("No existe el archivo");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objIs != null) {
					objIs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return ven;
	}

	@Override
	public Vencimiento getVencimiento(int n) {
		return listaVencimientos.get(n);
	}

	@Override
	public void guardarVencimientos(Vencimiento vencimiento) {
		listaVencimientos.add(vencimiento);
		OutputStream ops = null;
		ObjectOutputStream objOps = null;
		try {
			ops = new FileOutputStream(archivo);
			objOps = new ObjectOutputStream(ops);
			objOps.writeObject(listaVencimientos);
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
	}

	@Override
	public void borrarVencimiento(Vencimiento vencimiento) {
		listaVencimientos.remove(vencimiento);
		OutputStream ops = null;
		ObjectOutputStream objOps = null;
		try {
			ops = new FileOutputStream(archivo);
			objOps = new ObjectOutputStream(ops);
			objOps.writeObject(listaVencimientos);
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
	}

}
