package ua.nure.kn.herasymov.usermanagement_me.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.kn.herasymov.usermanagement_me.User;

 class HsqldbUserDao implements UserDao {

	private static final String FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
	private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
	private static final String UPDATE_USER = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";
	private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";
	private static final String SELECT_QUERY = "SELECT * FROM users WHERE id = ?";
	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private static final String SELECT_BY_NAME = "SELECT * FROM users WHERE firstname=? AND lastname=?";
	private ConnectonFactory connectonFactory ;
	
	public HsqldbUserDao() {

	}
	
	public HsqldbUserDao(ConnectonFactory connectonFactory) {
		this.connectonFactory = connectonFactory;
	}
	
	
	public ConnectonFactory getConnectonFactory() {
		return connectonFactory;
	}


	public void setConnectonFactory(ConnectonFactory connectonFactory) {
		this.connectonFactory = connectonFactory;
	}



	@Override
	public User create(User user) throws DatabaseException {
		try {
			Connection connection = connectonFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
			int n = statement.executeUpdate();
			if(n!=1) {
				throw new DatabaseException("Number of the inserted rows: " + n);
			}
			CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
			ResultSet keys = callableStatement.executeQuery();
			if (keys.next()) {
				user.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return user;
		} catch (DatabaseException e){
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	

	@Override
	public User update(User user) throws DatabaseException {
		try {
			Connection connection = connectonFactory.createConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
			preparedStatement.setLong(4, user.getId());
			
			int insertedRows = preparedStatement.executeUpdate();
			
			if (insertedRows != 1) {
				throw new DatabaseException("Number of inserted rows-" + insertedRows);
			}
			
			connection.close();
			preparedStatement.close();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e.toString());
		}
		return null;
	}

	@Override
	public User delet(User user) throws DatabaseException {
		try {
			Connection connection = connectonFactory.createConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
			preparedStatement.setLong(1, user.getId());
			
			int removedRows = preparedStatement.executeUpdate();
			
			if (removedRows !=1) {
				throw new DatabaseException("Number of removed rows-" + removedRows);
			}
			connection.close();
			preparedStatement.close();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e.toString());
		}
		return null;
	}

	@Override
	public User find(Long id) throws DatabaseException {
		User user = null;
		try {
			Connection connection = connectonFactory.createConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
			preparedStatement.setLong(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			user = null;
			
			while (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getLong(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirthd(resultSet.getDate(4));
			}
			connection.close();
			preparedStatement.close();
			resultSet.close();
			} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e.toString());
		}
		return user;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Collection findAll() throws DatabaseException {
		Collection result = new LinkedList();
		
		try {
			Connection connection = connectonFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			while (resultSet.next()) {
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirthd(resultSet.getDate(4));
				result.add(user);
			}
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		return result;
	}
	
	@Override
	public Collection find(String firstName, String lastName) throws DatabaseException {
		Collection result = new LinkedList();

	    try {
	      Connection connection = connectionFactory.createConnection();
	      PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME);
	      statement.setString(1, firstName);
	      statement.setString(2, lastName);
	      ResultSet resultSet = statement.executeQuery();
	      while(resultSet.next()){
	        User user = new User();
	        user.setId(new Long(resultSet.getLong(1)));
	        user.setFirstName(resultSet.getString(2));
	        user.setLastName(resultSet.getString(3));
	        user.setDateOfBirth(resultSet.getDate(4));
	        result.add(user);
	      }
	    } catch (SQLException e) {
	      throw new DatabaseException(e);
	    }
	    return result;
	  }



}
