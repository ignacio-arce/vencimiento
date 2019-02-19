package ui;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.time.DateTimeException;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.text.NumberFormatter;


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

	private Object data[][] = new Object[CELL_SIZE][COLUMN_SIZE];

	private JTable table;
	private JScrollPane scrollPane;
	private final JMenuBar menu = new JMenuBar();
	private final JMenu menuOpciones = new JMenu("Opciones");
	private final JMenu menuAcercaDe = new JMenu("Acerca de");;
	private final JMenuItem menuOpcionesAgregar = new JMenuItem("Agregar");
	private final JMenuItem menuOpcionesQuitar = new JMenuItem("Quitar");
	private final Container cp = getContentPane();
	private JTextField dia;
	private JTextField mes;
	private JTextField anio;
	private JFormattedTextField monto;
	private JTextField tipo;
	private JButton botonCargar;

	/**
	 * Initialise the JFrame containing the various JSwing components
	 * responsible of the Vencimiento System.
	 */
	public View() {
		crearPanelTabla();

		pack();
		setSize(400, 200);
		setTitle("Vencimiento");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	

	public void crearPanelTabla() {
		cp.setLayout(new BorderLayout());

		menuOpciones.add(menuOpcionesAgregar);
		menuOpciones.add(menuOpcionesQuitar);
		menu.add(menuOpciones);
		menu.add(menuAcercaDe);

		cp.add(menu, BorderLayout.NORTH);

		// Tabla
		table = new JTable(getData(), COLUMN_NAMES);
		scrollPane = new JScrollPane(table);

		cp.add(scrollPane, BorderLayout.CENTER);
	}

	public void crearPanelAgregarVencimiento() {
		limpiarContenedor();

		JPanel panel = new JPanel();
		JPanel panelFecha = new JPanel();

		panel.setLayout(new GridLayout(4, 2, 15, 9));
		panelFecha.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 0));

		cp.setLayout(new FlowLayout(FlowLayout.LEADING, 40, 15));

		dia = new JTextField("DD", 3);
		mes = new JTextField("MM", 3);
		anio = new JTextField("YYYY", 3);

		panelFecha.add(dia);
		panelFecha.add(new JLabel("-"));
		panelFecha.add(mes);
		panelFecha.add(new JLabel("-"));
		panelFecha.add(anio);

		panel.add(new JLabel("Fecha:", SwingConstants.RIGHT));
		panel.add(panelFecha);

		panel.add(new JLabel("Monto:", SwingConstants.RIGHT));

		NumberFormat intFormat = NumberFormat.getIntegerInstance();

		NumberFormatter numberFormatter = new NumberFormatter(intFormat);
		numberFormatter.setValueClass(Long.class); // optional, ensures
								// you will
								// always get a
								// long value
		numberFormatter.setAllowsInvalid(false); // this is the key!!
		numberFormatter.setMinimum(0l); // Optional

		monto = new JFormattedTextField(numberFormatter);
		monto.setBounds(0, 0, 8, getBounds().height);

		panel.add(monto);

		panel.add(new JLabel("Tipo:", SwingConstants.RIGHT));
		tipo = new JTextField(8);
		panel.add(tipo);

		panel.add(new JPanel());
		botonCargar = new JButton("Cargar datos");

		panel.add(botonCargar);
		cp.add(panel);
		cp.validate();
		cp.repaint();
	}

	public void limpiarContenedor() {
		cp.removeAll();
	}

	public void agregerListenersPanelAgregarVencimiento(ActionListener action) {
		botonCargar.addActionListener(action);
	}

	public void agregarListeners(ActionListener action) {
		menuAcercaDe.addActionListener(action);
		menuOpcionesAgregar.addActionListener(action);
		menuOpcionesQuitar.addActionListener(action);
	}

	public String getTipo() {
		// TODO Auto-generated method stub
		return tipo.getText();
	}

	public JTable getTable() {
		return table;
	}

	public Object[][] getData() {
		return data;
	}
	
	public void clearData() {
		this.data = new Object[CELL_SIZE][COLUMN_SIZE];
	}


	public LocalDate getFecha() {
		try {
			int fecha[] = new int[3];

			fecha[0] = Integer.parseInt(dia.getText());
			fecha[1] = Integer.parseInt(mes.getText());
			fecha[2] = Integer.parseInt(anio.getText());
			return LocalDate.of(fecha[2], fecha[1], fecha[0]);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Formato de fecha invalido", "Error",
					JOptionPane.WARNING_MESSAGE);
			System.err.println("Formato de fecha invalido");
			// e.printStackTrace();
		} catch (DateTimeException e) {
			JOptionPane.showMessageDialog(this, "Fecha no valida", "Error", JOptionPane.ERROR_MESSAGE);
			System.err.println("Fecha no valida");
		} catch (Exception e) {
			// TODO agregar error correspondiente a la fecha
			// invalida
			e.printStackTrace();
		}
		return null;
	}

	public int getMonto() {
		try {
			return Integer.valueOf(monto.getText().replace(".", ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return 0;
	}

}