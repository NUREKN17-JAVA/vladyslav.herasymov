package ua.nure.kn.herasymov.usermanagement_me.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {
	
	protected static final String USER_DAO = "dao.ua.nure.kn.herasymov.usermanagement_me.db.UserDao";
	private static final String DAO_FACTORY = "dao.factory";
	protected static Properties properties;
	
	private static DaoFactory instance;
	
	static {
		properties = new Properties();
		try {
			properties.load(DaoFactory.class.getClassLoader()
					.getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static synchronized DaoFactory getInstance() {
		if (instance == null){
	    		try {
				Class factoryClass = Class.forName(properties
						.getProperty(DAO_FACTORY));
				instance = (DaoFactory) factoryClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	   	}
	       return instance;
	}
	
	protected DaoFactory() {
		
	}

	public static void init(Properties prop) {
		properties = prop;
		instance = null;
	}
	
	protected ConnectonFactory getConnectonFactory() {
		return new ConnectioFactoryImpl(properties);
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
