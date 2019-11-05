package ua.nure.kn.herasymov.usermanagement_me.db;

import java.util.Collection;

import ua.nure.kn.herasymov.usermanagement_me.User;

public interface UserDao {
	
	User create (User user) throws DatabaseException;
	
	User update(User user) throws DatabaseException;
	
	User delet(User user) throws DatabaseException;
	
	User find(Long id) throws DatabaseException;
	
	@SuppressWarnings("rawtypes")
	Collection findAll() throws DatabaseException;
	
	void setConnectonFactory(ConnectonFactory connectonFactory);
	
}
