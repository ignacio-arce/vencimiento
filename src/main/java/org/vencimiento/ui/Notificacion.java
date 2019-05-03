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
import static java.awt.TrayIcon.MessageType.ERROR;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lavoratoriov
 */
public class Notificacion {
    private final TrayIcon tryIcon;
    private static final Image img = Toolkit.getDefaultToolkit().getImage("OptionPane.informationIcon");
    private static final PopupMenu menu = new PopupMenu();
    private static final MenuItem salir = new MenuItem("Salir");
    private static final MenuItem abrir = new MenuItem("Abrir");
    
    public Notificacion() {
            menu.add(salir);
            menu.add(abrir);
            this.tryIcon = new TrayIcon(img,"Vencimiento app",menu);
            try {
                SystemTray.getSystemTray().add(tryIcon);
                Thread.sleep(5000);
            } catch (AWTException | InterruptedException ex) {
                Logger.getLogger(Notificacion.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        }
        tryIcon.displayMessage(caption, message, t);
    }
}
