package vencimiento.poo;

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
 


public class ArchivoDeObjetos {
	private List<Vencimiento> vencimiento = new ArrayList<>();

	ArchivoDeObjetos() {

	}

    public static void guardarVencimiento(List<Vencimiento> ven){
         
        OutputStream ops = null;
        ObjectOutputStream objOps = null;
        try {
            ops = new FileOutputStream("archivoVencimientos.txt");
            objOps = new ObjectOutputStream(ops);
            objOps.writeObject(ven);
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

    public static void main(String a[]){
		
		ArchivoDeObjetos ado = new ArchivoDeObjetos();
		
		Vencimiento v1 = new Vencimiento(todayWithZeroTime,2200,true,"Seguro del auto");
		Vencimiento v2 = new Vencimiento(todayWithZeroTime,2200,true,"Seguro del auto");
		ven.add(v1);
		ven.add(v2);
		ado.guardarObjeto(ven);
		ado.mostrarObjeto();
    }
}