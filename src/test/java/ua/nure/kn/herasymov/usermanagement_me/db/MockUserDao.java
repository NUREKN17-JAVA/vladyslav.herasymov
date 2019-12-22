package ua.nure.kn.herasymov.usermanagement_me.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ua.nure.kn.herasymov.usermanagement_me.User;

public class MockUserDao implements UserDao {

	private long id = (long) 0;
	private Map users = new HashMap();
	
	@Override
	public User create(User user) throws DatabaseException {
		Long currentId = new Long(++id);
		user.setId(currentId);
		users.put(currentId, user);
		return user;
	}

	@Override
	public User update(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.remove(currentId);
		users.put(currentId, user);
		return user;
	}

	@Override
	public User delet(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.remove(currentId);
		return null;
	}

	@Override
	public User find(Long id) throws DatabaseException {
		
		return (User) users.get(id);
	}

	@Override
	public Collection findAll() throws DatabaseException {
		
		return users.values();
	}

	@Override
	public void setConnectonFactory(ConnectonFactory connectonFactory) {
		// TODO Auto-generated method stub
	}
	public Collection find(String firstName, String lastName) throws DatabaseException{
		throw new UnsupportedOperationException();
	}

}
