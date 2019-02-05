import poo.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Vencimiento_GUI extends JFrame {

	Vencimiento_GUI() {
		
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		cp.add( new JLabel("Vencimientos",SwingConstants.CENTER),BorderLayout.NORTH);

		String[] columnNames = {"Tipo",
                                "Fecha vto.",
                                "Importe",
                                "Estado"};

        Object[][] data = {
	    {"Kathy", "Smith",
	     "Snowboarding", new Integer(5), new Boolean(false)},
	    {"John", "Doe",
	     "Rowing", new Integer(3), new Boolean(true)},
	    {"Sue", "Black",
	     "Knitting", new Integer(2), new Boolean(false)},
	    {"Jane", "White",
	     "Speed reading", new Integer(20), new Boolean(true)},
	    {"Joe", "Brown",
	     "Pool", new Integer(10), new Boolean(false)}
        };

		
		final JTable table = new JTable(data, columnNames);
		
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
