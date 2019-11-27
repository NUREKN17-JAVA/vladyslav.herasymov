package ua.nure.kn.herasymov.usermanagement_me;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class UserTest extends TestCase {

	private User user;
	private Date dateOfBirthd;
	
	protected void setUp() throws Exception {
		super.setUp();
		user = new User();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1984, Calendar.MAY, 26);
		dateOfBirthd = calendar.getTime();
		
	}

	public void testGetFullName() {
		user.setFirstName("John");
		user.setLastName("Doe");
		assertEquals("Doe, John", user.getFullName());
	}
	
	public void testGetAge() {
		user.setDateOfBirthd(dateOfBirthd);
		assertEquals(2019 - 1984, user.getAge());
	}
}
