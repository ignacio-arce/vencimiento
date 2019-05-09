package controller;

import javax.swing.SwingUtilities;

import dao.SQLiteDAOManager;
import dao.VencimientoDao;
import dao.VencimientoDaoImpl;

import java.sql.SQLException;
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
				SQLiteDAOManager sqlManager = new SQLiteDAOManager();
				
				View view = new View();
				Controller controller = new Controller(sqlManager.getVencimientoDao(), view);
				
				view.setVisible(true);
				controller.init();
				
				t.scheduleAtFixedRate(controller, 0, 8, TimeUnit.HOURS);
			}
		});

	}
}