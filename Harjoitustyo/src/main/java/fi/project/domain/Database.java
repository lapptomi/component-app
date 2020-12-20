package fi.project.domain;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class Database {

    private String dbUrl = null;
    private final String databaseName = "app.db";
    private final String testDatabaseName = "app-test.db";

    public Database() {
        String args = System.getProperty("exec.args") != null
                ? System.getProperty("exec.args")
                : "do not run in test mode";

        if (args.equals("test")) {
            dbUrl = "jdbc:sqlite:" + testDatabaseName;
        } else {
            dbUrl = "jdbc:sqlite:" + databaseName;
        }
    }

    /**
     * Makes connection to database
     *
     * @return Connection to database
     *
     */
    public Connection getConnection() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    /**
     * Initializes database that is made for testing
     */
    public void initializeTestDatabase() {
        formatTestDb();
        try {
            Statement statement = getConnection().createStatement();
            statement.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, username TEXT NOT NULL UNIQUE, password TEXT)");
            addTestUsersToDatabase();

            statement.execute("CREATE TABLE Components (id INTEGER PRIMARY KEY, type TEXT, model TEXT, manufacturer TEXT, serialnumber TEXT UNIQUE)");
            addTestComponentsToDatabase();
        } catch (Exception e) {
            System.out.println("Error initializing test database");
        }
    }

    /**
     * Formats database that is made for testing
     */
    private void formatTestDb() {
        try {
            Statement statement = getConnection().createStatement();
            statement.execute("DROP TABLE Users");
            statement.execute("DROP TABLE Components");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error formatting test database");
        }
    }

    /**
     * Add users to database that is made for testing
     */
    private void addTestUsersToDatabase() throws ClassNotFoundException, SQLException {
        Statement statement = getConnection().createStatement();
        statement.execute("INSERT INTO Users (username, password) VALUES ('testUser1', 'password1')");
        statement.execute("INSERT INTO Users (username, password) VALUES ('testUser2', 'password2')");
    }

    /**
     * Add components to database that is made for testing
     */
    private void addTestComponentsToDatabase() throws ClassNotFoundException, SQLException {
        Statement statement = getConnection().createStatement();
        String query = "INSERT INTO Components (type, model, manufacturer, serialnumber) VALUES ('%s', '%s', '%s', '%s')";
        statement.execute(String.format(query, "GPU", "3070", "Asus", "123129381293sf"));
        statement.execute(String.format(query, "CPU", "8400", "Intel", "2222aaaaaaa"));
        statement.execute(String.format(query, "PSU", "123", "Corsair", "999sa9dnf"));
        statement.execute(String.format(query, "CPU", "2600", "AMD", "sad8as9f"));
        statement.execute(String.format(query, "Motherboard", "LGA1151", "Asus", "99sasda9dnf"));
    }

    /**
     * Deletes test database file
     */
    public void deleteTestDbFile() {
        Path path = FileSystems.getDefault().getPath(testDatabaseName);
        try {
            Files.delete(path);
        } catch (IOException x) {
            System.out.println(x.getMessage());
        }
    }

    /**
     * Creates new database with tables for users and components
     * if database does not exist yet
     */
    public void initializeDatabase() {
        try {
            Statement statement = getConnection().createStatement();
            System.out.println("Creating Table: Users");
            statement.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, username TEXT NOT NULL UNIQUE, password TEXT)");
            System.out.println("Creating Table: Components");
            statement.execute("CREATE TABLE Components (id INTEGER PRIMARY KEY, type TEXT, model TEXT, manufacturer TEXT, serialnumber TEXT UNIQUE)");
        } catch (Exception e) {
            System.out.println("Database is already existing");
        }
    }
}
