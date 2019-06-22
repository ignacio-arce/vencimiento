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
	private final String[] COLUMN_NAMES = { "Insumo", "Fecha vto.", "Lote", "Estado"};
	private VencimientoDao vencimientoDao;

	private List<Vencimiento> listaVencimientos = new ArrayList<>();

	public VencimientoTableModel(VencimientoDao vencimientoDao) {
		this.vencimientoDao = vencimientoDao;
	}

	public void updateModel() {
		this.listaVencimientos = vencimientoDao.getListaVencimientos();
	}

	

	@Override
	public String getColumnName(int i) {
		return COLUMN_NAMES[i];
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return listaVencimientos.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Vencimiento elegido = listaVencimientos.get(row);
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
	
	private void printDebugData() {
	      int numRows = getRowCount();
	      int numCols = getColumnCount();
	      
	      for (int i = 0; i < numCols; i++) {
	        System.out.print("    columna " + i + ":");
	        System.out.println();
	        for (int j = 0; j < numRows; j++) {
	        	System.out.println(getValueAt(j,i));
	        }
	        System.out.println();
	      }
	      System.out.println("--------------------------");
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
