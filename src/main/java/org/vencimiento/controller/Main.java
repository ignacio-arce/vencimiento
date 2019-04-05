package controller;

import javax.swing.SwingUtilities;

import dao.VencimientoDao;
import dao.VencimientoDaoImpl;
import ui.View;

/**
 * Main method for creation of MVC 
 * create model of vencimiento, controller and then run the controller
 * @author acerNacho
 * @version 1.0
 */

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
