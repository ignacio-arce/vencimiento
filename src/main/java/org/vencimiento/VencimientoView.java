
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;

/**
* Class responsible for launching the Vencimiento System.
* @author acerNacho
* @version 0.1b
*/
public class VencimientoView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final String[] COLUMN_NAMES = { "Servicio", "Fecha vto.", "Importe", "Estado" };
	private final int COLUMN_SIZE = COLUMN_NAMES.length;
	
	private JTable table;
	private JScrollPane scrollPane;
	private Object data[][] = new Object[30][COLUMN_SIZE];
	private List<VencimientoModel> vto_model;
	
	/**
	 * Initialise the JFrame containing the various JSwing components responsible of the Vencimiento System.
	 */
	public VencimientoView(List<VencimientoModel> vto_model) {
		this.vto_model = vto_model;
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		cp.add(new JLabel("Vencimientos", SwingConstants.CENTER), BorderLayout.NORTH);

		// Tabla
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date today = new Date();
		// Date todayWithZeroTime = null;
		try {
			Date todayWithZeroTime = formatter.parse(formatter.format(today));
			/* Provisorio */
			vto_model.add(new VencimientoModel(todayWithZeroTime, 0, true, "Ninguno"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		cargarDatos();
			
		table = new JTable(data, COLUMN_NAMES);

		scrollPane = new JScrollPane(table);
		
		cp.add(scrollPane, BorderLayout.CENTER);
		pack();
		setSize(400, 200);
		setTitle("Vencimiento");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public void cargarDatos() {
		for (int j = 0; j < vto_model.size(); j++) {
			for (VencimientoModel v : vto_model) {
				data[j][0] = v.getServicio();
				data[j][1] = v.getFechavencimiento();
				data[j][2] = (Integer) v.getMonto();
				data[j][3] = v.isPagado();
			}
		}
	}
	
}