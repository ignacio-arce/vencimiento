package dao;

import java.util.List;

import model.Vencimiento;

public interface VencimientoDao {
	public static final String archivo = "archivoVencimientos.txt";
	
	public List<Vencimiento> getListaVencimientos();
	public Vencimiento getVencimiento(int n);
	public void guardarVencimientos(Vencimiento Vencimiento);
	public void borrarVencimiento(Vencimiento Vencimiento);
}
