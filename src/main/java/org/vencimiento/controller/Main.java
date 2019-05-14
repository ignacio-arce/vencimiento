package controller;

import javax.swing.SwingUtilities;

import dao.SQLiteDAOManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ui.View;

public class Main {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(() -> {
                    SQLiteDAOManager sqlManager = new SQLiteDAOManager();
                    
                    View view = new View();
                    Controller controller = new Controller(sqlManager.getVencimientoDao(), view);
                    controller.init();
                    view.setVisible(true);
                    if (java.awt.SystemTray.isSupported()) {
                        final ScheduledExecutorService t = Executors.newSingleThreadScheduledExecutor();
                        t.scheduleAtFixedRate(controller, 0, 8, TimeUnit.HOURS);
                    }
                });

    }

}