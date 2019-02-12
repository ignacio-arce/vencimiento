
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;
import java.text.*;

/**
 * @author acerNacho
 */


public class VencimientoController {
	private List<VencimientoModel> vto_model;
	private VencimientoView vto_view;
	
	
	public VencimientoController(List<VencimientoModel> vto_model, VencimientoView vto_view) {
		this.vto_model = vto_model;
		this.vto_view = vto_view;
		
		//mostrarVencimiento();
	}

    public static void guardarVencimiento(){
         
        OutputStream ops = null;
        ObjectOutputStream objOps = null;
        try {
            ops = new FileOutputStream("archivoVencimientos.txt");
            objOps = new ObjectOutputStream(ops);
            objOps.writeObject(vto_model);
            objOps.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                if(objOps != null) objOps.close();
            } catch (Exception ex){
                 
            }
        }
         
    }
	
	public void mostrarVencimiento() {
		for (int j = 0; j < vto_model.size(); j++) {
			for (VencimientoModel v : vto_model) {
				vto_view.data[j][0] = v.getServicio();
				vto_view.data[j][1] = v.getFechavencimiento();
				vto_view.data[j][2] = (Integer) v.getMonto();
				vto_view.data[j][3] = v.isPagado();
			}
		}
		vto_view.table.repaint();
	}

    /*
    public static List<Vencimiento> leerVencimiento(){
         
        InputStream fileIs = null;
        ObjectInputStream objIs = null;
		List<Vencimiento> ven = null;
        try {
            fileIs = new FileInputStream("archivoVencimientos.txt");
            objIs = new ObjectInputStream(fileIs);
            ven = (ArrayList<Vencimiento>) objIs.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(objIs != null) {
					objIs.close();
				}
            } catch (Exception ex){
                 
            }
        }
        return ven;
    }

	
	public static List<Vencimiento> getVencimiento() {
		return vencimiento;
	}

	
	 * @param vencimiento the vencimiento to set
	 
	public static void setVencimiento(List<Vencimiento> vencimiento) {
		ArchivoDeObjetos.vencimiento = vencimiento;
	}*/
	
}