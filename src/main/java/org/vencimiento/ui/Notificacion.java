/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionListener;

/**
 *
 * @author lavoratoriov
 */
public class Notificacion {
	private static TrayIcon tryIcon;
	private static final Image img = java.awt.Toolkit.getDefaultToolkit().getImage("images/166.gif");
	private PopupMenu menu;
	private MenuItem menuItemSalir;
	private MenuItem menuItemAbrir;

	protected Notificacion() {
		crearMenuNotificacion();
		menu.add(menuItemSalir);
		menu.add(menuItemAbrir);
		tryIcon = new TrayIcon(img, "Vencimiento app", menu);
		tryIcon.setImageAutoSize(true);

		try {
			SystemTray.getSystemTray().add(tryIcon);
			Thread.sleep(1000);
		} catch (InterruptedException | AWTException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	private void crearMenuNotificacion() {
		this.menu = new PopupMenu();
		this.menuItemSalir = new MenuItem("Salir");
		this.menuItemAbrir = new MenuItem("Abrir");
	}

	public void agregarListeners(ActionListener action) {
		menu.addActionListener(action);
	}

	public void mostrarNotificacion(String caption, String message, MessageType type) {
		TrayIcon.MessageType t = TrayIcon.MessageType.NONE;
		switch (type) {
		case ERROR: {
			t = TrayIcon.MessageType.ERROR;
			break;
		}
		case WARNING: {
			t = TrayIcon.MessageType.WARNING;
			break;
		}
		case INFO: {
			t = TrayIcon.MessageType.INFO;
			break;
		}
		case NONE:
			break;
		default:
			break;
		}
		tryIcon.displayMessage(caption, message, t);
	}

}
