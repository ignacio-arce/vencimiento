package controller;

import javax.swing.SwingUtilities;

import dao.VencimientoDao;
import dao.VencimientoDaoImpl;
import ui.View;

public class Main {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				VencimientoDao vencimientoDao = new VencimientoDaoImpl();
				
				View view = new View();
				Controller controller = new Controller(vencimientoDao,view);
				
				view.setVisible(true);
				controller.run();
			}
		});
	}
}