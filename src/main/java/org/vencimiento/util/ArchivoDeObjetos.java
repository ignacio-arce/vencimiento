package org.vencimiento.util;

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

import org.vencimiento.poo.Vencimiento;

/**
 * @author acerNacho
 */


public class ArchivoDeObjetos {
	private static List<Vencimiento> vencimiento = new ArrayList<>();

	private ArchivoDeObjetos() {

	}

    public static void guardarVencimiento(){
         
        OutputStream ops = null;
        ObjectOutputStream objOps = null;
        try {
            ops = new FileOutputStream("archivoVencimientos.txt");
            objOps = new ObjectOutputStream(ops);
            objOps.writeObject(vencimiento);
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

	/**
	 * @return the vencimiento
	 */
	public static List<Vencimiento> getVencimiento() {
		return vencimiento;
	}

	/**
	 * @param vencimiento the vencimiento to set
	 */
	public static void setVencimiento(List<Vencimiento> vencimiento) {
		ArchivoDeObjetos.vencimiento = vencimiento;
	}
}
