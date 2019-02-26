package dao;

import java.io.File;
import java.util.List;

import model.Vencimiento;

public interface VencimientoDao {
	public static final File archivo = new File("archivoVencimientos.txt");
	
	public List<Vencimiento> getListaVencimientos();
	public Vencimiento getVencimiento(int n);
	public void guardarVencimientos(Vencimiento vencimiento);
	public void borrarVencimiento(Vencimiento vencimiento);
}
