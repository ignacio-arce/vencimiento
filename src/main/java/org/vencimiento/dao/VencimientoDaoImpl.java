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
	private static final String DRIVER = "org.sqlite.JDBC";
	private static final String DBURL = "jdbc:sqlite:data.db";

	public VencimientoDaoImpl() {
		this.listaVencimientos = getListaVencimientos();
	}
	
	private int[] toInteger(String cadenas[]) {
		int salidaInt[] = new int[3];
		int i=0;
		for(String s: cadenas) {
			salidaInt[i]=Integer.valueOf(s);
			i++;
		}
		return salidaInt;
	}
	

	
	@Override
	public List<Vencimiento> getListaVencimientos() {
		try {
			Class.forName(DRIVER);
			c = DriverManager.getConnection(DBURL);
			
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			List<Vencimiento> lista = new ArrayList<Vencimiento>();
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM VENCIMIENTO;");

			while (rs.next()) {
				int fecha[] = toInteger(rs.getString("Fecha").split("-"));
				String tipo = rs.getString("Tipo");
				String lote = rs.getString("Lote");
				int id = rs.getInt("ID");
				lista.add(new Vencimiento(LocalDate.of(fecha[0],fecha[1],fecha[2]), tipo, lote, id));
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
			
			String sql = "INSERT INTO VENCIMIENTO (Fecha,Tipo,Lote) " + "VALUES ( '" + ven.getFechaVencimiento()
					+ "', '" + ven.getTipo() + "' ,'" + ven.getLote() + "');";
			stmt.executeUpdate(sql);
			ResultSet rs = stmt.executeQuery("SELECT seq FROM sqlite_sequence WHERE name='VENCIMIENTO';");
			ven.setId(rs.getInt("seq"));
			System.out.println(rs.getInt("seq"));
			rs.close();
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		listaVencimientos.add(ven);
		System.out.println("Records created successfully");
	}

	@Override
	public void borrarVencimiento(Vencimiento vencimiento) {
		try {
			Class.forName(DRIVER);
			c = DriverManager.getConnection(DBURL);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "DELETE from VENCIMIENTO where ID='" + vencimiento.getId() +"';";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		listaVencimientos.remove(vencimiento);
		System.out.println("Deleted:" + vencimiento.getId());
		System.out.println("Operation done successfully");
	}

}
