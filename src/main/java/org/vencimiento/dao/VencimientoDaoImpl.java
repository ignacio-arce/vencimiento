package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Vencimiento;

public class VencimientoDaoImpl implements VencimientoDao {

	private Connection conn = null;
	private static final String NOMBRE_TABLA = "VENCIMIENTO";

	//private final String CREATE_TABLE = "CREATE TABLE  " + NOMBRE_TABLA
	//		+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Tipo TEXT NOT NULL, Fecha TEXT NOT NULL, Lote TEXT);";
	private final String INSERT = "INSERT INTO " + NOMBRE_TABLA + " (Tipo,Fecha,Lote) VALUES (?, ? ,?);";
	private final String DELETE = "DELETE from " + NOMBRE_TABLA + " where ID= ?;";
	private final String GETALL = "SELECT ID, Fecha, Tipo, Lote FROM " + NOMBRE_TABLA + ";";

	public VencimientoDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Vencimiento> getListaVencimientos() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Vencimiento> listaVencimientos = new ArrayList<>();
		
		try {
				
				stmt = conn.prepareStatement(GETALL);
				rs = stmt.executeQuery();
				
				while (rs.next()) {
					listaVencimientos.add(convertir(rs));
				}
			
			
			/*
			

			
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(DBURL);

			stmt = conn.prepareStatement(GETALL);
			if (!sql.equals("")) {
				stmt.executeUpdate(sql);
			}
			conn.setAutoCommit(false);
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
			conn.close();
			return listaVencimientos;

		}*/
		}catch(

	Exception e)
	{
		System.err.println(e.getClass().getName() + ": " + e.getMessage());
		System.exit(0);
	}finally
	{
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}return listaVencimientos;
	}

	@Override
	public Vencimiento getVencimiento(int n) {
		return null;
	}

	@Override
	public void guardarVencimientos(Vencimiento ven) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT);
			stmt.setString(1, ven.getTipo());
			stmt.setString(2, ven.getFechaVencimiento().toString());
			stmt.setString(3, ven.getLote());
			stmt.executeUpdate();
			/*Class.forName(DRIVER);
			c = DriverManager.getConnection(DBURL);
			c.setAutoCommit(false);
			stmt = c.createStatement();

			String sql = "INSERT INTO " + NOMBRE_TABLA + " (Fecha,Tipo,Lote) " + "VALUES ( '"
					+ ven.getFechaVencimiento() + "', '" + ven.getTipo() + "' ,'" + ven.getLote() + "');";
			stmt.executeUpdate(sql);
			ResultSet rs = stmt.executeQuery("SELECT seq FROM sqlite_sequence WHERE name='" + NOMBRE_TABLA + "';");
			ven.setId(rs.getInt("seq"));
			rs.close();
			stmt.close();
			c.commit();
			c.close();*/
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Datos cargados satisfactoriamente");
	}

	@Override
	public void borrarVencimiento(Vencimiento ven) {
		PreparedStatement stmt = null;
		try {
			
			stmt = conn.prepareStatement(DELETE);
			stmt.setInt(1, ven.getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		System.out.println("Vencimiento borrado satisfactoriamente");
	}

	private Vencimiento convertir(ResultSet rs) throws SQLException {
		String tipo = rs.getString("Tipo");
		String lote = rs.getString("Lote");
		java.time.LocalDate fecha = LocalDate.parse(rs.getString("Fecha"));
		int id = rs.getInt("ID");
		
		return new Vencimiento(fecha, tipo, lote, id);
	}

}
