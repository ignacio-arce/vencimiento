import vencimiento.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Vencimiento_GUI extends JFrame {

	Vencimiento_GUI() {
		
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		cp.add( new JLabel("Vencimientos",SwingConstants.CENTER),BorderLayout.NORTH);


		
		final String[] COLUMN_NAMES = {"Tipo",
                                "Fecha vto.",
                                "Importe",
                                "Estado"};
        final int COLUMN_SIZE = COLUMN_NAMES.length;
        

        ArchivoDeObjetos ado = new ArchivoDeObjetos();
        Object[][] data;

        for (int i = 0; i< ado.leerVencimiento().size();i++) {
        	for (int j = 0; j<COLUMN_SIZE; j++) {
        		data[i][j] = ado.leerVencimiento().get(j);
        	}
        }
        

        // Fecha

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date today = new Date();
		Date todayWithZeroTime = null;
		try {
			todayWithZeroTime = formatter.parse(formatter.format(today));
		} catch (Exception e) {

		}

		// Tabla

		
		final JTable table = new JTable(data, COLUMN_NAMES);
		
        JScrollPane scrollPane = new JScrollPane(table);
		cp.add(scrollPane, BorderLayout.CENTER);
		pack();
		setSize(300,100);
		setTitle("Vencimiento");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				new Vencimiento_GUI();
			}
		});
	}
}
