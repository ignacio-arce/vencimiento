package dao;

import java.sql.*;
import java.util.ArrayList;

import model.Vencimiento;

public class VencimientoDaoImpl implements VencimientoDao {

	private static ArrayList<Vencimiento> listaVencimientos;
	private Connection c = null;
	private Statement stmt = null;
	private static final String NOMBRE_TABLA = "VENCIMIENTO";
	private static final String DRIVER = "org.sqlite.JDBC";
	private static final String DBURL = "jdbc:sqlite:data.db";

	public VencimientoDaoImpl() {

	}

	
	@Override
	public ArrayList<Vencimiento> getListaVencimientos() {
		try {
			String sql = "";

			if (!new java.io.File(DBURL.split(":")[2]).exists()) {
				sql = "CREATE TABLE " + NOMBRE_TABLA + " " + "(ID INTEGER PRIMARY KEY AUTOINCREMENT    NOT NULL,"
						+ " Tipo           TEXT    NOT NULL, " + " Fecha           TEXT     NOT NULL, "
						+ " Lote           TEXT)";
			}
			Class.forName(DRIVER);
			c = DriverManager.getConnection(DBURL);

			// System.out.println("DB opened");
			stmt = c.createStatement();
			if (!sql.equals("")) {
				stmt.executeUpdate(sql);
			}
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + NOMBRE_TABLA + ";");

			listaVencimientos = new ArrayList<Vencimiento>();
			while (rs.next()) {
				String tipo = rs.getString("Tipo");
				String lote = rs.getString("Lote");
				int id = rs.getInt("ID");
				listaVencimientos.add(new Vencimiento(rs.getString("Fecha").split("-"), tipo, lote, id));
			}

			rs.close();
			stmt.close();
			c.close();
			return listaVencimientos;

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
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
			// System.out.println("DB opened");
			stmt = c.createStatement();

			String sql = "INSERT INTO " + NOMBRE_TABLA + " (Fecha,Tipo,Lote) " + "VALUES ( '"
					+ ven.getFechaVencimiento() + "', '" + ven.getTipo() + "' ,'" + ven.getLote() + "');";
			stmt.executeUpdate(sql);
			ResultSet rs = stmt.executeQuery("SELECT seq FROM sqlite_sequence WHERE name='" + NOMBRE_TABLA + "';");
			ven.setId(rs.getInt("seq"));
			rs.close();
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		listaVencimientos.add(ven);
		System.out.println("Datos cargados satisfactoriamente");
	}

	@Override
	public void borrarVencimiento(Vencimiento vencimiento) {
		try {
			Class.forName(DRIVER);
			c = DriverManager.getConnection(DBURL);
			c.setAutoCommit(false);
			// System.out.println("DB opened");
			stmt = c.createStatement();

			String sql = "DELETE from " + NOMBRE_TABLA + " where ID='" + vencimiento.getId() + "';";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		listaVencimientos.remove(vencimiento);
		System.out.println("Vencimiento borrado satisfactoriamente");
	}

}
