package dao;

import java.io.File;
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
	public static final File archivo = crearArchivo("archivoVencimientos.txt");

	public VencimientoDaoImpl() {
		this.listaVencimientos = (listaVencimientos == null) ? getListaVencimientos() : listaVencimientos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vencimiento> getListaVencimientos() {
		InputStream fileIs = null;
		ObjectInputStream objIs = null;
		List<Vencimiento> ven = null;
		try {
			if (archivo.length() != 0) {
				fileIs = new FileInputStream(archivo);
				objIs = new ObjectInputStream(fileIs);
				ven = (ArrayList<Vencimiento>) objIs.readObject();
			} else {
				ven = new ArrayList<Vencimiento>();
				ven.add(new Vencimiento());
				return ven;
			}
		} catch (FileNotFoundException e) {
			try {
				archivo.createNewFile();
			} catch (IOException e1) {
				System.err.println("No se pudo crear el archivo en la ruta especificada");
				e1.printStackTrace();
			}
			/*
			 * JFileChooser fc = new JFileChooser();
			 * fc.setFileSelectionMode(JFileChooser.
			 * FILES_AND_DIRECTORIES); int returnVal =
			 * fc.showOpenDialog(fc); if (returnVal ==
			 * JFileChooser.APPROVE_OPTION) { archivo = new
			 * File(fc.getSelectedFile() + archivo.getName()); try {
			 * archivo.createNewFile(); } catch (IOException e1) {
			 * System.err.
			 * println("No se pudo crear el archivo en la ruta especificada"
			 * ); e1.printStackTrace(); } } else { System.exit(0); }
			 */

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

	private static File crearArchivo(String archivo) {
		File file = new File(archivo);
		if (file.exists()) {
			return file;
		} else {
			try {
				file.createNewFile();
				return file;
			} catch (IOException e1) {
				System.err.println("No se pudo crear el archivo en la ruta especificada");
				e1.printStackTrace();
			}

		}
		return null;
	}

}
