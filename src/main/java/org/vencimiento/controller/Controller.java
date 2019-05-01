package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.VencimientoDao;
import model.Vencimiento;
import ui.View;

/**
 * @author acerNacho
 */
public class Controller {

    private View view;
    private VencimientoDao vencimientoDao;

    protected Controller(VencimientoDao vencimientoDao, View view) {
        this.view = view;
        this.vencimientoDao = vencimientoDao;
    }

    protected void run() {
        view.agregarListenersMenu(new MenuListener());
        view.agregarListenersPanelAgregarVencimiento(new BotonCargarDatosListener());
        view.agregarListenersTextoFecha(new TextoFechaListener());
        cargarDatosEnTabla(vencimientoDao.getListaVencimientos());
    }

    private class BotonCargarDatosListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().getClass() == JButton.class) {
                if (view.getFecha() != null) {
                    Vencimiento vencimiento = new Vencimiento(view.getFecha(), view.getTipo(), view.getLote());
                    vencimientoDao.guardarVencimientos(vencimiento);
                    cargarDatosEnTabla(vencimientoDao.getListaVencimientos());
                    view.changePanel("scrollPane");
                }
            }
        }
    }

    private class TextoFechaListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            String texto = ((JTextField) e.getSource()).getText();
            if (texto.equals("DD") || texto.equals("MM") || texto.equals("YYYY")) {
                ((JTextField) e.getSource()).setText("");
            }
        }

        @Override
        public void focusLost(FocusEvent e) {

        }

    }

    private class MenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Agregar":
                    view.changePanel("panelVencimiento");
                    break;
                case "Quitar":
                    if (view.getTable().getSelectedRow() > -1) {
                        if (JOptionPane.showConfirmDialog(view, "Confirmacion",
                                "Desea borrar el item seleccionado?", 0) == 0) { // Si
                            int filasElegidas[] = view.getTable().getSelectedRows();
                            for (int i = 0; i < filasElegidas.length; i++) {
                                System.out.println(filasElegidas.length);
                                Object o[] = armarFilaElegida(view.getTableModel(), filasElegidas[i]);
                                int fecha[] = vencimientoDao.toInteger(((String) o[1]).split("-"));

                                Vencimiento vencimientoElegido = new Vencimiento(LocalDate.of(fecha[0], fecha[1], fecha[2]), (String) o[0], (String) o[2]);
 
                                for (Vencimiento v : vencimientoDao.getListaVencimientos()) {
                                    if (vencimientoElegido.compareTo(v) == 0) {
                                        vencimientoDao.borrarVencimiento(v);
                                        break;
                                    }

                                }
                            }

                            cargarDatosEnTabla(vencimientoDao.getListaVencimientos());
                        }
                    } else {
                        JOptionPane.showMessageDialog(view, "No se han seleccionado items", "Error", 0);
                    }
                    break;
                default:
                    break;
            }
        }

        private Object[] armarFilaElegida(DefaultTableModel t, int filaElegida) {
            Object[] o = new String[t.getColumnCount() - 1]; // -1 avoid the status of expiry date
            for (int j = 0; j < t.getRowCount(); j++) {
                for (int i = 0; i < t.getColumnCount() - 1; i++) {
                    if (j == filaElegida) {
                        o[i] = t.getValueAt(j, i);
                    }
                }
            }
            return o;
        }

    }

    /*
     * Carga los datos a la tabla
     */
    public void cargarDatosEnTabla(ArrayList<Vencimiento> listaVencimientos) {
        // Reinicia el table model
        view.getTableModel().setRowCount(0);

        // Ordenar por vencimiento próximo
        Collections.sort(listaVencimientos);
        for (Vencimiento v : listaVencimientos) {
            Object data[] = {v.getTipo(), v.getFechaVencimiento().toString(), v.getLote(), isExpired(v.getFechaVencimiento())};
            view.getTableModel().addRow(data);
        }
    }

    /**
     * Controla si los items estan vencidos
     */
    private String isExpired(LocalDate fechaVencimiento) {
        if (fechaVencimiento.isBefore(LocalDate.now())) {
            return "Vencido";
        } else {
            return "En fecha";
        }

    }

}
