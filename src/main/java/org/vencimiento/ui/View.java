package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import model.Vencimiento;
/**
 * Class responsible for launching the Vencimiento System.
 * 
 * @author acerNacho
 * @version 0.1b
 */
public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	private final String[] COLUMN_NAMES = { "Servicio", "Fecha vto.", "Importe", "Estado" };
	private final int COLUMN_SIZE = COLUMN_NAMES.length;
	private int CELL_SIZE = 10;

	private JTable table;
	private JScrollPane scrollPane;
	private Object data[][] = new Object[CELL_SIZE][COLUMN_SIZE];
	private List<Vencimiento> listaVencimientos;
	private JMenuBar menu;
	private JMenu menuAgregar, menuQuitar;

	/**
	 * Initialise the JFrame containing the various JSwing components
	 * responsible of the Vencimiento System.
	 */
	public View(List<Vencimiento> listaVencimientos) {
		this.listaVencimientos = listaVencimientos;
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		menu  = new JMenuBar();
		menuAgregar = new JMenu("Agregar");
		menuQuitar = new JMenu("Quitar");
		menu.add(menuAgregar);
		menu.add(menuQuitar);
		menuAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Ok", "Bien", 0);
			}
		});
		
		cp.add(menu, BorderLayout.NORTH);

		// Tabla
		/*
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date today = new Date();
		// Date todayWithZeroTime = null;
		try {
			Date todayWithZeroTime = formatter.parse(formatter.format(today));
			
			listaVencimientos.add(new Vencimiento(todayWithZeroTime, 0, true, "Ninguno"));
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		table = new JTable(getData(), COLUMN_NAMES);
		
		scrollPane = new JScrollPane(table);
		cp.add(scrollPane, BorderLayout.CENTER);
		pack();
		setSize(400, 200);
		setTitle("Vencimiento");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	/*
	 *	Carga los datos a la tabla
	 *  */
	public void cargarDatosEnTabla() {
		for (int j = 0; j < listaVencimientos.size(); j++) {
			for (Vencimiento v : listaVencimientos) {
				data[j][0] = v.getServicio();
				data[j][1] = v.getFechavencimiento();
				data[j][2] = (Integer) v.getMonto();
				data[j][3] = v.isPagado();
			}
		}
	}

	public JTable getTable() {
		return table;
	}
	public Object[][] getData() {
		return data;
	}

}