import java.sql.SQLException;

/**
 * This is a demo program. This program was written to demonstrate basic working
 * knowledge of JDBC Database Programming. A local MySQL server was used with
 * USER: root, PASSWORD: root
 * 
 * This program creates a test database table of users. The table contains
 * columns consisting of user data. User data includes name & address in the
 * columns: NAME, STREET, CITY, STATE, ZIP
 * 
 * All database commands are handled in class DB_Helper.
 * 
 * References: 
 * http://www.tutorialspoint.com/jdbc/index.htm
 * http://www.vogella.com/tutorials/MySQLJava/article.html
 * 
 */

public class DB_Demo {

	public final static String tableName = "DB_Demo_Test_Table";
	public static String command = null;

	public static void main(String[] args) throws SQLException {

		// Create object 'example' of DB_Helper class
		DB_Helper example = new DB_Helper();

		// Populate the database with a test table
		populateDB(example);

		// At this point, the test database is now filled with test data.
		// Non ResultSet update commands can be used with the 'executeUpdate
		// method.
		//
		// A search can be attempted using searchDB method. Results are printed
		// to console.
		//
		// Both methods take a String parameter - the SQL query command.

		// Search database for names and print to console
		example.searchForName(tableName, "John");
		example.searchForName(tableName, "Doe");

		// Attempt to drop the table
		System.out.println("Dropping table...");
		example.dropTable(tableName);
	}

	private static void populateDB(DB_Helper example) throws SQLException {

		// Drop table if it wasn't previously dropped
		// Ensures a fresh, clean table
		System.out.println("Dropping table if it exists...");
		example.dropTable(tableName);

		// SQL command to create table
		command = "CREATE TABLE " + tableName + " ( "
				+ "ID INTEGER NOT NULL AUTO_INCREMENT, "
				+ "NAME varchar(40) NOT NULL, "
				+ "STREET varchar(40) NOT NULL, "
				+ "CITY varchar(20) NOT NULL, " + "STATE char(2) NOT NULL, "
				+ "ZIP char(5), " + "PRIMARY KEY (ID))";
		example.setTableName(tableName);
		example.executeUpdate(command);

		// Add entry #1
		command = "INSERT INTO " + tableName
				+ " values (default, 'John Doe', '10 Alpha Street',"
				+ "'Los Angeles', 'CA', '90210');";
		example.executeUpdate(command);

		// Add entry #2
		command = "INSERT INTO " + tableName
				+ " values (default, 'Jane Doe', '20 Beta Street',"
				+ "'Los Angeles', 'CA', '90210');";
		example.executeUpdate(command);

		// Add entry #3
		command = "INSERT INTO " + tableName
				+ " values (default, 'Marky Mark', '30 Charlie Street',"
				+ "'Los Angeles', 'CA', '90211');";
		example.executeUpdate(command);

		// Add entry #4
		command = "INSERT INTO " + tableName
				+ " values (default, 'Clarky Clark', '40 Delta Street',"
				+ "'Los Angeles', 'CA', '90212');";
		example.executeUpdate(command);
	}
}