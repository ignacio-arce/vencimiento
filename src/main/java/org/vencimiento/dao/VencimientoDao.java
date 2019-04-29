package dao;


import java.util.ArrayList;

import model.Vencimiento;

public interface VencimientoDao {
	
	
	public ArrayList<Vencimiento> getListaVencimientos();
	public Vencimiento getVencimiento(int n);
	public void guardarVencimientos(Vencimiento vencimiento);
	public void borrarVencimiento(Vencimiento vencimiento);
	public int[] toInteger(String[] split);
}
