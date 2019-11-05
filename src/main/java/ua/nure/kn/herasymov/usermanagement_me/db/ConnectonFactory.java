package ua.nure.kn.herasymov.usermanagement_me.db;

import java.sql.Connection;

public interface ConnectonFactory {
	Connection createConnection() throws DatabaseException;
}
