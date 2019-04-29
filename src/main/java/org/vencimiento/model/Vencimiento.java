
package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Vencimiento implements Serializable, Comparable<Vencimiento>{
	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate fechaVencimiento;
	private String tipo;
	private String lote;
	private int id;
	
	public Vencimiento() {
		
	}
	
	public Vencimiento(LocalDate fechaVencimiento, String tipo, String lote) {
		this.fechaVencimiento = fechaVencimiento;
		this.tipo = tipo;
		this.lote = lote;
	}
	
	public Vencimiento(LocalDate fechaVencimiento, String tipo, String lote, int id) {
		this.fechaVencimiento = fechaVencimiento;
		this.tipo = tipo;
		this.lote = lote;
		this.id = id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	@Override
	public int compareTo(Vencimiento v) {
		if (v.getLote().equals(this.getLote()) && v.getFechaVencimiento().equals(this.getFechaVencimiento()) && v.getTipo().equals(this.getTipo())) {
			return 0;
		} else if (v.getFechaVencimiento().isAfter(this.getFechaVencimiento())) {
			return -1;
		}else {
			return 1;
		}
		
	}

}