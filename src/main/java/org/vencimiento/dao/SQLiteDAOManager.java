package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDAOManager implements DAOManager {

	private static final String DRIVER = "org.sqlite.JDBC";
	private static final String DBURL = "jdbc:sqlite:data.db";
	private Connection conn;
	private VencimientoDao vencimientoDao;

	public SQLiteDAOManager() {

		try {
			Class.forName(DRIVER);
			conn = getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public VencimientoDao getVencimientoDao() {
		if (this.vencimientoDao == null) {
			this.vencimientoDao = new VencimientoDaoImpl(conn);
		}
		return this.vencimientoDao;
	}

	private static Connection getConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DBURL);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

}
