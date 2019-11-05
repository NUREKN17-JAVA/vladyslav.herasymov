package ua.nure.kn.herasymov.usermanagement_me.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectioFactoryImpl implements ConnectonFactory {

	private String driver;
	private String url;
	private String user;
	private String password;
	
	public ConnectioFactoryImpl(String driver, String url, String user, String password) {
		this.driver=driver;
		this.url=url;
		this.user=user;
		this.password=password;
	}

	@Override
	public Connection createConnection() throws DatabaseException {
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
}
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
