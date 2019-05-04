package controller;

import javax.swing.SwingUtilities;

import dao.VencimientoDao;
import dao.VencimientoDaoImpl;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ui.View;

public class Main {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final ScheduledExecutorService t = Executors.newSingleThreadScheduledExecutor();
				VencimientoDao vencimientoDao = new VencimientoDaoImpl();
				View view = new View();
				Controller controller = new Controller(vencimientoDao, view);
				view.setVisible(true);
				controller.init();
				t.scheduleAtFixedRate(controller, 0, 1, TimeUnit.MINUTES);
			}
		});

	}
}