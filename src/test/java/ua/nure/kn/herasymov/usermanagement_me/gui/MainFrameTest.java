package ua.nure.kn.herasymov.usermanagement_me.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.tree.ExpandVetoException;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.herasymov.usermanagement_me.User;
import ua.nure.kn.herasymov.usermanagement_me.db.DaoFactory;
import ua.nure.kn.herasymov.usermanagement_me.db.DaoFactoryImpl;
import ua.nure.kn.herasymov.usermanagement_me.db.MockDaoFactory;
import ua.nure.kn.herasymov.usermanagement_me.db.MockUserDao;

public class MainFrameTest extends JFCTestCase {

	private MainFrame mainFrame;
	private Mock mockUserDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		try {
			Properties properties = new Properties();
			properties.setProperty("dao.factory", MockDaoFactory.class.
					getName());
			DaoFactory.getInstance().init(properties);
			mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
			mockUserDao.expectAndReturn("findAll", new ArrayList());
			setHelper(new JFCTestHelper());
			mainFrame = new MainFrame();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		mainFrame.setVisible(true);
	}
	
	protected void tearDown() throws Exception {
		try {
			mockUserDao.verify();
			mainFrame.setVisible(false);
			getHelper().cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Component find(Class componentClass, String name) {
		 NamedComponentFinder finder;
		 finder = new NamedComponentFinder(componentClass, name);
		 finder.setWait(0);
		 Component component = finder.find(mainFrame, 0);
		 assertNotNull("Could not find component '" + name + "'", component);
		 return component;
	}
	
	public void testBrowseControls() {
		find(JPanel.class, "browsePanel");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals("ID", table.getColumnName(0));
		assertEquals("Имя", table.getColumnName(1));
		assertEquals("Фамилия", table.getColumnName(2));
		
		find(JButton.class, "addButton");
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
	}
	
	public void testAddUser() {
		try {
			String firstName = "John";
			String lastName = "Doe";
			Date now = new Date();
			
			User user = new User(firstName, lastName, now);
			
			User expectedUser = new User(new Long(1), firstName, lastName, now);
			mockUserDao.expectAndReturn("create", user, expectedUser);
			
			ArrayList users = new ArrayList();
			mockUserDao.expectAndReturn("findAll", users);
			
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(0, table.getRowCount());
			
			JButton addButton = (JButton) find(JButton.class, "addButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
			
			find(JPanel.class, "addPanel");
			
			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
			JTextField dayOfBirthField = (JTextField) find(JTextField.class, "dayOfBirthField");
			JButton okButton = (JButton) find(JButton.class, "okButton");
			find(JButton.class, "cancelButton");
		
			getHelper().sendString(new StringEventData(this, firstNameField, firstName));
			getHelper().sendString(new StringEventData(this, lastNameField, lastName));
			DateFormat formatter = DateFormat.getDateInstance();
			String date = formatter.format(now);
			getHelper().sendString(new StringEventData(this, dayOfBirthField, date));
			
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			
			find(JPanel.class, "browsePanel");	
			
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
		public void testEditUser() { 
			String lastName = "Doe";
			String firstName = "John";
			Date now = new Date();
			
			User user = new User(firstName, lastName, now);
			
			find(JPanel.class, "browsePanel");

            User expectedUser = new User(new Long(2), firstName, lastName, new Date());
            mockUserDao.expect("update", expectedUser);
            ArrayList users = new ArrayList();
            users.add(user);
            users.add(expectedUser);

            mockUserDao.expectAndReturn("findAll", users);

            JTable userTable = (JTable) find(JTable.class, "userTable");
            assertEquals(1, userTable.getRowCount());
            JButton editButton = (JButton) find(JButton.class, "editButton");
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
            
            find(JPanel.class, "editPanel");
            JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
            JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
            getHelper().sendString(new StringEventData(this, firstNameField, "1"));
            getHelper().sendString(new StringEventData(this, lastNameField, "1"));

            JButton okButton = (JButton) find(JButton.class, "okButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
            
            find(JPanel.class, "browsePanel");
            userTable = (JTable) find(JTable.class, "userTable");
            assertEquals(2, userTable.getRowCount());
		}
		
		public void testDetailsUser() {
			try {
				String lastName = "Doe";
				String firstName = "John";
				Date now = new Date();
				
				User expectedUser = new User(new Long(2), firstName, lastName, new Date());
		        mockUserDao.expect("update", expectedUser);
				ArrayList users = new ArrayList();
	            mockUserDao.expectAndReturn("findAll", users);

	            JTable table = (JTable) find(JTable.class, "userTable");
	            assertEquals(1, table.getRowCount());

	            JButton detailsButton = (JButton) find(JButton.class, "detailsButton");
	            getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));

	            String title = "Details user";

	            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
	            getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));

	            find(JPanel.class, "detailsPanel");

	            JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
	            getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

	            find(JPanel.class, "browsePanel");
	            table = (JTable) find(JTable.class, "userTable");
	            assertEquals(1, table.getRowCount());
	        } catch (Exception e) {
	            fail(e.toString());
	        }
		}
		
		public void testDeleteUser() {
	        try {
	            User expectedUser = new User(new Long(1001), "George", "Bush", new Date());
	            mockUserDao.expect("delete", expectedUser);
	            ArrayList users = new ArrayList();
	            mockUserDao.expectAndReturn("findAll", users);

	            JTable table = (JTable) find(JTable.class, "userTable");
	            assertEquals(1, table.getRowCount());
	            JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
	            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
	            getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
	            
	            find(JPanel.class, "browsePanel");
	            table = (JTable) find(JTable.class, "userTable");
	            assertEquals(3, table.getRowCount());

	        } catch (Exception e) {
	            fail(e.toString());
	        }
	    }

		
}
