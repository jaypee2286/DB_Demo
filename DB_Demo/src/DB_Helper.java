import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
public class DB_Helper {

	// MySQL account username & password
	private final String userName = "root";
	private final String password = "root";

	// Computer information of system running MySQL
	private final String serverName = "localhost";
	private final int portNumber = 3306;
	private Connection conn = null;
	private Statement statement = null;

	// The name of the database we are testing with (this default is installed
	// with MySQL)
	private final String dbName = "test";

	// The name of the table we are testing with
	private String tableName = "JDBC_TEST";

	// Default constructor
	public DB_Helper() throws SQLException {
		this.openConnection();
	}

	/**
	 * Set method for table name.
	 * 
	 * @param input
	 *            Name of the table.
	 */
	public void setTableName(String input) {
		tableName = input;
	}

	/**
	 * Get method for current table name.
	 * 
	 * @return tableName Name of table being worked on.
	 */
	public String getTableName() {
		return this.tableName;
	}

	/**
	 * Attempts to open a connection to the local MySQL database.
	 * 
	 * @throws SQLException
	 *             If something goes wrong.
	 */
	public void openConnection() throws SQLException {
		try {
			Properties connectionProps = new Properties();
			connectionProps.put("user", this.userName);
			connectionProps.put("password", this.password);
			conn = DriverManager.getConnection("jdbc:mysql://"
					+ this.serverName + ":" + this.portNumber + "/"
					+ this.dbName, connectionProps);
			System.out.println("SUCCESS: Connected to database.");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database.");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Attempts to close the connection to the database.
	 * 
	 * @throws SQLException
	 *             If something goes wrong.
	 */
	public void closeConnection() throws SQLException {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not close database connection.");
		}
	}

	/**
	 * Queries the database with a SELECT command and returns a ResultSet.
	 * 
	 * @throws SQLException
	 *             If something goes wrong.
	 * @return results This
	 * 
	 */
	public void searchForName(String tableName, String searchName)
			throws SQLException {
		try {

			System.out.println("\nSearching for " + searchName + " . . .");
			
			// Create statement and run query
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String command = "SELECT id, name, street, city, state, zip FROM "
					+ tableName + " WHERE name LIKE '%" + searchName + "%'";
			ResultSet rs = statement.executeQuery(command);

			// Print query search results to console starting with the first
			rs.first();
			System.out.println("Results obtained! Printing . . .");

			// Process & print results
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String street = rs.getString("street");
			String city = rs.getString("city");
			String state = rs.getString("state");
			String zip = rs.getString("zip");
			System.out.println("ID: " + id);
			System.out.println(name);
			System.out.println(street);
			System.out.println(city + ", " + state + " " + zip);

			// If there are multiple results, this loop will process them
			while (rs.next()) {
				id = rs.getInt("id");
				name = rs.getString("name");
				street = rs.getString("street");
				city = rs.getString("city");
				state = rs.getString("state");
				zip = rs.getString("zip");
				System.out.println("ID: " + id);
				System.out.println(name);
				System.out.println(street);
				System.out.println(city + ", " + state + " " + zip);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("SQLEXCEPTION SE");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION E");
		} finally {

			// Close the statement & resultset.
			// This will run regardless of an exception.
			if (statement != null) {
				statement.close();
			}
		}
	}

	/**
	 * Run a SQL command which does not return a ResultSet:
	 * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
	 * 
	 * @throws SQLException
	 *             If something goes wrong
	 */
	public boolean executeUpdate(String command) throws SQLException {
		try {
			statement = this.conn.createStatement();
			statement.executeUpdate(command);
			return true;
		} finally {

			// This will run regardless of an exception
			if (statement != null) {
				statement.close();
			}
		}
	}

	/**
	 * Run a SQL command to obtain the number of records in the table.
	 * 
	 * @return count The number of records in the table.
	 * @throws SQLException
	 *             If something goes wrong
	 */
	public int getNumberOfRecords() throws SQLException {
		statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet sqlCount = statement
				.executeQuery("SELECT COUNT(*) AS count FROM " + tableName);
		sqlCount.first();
		int count = sqlCount.getInt("count");
		return count;
	}

	/**
	 * Run a SQL command to drop the table.
	 */
	public void dropTable() {
		// Drop the table
		try {
			String dropString = "DROP TABLE " + this.tableName;
			this.executeUpdate(dropString);
			System.out.println("Dropped the table");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not drop the table");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Run a SQL command to drop the specified table.
	 * 
	 * @param name
	 *            Specified table name to be dropped.
	 * @throws SQLException
	 *             If something goes wrong
	 */
	public void dropTable(String name) {
		// Drop the table
		try {
			String dropString = "DROP TABLE " + name;
			this.executeUpdate(dropString);
			System.out.println("Dropped the table");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not drop the table");
			e.printStackTrace();
			return;
		}
	}
}