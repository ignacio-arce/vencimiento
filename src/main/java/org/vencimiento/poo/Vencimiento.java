package org.vencimiento.poo;

import java.util.*;
import java.io.Serializable;

public class Vencimiento implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date fechaVencimiento;
	private int monto;
	private boolean pagado;
	private String servicio;
	
	public Vencimiento(Date fechaVencimiento, int monto, boolean pagado, String servicio) {
		this.fechaVencimiento = fechaVencimiento;
		this.monto = monto;
		this.pagado = pagado;
		this.servicio = servicio;
	}
	
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public Date getFechavencimiento() {
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
