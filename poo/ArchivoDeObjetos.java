import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.text.*;
 
public class ArchivoDeObjetos {
 
    public void guardarObjeto(Vencimiento ven){
         
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
     
    public void mostrarObjeto(){
         
        InputStream fileIs = null;
        ObjectInputStream objIs = null;
        try {
            fileIs = new FileInputStream("archivoVencimientos.txt");
            objIs = new ObjectInputStream(fileIs);
            Vencimiento ven = (Vencimiento) objIs.readObject();
            System.out.println(ven);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(objIs != null) objIs.close();
            } catch (Exception ex){
                 
            }
        }
         
    }
     
    public static void main(String a[]){
	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	Date today = new Date();
	Date todayWithZeroTime = null;
	try {
		todayWithZeroTime = formatter.parse(formatter.format(today));
	} catch (Exception e) {

	}
        ArchivoDeObjetos ado = new ArchivoDeObjetos();
        Vencimiento v1 = new Vencimiento(todayWithZeroTime,2200,true,"Seguro del auto");
        ado.guardarObjeto(v1);
        ado.mostrarObjeto();
    }
}