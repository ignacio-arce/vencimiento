package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Vencimiento;

public class VencimientoDaoImpl implements VencimientoDao {

	private List<Vencimiento> listaVencimientos;
	private Connection c = null;
	private Statement stmt = null;
	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String DBURL = "jdbc:sqlite:data.db";

	public VencimientoDaoImpl() {
		this.listaVencimientos = getListaVencimientos();
		
	}

	@Override
	public List<Vencimiento> getListaVencimientos() {
		try {
			Class.forName(DRIVER);
			c = DriverManager.getConnection(DBURL);
			List<Vencimiento> lista = new ArrayList<Vencimiento>();
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM VENCIMIENTO;");
			
			while (rs.next()) {
				String fecha = rs.getString("Fecha");
				String tipo = rs.getString("Tipo");
				String lote = rs.getString("Lote");
				lista.add(new Vencimiento(LocalDate.now(),tipo,lote));
			}
			rs.close();
			stmt.close();
			c.close();
			return lista;
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return null;
	}

	@Override
	public Vencimiento getVencimiento(int n) {
		return listaVencimientos.get(n);
	}

	@Override
	public void guardarVencimientos(Vencimiento ven) {
	      try {
	         Class.forName(DRIVER);
	         c = DriverManager.getConnection(DBURL);
	         c.setAutoCommit(false);
	         System.out.println("Opened database successfully");

	         stmt = c.createStatement();
	         String sql = "INSERT INTO VENCIMIENTO (Fecha,Tipo,Lote) " +
	                        "VALUES ( '" + ven.getFechaVencimiento() + "', '" + ven.getLote() + "' ,'" + ven.getLote() + "');"; 
	         stmt.executeUpdate(sql);

	         stmt.close();
	         c.commit();
	         c.close();
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Records created successfully");
	}

	@Override
	public void borrarVencimiento(Vencimiento vencimiento) {

	}

}
