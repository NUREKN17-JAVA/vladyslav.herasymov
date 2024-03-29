package ua.nure.kn.herasymov.usermanagement_me.db;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {
	
	private static final String USER_DAO = "dao.ua.nure.kn.herasymov.usermanagement_me.db.UserDao";
	private final Properties properties;
	
	private final static DaoFactory INSTANCE = new DaoFactory();
	
	public static DaoFactory getInstance() {
		return INSTANCE;
	}
	
	private DaoFactory() {
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private ConnectonFactory getConnectonFactory() {
		String user = properties.getProperty("connection.user");
		String password = properties.getProperty("connection.password");
		String url = properties.getProperty("connection.url");
		String driver = properties.getProperty("connection.driver");
		return new ConnectioFactoryImpl(driver, url, user, password);
	}
	
	public UserDao getUserDao() {
		UserDao result = null;
		try {
			Class clazz = Class.forName(properties.getProperty(USER_DAO));
			result = (UserDao) clazz.newInstance();
			result.setConnectonFactory(getConnectonFactory());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	
	
}
