package co.com.icono.core.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ICONOTECHConnection {

	private static HikariDataSource connectionPool = null;
	private static Connection connection = null;

	private static String IP_BD = "localhost";

	public static Connection getConnection() throws SQLException {
		if (connectionPool == null) {
			initpool();
		}
		connection = connectionPool.getConnection();
		return connection;
	}

	private static void initpool() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:postgresql://" + IP_BD + ":5432/ICONOTECH");
		config.setUsername("postgres");
		config.setPassword("postgres");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.setMaximumPoolSize(50);
		connectionPool = new HikariDataSource(config);
	}

	public static void closePool() {
		connectionPool.close();
	}
	
	public static void main(String[] args) {
		try {
		Connection con = ICONOTECHConnection.getConnection();
		con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
