package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.VencimientoDao;

public class VencimientoTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String[] COLUMN_NAMES = { "Insumo", "Fecha vto.", "Lote", "Estado" };
	private VencimientoDao vencimientoDao;

	private List<Vencimiento> listaVencimientos = new ArrayList<>();

	public VencimientoTableModel(VencimientoDao vencimientoDao) {
		this.vencimientoDao = vencimientoDao;
	}

	public void updateModel() {
		listaVencimientos = vencimientoDao.getListaVencimientos();
	}

	@Override
	public String getColumnName(int i) {
		return (i > COLUMN_NAMES.length || i < 0) ? "" : COLUMN_NAMES[i];
	}

	@Override
	public int getColumnCount() {
		return listaVencimientos.size();
	}

	@Override
	public int getRowCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public Object getValueAt(int col, int rows) {
		Vencimiento elegido = listaVencimientos.get(rows);
		switch (col) {
		case 0:
			return elegido.getTipo();
		case 1:
			return elegido.getFechaVencimiento();
		case 2:
			return elegido.getLote();
		case 3:
			return isExpired(elegido.getFechaVencimiento(), 15); // TODO : cambiar los 15 dias!!!!!!!!!!!!!!!!!!
		default:
			return "";
		}
	}

	private String isExpired(LocalDate fechaVencimiento, int diasAntes) {
		if (LocalDate.now().isAfter(fechaVencimiento)) { // (fechaVencimiento, +inf)
			return "Vencido";
		} else if (LocalDate.now().isAfter(fechaVencimiento.minusDays(diasAntes))) { // (fechaVencimiento-diasAntes,
																						// +inf)
			return "Proximo a vencer";
		} else {
			return "En fecha";
		}

	}

}
