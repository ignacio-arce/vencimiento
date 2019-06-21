package controller;

import javax.swing.SwingUtilities;

import dao.SQLiteDAOManager;
import model.VencimientoTableModel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ui.View;

public class Main {
	public static void main(String args[]) {
		SQLiteDAOManager sqlManager = new SQLiteDAOManager();
		VencimientoTableModel vtoTableModel = new VencimientoTableModel(sqlManager.getVencimientoDao());
		vtoTableModel.updateModel();
		View view = new View(vtoTableModel);
        Controller controller = new Controller(sqlManager.getVencimientoDao(), view);
		SwingUtilities.invokeLater(() -> {
                    
                    
                    
                    controller.init();
                    
                    view.setVisible(true);
                    if (java.awt.SystemTray.isSupported() && !System.getProperty("os.name").equalsIgnoreCase("linux")) {
                        final ScheduledExecutorService t = Executors.newSingleThreadScheduledExecutor();
                        t.scheduleAtFixedRate(controller, 0, 8, TimeUnit.HOURS);
                    }
                });

    }

}