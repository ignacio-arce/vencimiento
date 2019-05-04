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
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lavoratoriov
 */
public class Notificacion {
	private TrayIcon tryIcon;
	private static final boolean SystemTraySupported = SystemTray.isSupported();
	private Image img;
	private PopupMenu menu;
	private MenuItem menuItemSalir;
	private MenuItem menuItemAbrir;

	public Notificacion() {
		if (Notificacion.SystemTraySupported) {
			crearMenuNotificacion();
			menu.add(menuItemSalir);
			menu.add(menuItemAbrir);
			this.tryIcon = new TrayIcon(img, "Vencimiento app1", menu);
			try {
				SystemTray.getSystemTray().add(tryIcon);
				Thread.sleep(1000);
			} catch (AWTException | InterruptedException ex) {
				Logger.getLogger(Notificacion.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private void crearMenuNotificacion() {
		this.img = Toolkit.getDefaultToolkit().getImage("empty_star.gif");
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
	
	public static boolean isSystemTraySupported() {
		return SystemTraySupported;
	}

}
