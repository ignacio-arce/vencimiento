
package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Vencimiento implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate fechaVencimiento;
	private int monto;
	private boolean pagado;
	private String servicio;
	
	public Vencimiento() {
		
	}
	
	public Vencimiento(LocalDate fechaVencimiento, int monto, boolean pagado, String servicio) {
		this.fechaVencimiento = fechaVencimiento;
		this.monto = monto;
		this.pagado = pagado;
		this.servicio = servicio;
	}
	
	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public LocalDate getFechavencimiento() {
		return fechaVencimiento;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}
	
	public int getMonto() {
		return monto;
	}
	
	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}
	
	public boolean isPagado() {
		return pagado;
	}
	
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	
	public String getServicio() {
		return servicio;
	}

}