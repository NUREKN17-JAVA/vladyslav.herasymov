package ua.nure.kn.herasymov.usermanagement_me.db;

import com.mockobjects.dynamic.Mock;

import ua.nure.kn.herasymov.usermanagement_me.User;

public class MockDaoFactory extends DaoFactory {
	
	private Mock mockUserDao;
	
	public MockDaoFactory() {
		mockUserDao = new Mock(UserDao.class);
	}
	
	public Mock getMockUserDao() {
		return mockUserDao;
	}
	
	public UserDao getUserDao() {
		
		return (UserDao) mockUserDao.proxy();
	}
	
}
