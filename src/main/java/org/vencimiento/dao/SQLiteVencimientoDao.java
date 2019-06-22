package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Vencimiento;

public class SQLiteVencimientoDao implements VencimientoDao {

	private Connection conn = null;
	
	private static final String NOMBRE_TABLA = "VENCIMIENTO";
	private final String INSERT = "INSERT INTO " + NOMBRE_TABLA + " (Tipo,Fecha,Lote) VALUES (?, ? ,?);";
	private final String UPDATE = "UPDATE " + NOMBRE_TABLA + " SET Tipo = ?, Fecha = ?, Lote = ? WHERE ID = ? ";
	private final String DELETE = "DELETE from " + NOMBRE_TABLA + " where ID = ?;";
	private final String GETALL = "SELECT ID, Fecha, Tipo, Lote FROM " + NOMBRE_TABLA;
	private final String GETONE = GETALL + "WHERE ID = ?";
	
	private static final Logger logger = Logger.getLogger(SQLiteVencimientoDao.class.getName());
	
	
	public SQLiteVencimientoDao(Connection conn) {
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

		} catch (Exception e) {
			logError(e);
			System.exit(0);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logError(e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logError(e);
				}
			}
		}
		return listaVencimientos;
	}

	@Override
	public Vencimiento getVencimiento(int n) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Vencimiento vencimiento = null;

		try {

			stmt = conn.prepareStatement(GETONE);
			stmt.setInt(1, n);
			rs = stmt.executeQuery();

			vencimiento=(convertir(rs));

		} catch (Exception e) {
			logError(e);
			System.exit(0);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logError(e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logError(e);
				}
			}
		}
		return vencimiento;
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
		} catch (Exception e) {
			logError(e);
			System.exit(0);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logError(e);
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
			logError(e);
			System.exit(0);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logError(e);
				}
			}

		}
		System.out.println("Vencimiento borrado satisfactoriamente");
	}

	
	@Override
	public void actualizarVencimiento(Vencimiento ven) {

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE);
			stmt.setString(1, ven.getTipo());
			stmt.setString(2, ven.getFechaVencimiento().toString());
			stmt.setString(3, ven.getLote());
			stmt.setInt(4, ven.getId());
			stmt.executeUpdate();
		} catch (Exception e) {
			logError(e);
			System.exit(0);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logError(e);
			}
		}
		System.out.println("Datos actualizados satisfactoriamente");
	}
	
	private Vencimiento convertir(ResultSet rs) throws SQLException {
		String tipo = rs.getString("Tipo");
		String lote = rs.getString("Lote");
		java.time.LocalDate fecha = java.time.LocalDate.parse(rs.getString("Fecha"));
		int id = rs.getInt("ID");

		return new Vencimiento(fecha, tipo, lote, id);
	}
	
	private static void logError(Exception e) {
		logger.log(Level.SEVERE, e.getClass().getName() + ": " + e.getMessage(), e);
	}

	

}
