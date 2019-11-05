package ua.nure.kn.herasymov.usermanagement_me.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import junit.framework.TestCase;
import ua.nure.kn.herasymov.usermanagement_me.User;

@SuppressWarnings("unused")
public class HsqldbUserDaoTest extends DatabaseTestCase {
	
	private static final Date DATE_OF_BIRTH_UPDATE2 = new Date(2000-07-18);
	private static final Date DATE_OF_BIRTH_UPDATE = new Date(1999-10-07);
	private static final String LAST_NAME_UPDATE2 = "Tayson";
	private static final String FIRST_NAME_UPDATE2 = "Mike";
	private static final String FIRST_NAME2 = "Ivan";
	private static final String LAST_NAME2 = "Varenkov";
	private static final long ID = 1000L;

	private static final String LAST_NAME_UPDATE = "Murphy";
	private static final String FIRST_NAME_UPDATE = "Dairon";
	private static final String LAST_NAME = "Doe";
	private static final String FIRST_NAME = "John";
	
	private HsqldbUserDao dao;
	private ConnectonFactory connectonFactory;
	

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDao(connectonFactory);
	}

	public void testCreate() {
		try {
			User user = new User();
			user.setFirstName(FIRST_NAME);
			user.setLastName(LAST_NAME);
			user.setDateOfBirthd(new Date());
			assertNull(user.getId());
			user = dao.create(user);
			assertNotNull(user);
			assertNotNull(user.getId());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void testFindAll() {
		try {
			Collection collection = dao.findAll();
			assertNotNull("Collection is null", collection);
			assertEquals("Collection size.", 2, collection.size());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	public void testUpdate() {
		try {
			User user = new User();
			user.setFirstName(FIRST_NAME_UPDATE);
			user.setLastName(LAST_NAME_UPDATE);
			user.setDateOfBirthd(DATE_OF_BIRTH_UPDATE);
			user = dao.create(user);
			assertEquals(FIRST_NAME_UPDATE,user.getFirstName());
			assertEquals(LAST_NAME_UPDATE,user.getLastName());
			assertEquals(DATE_OF_BIRTH_UPDATE,user.getDateOfBirthd());
			user.setFirstName(FIRST_NAME_UPDATE2);
			user.setLastName(LAST_NAME_UPDATE2);
			user.setDateOfBirthd(DATE_OF_BIRTH_UPDATE2);
			dao.update(user);
			assertEquals(FIRST_NAME_UPDATE2,user.getFirstName());
			assertEquals(LAST_NAME_UPDATE2,user.getLastName());
			assertEquals(DATE_OF_BIRTH_UPDATE2,user.getDateOfBirthd());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	public void testDelete() {
		try {
			User user = new User();
			user.setFirstName(FIRST_NAME_UPDATE);
			user.setLastName(LAST_NAME_UPDATE);
			user.setDateOfBirthd(DATE_OF_BIRTH_UPDATE);
			user = dao.create(user);
			assertNotNull(user.getId());
			dao.delet(user);
			User user2 = dao.find(user.getId());
			assertNull(user2);
			
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	public void testFind() {
		try {
			User user = new User();
			user = dao.find(ID);
			user.setFirstName(FIRST_NAME2);
			user.setLastName(LAST_NAME2);
			assertEquals(FIRST_NAME2,user.getFirstName());
			assertEquals(LAST_NAME2, user.getLastName());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectonFactory = new ConnectioFactoryImpl("org.hsqldb.jdbcDriver",
				"jdbc:hsqldb:file:db/usermanagement_me", "sa", "");
		return new DatabaseConnection(connectonFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader()
				.getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

}
