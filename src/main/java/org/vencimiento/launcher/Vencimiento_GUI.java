package org.vencimiento.launcher;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import org.vencimiento.poo.Vencimiento;
import org.vencimiento.util.ArchivoDeObjetos;
/**
* Class responsible for launching the Vencimiento System.
* @author acerNacho
* @version 0.1b
*/
public class Vencimiento_GUI extends JFrame {

	
	private static final long serialVersionUID = 1L;
	/**
	 * Initialise the JFrame containing the various JSwing components responsible of the Vencimiento System.
	 */
	public Vencimiento_GUI() {

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		cp.add(new JLabel("Vencimientos", SwingConstants.CENTER), BorderLayout.NORTH);

		final String[] COLUMN_NAMES = { "Servicio", "Fecha vto.", "Importe", "Estado" };
		final int COLUMN_SIZE = COLUMN_NAMES.length;

		// Fecha

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date today = new Date();
		// Date todayWithZeroTime = null;
		try {
			Date todayWithZeroTime = formatter.parse(formatter.format(today));
			/* Provisorio */

			Vencimiento v1 = new Vencimiento(todayWithZeroTime, 2200, true, "Seguro del auto");
			Vencimiento v2 = new Vencimiento(todayWithZeroTime, 2200, true, "Seguro del auto");
			ArchivoDeObjetos.getVencimiento().add(v1);
			ArchivoDeObjetos.getVencimiento().add(v2);

			ArchivoDeObjetos.guardarVencimiento();
		} catch (Exception e) {

		}

		// Cargar datos

		
		Object data[][] = new Object[30][COLUMN_SIZE];
		for (int j = 0; j < ArchivoDeObjetos.getVencimiento().size(); j++) {
			for (Vencimiento v : ArchivoDeObjetos.leerVencimiento()) {
					data[j][0] = v.getServicio();
					data[j][1] = v.getFechavencimiento();
					data[j][2] = (Integer) v.getMonto();
					data[j][3] = v.isPagado();
			}
		}

		// Tabla

		final JTable table = new JTable(data, COLUMN_NAMES);

		JScrollPane scrollPane = new JScrollPane(table);
		cp.add(scrollPane, BorderLayout.CENTER);
		pack();
		setSize(300, 100);
		setTitle("Vencimiento");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Vencimiento_GUI();
			}
		});
	}
}
