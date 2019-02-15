package controller;

import javax.swing.*;
import java.util.*;

import model.Vencimiento;
import ui.View;
import controller.Controller;

public class Main {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				List<Vencimiento> listaVencimientos = new ArrayList<Vencimiento>();
				View view = new View(listaVencimientos);
				
				Controller controller = new Controller(listaVencimientos,view);
				view.setVisible(true);
				controller.run();
			}
		});
	}
}