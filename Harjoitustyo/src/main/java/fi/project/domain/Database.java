package fi.project.domain;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class Database {

    private String dbUrl = null;

    public Database() {
        // Change to false if not running tests
        boolean runInTestMode = true;
        if (runInTestMode) {
            dbUrl = "jdbc:sqlite:test.db";
        } else {
            dbUrl = "jdbc:sqlite:app.db";
        }
    }

    public Connection connection(String url) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }


    public Connection getConnection() throws ClassNotFoundException {
        return this.connection(dbUrl);
    }

    public void initializeTestDatabase() {
        Path path = FileSystems.getDefault().getPath("test.db");
        try {
            Files.delete(path);
        } catch (IOException x) {
            System.out.println(x.getMessage());
        }
        try {
            Statement statement = getConnection().createStatement();
            System.out.println("Creating Table: Users");
            statement.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, username TEXT NOT NULL UNIQUE, password TEXT)");
            addTestUsersToDatabase();

            System.out.println("Creating Table: Components");
            statement.execute("CREATE TABLE Components (id INTEGER PRIMARY KEY, type TEXT, model TEXT, manufacturer TEXT, serialnumber TEXT UNIQUE)");
            addTestComponentsToDatabase();
        } catch (Exception e) {
            System.out.println("Error initializing test database");
        }
    }

    public void addTestUsersToDatabase() throws ClassNotFoundException, SQLException {
        Statement statement = getConnection().createStatement();
        statement.execute("INSERT INTO Users (username, password) VALUES ('testUser1', 'password1')");
        statement.execute("INSERT INTO Users (username, password) VALUES ('testUser2', 'password2')");
    }

    private void addTestComponentsToDatabase() throws ClassNotFoundException, SQLException {
        Statement statement = getConnection().createStatement();
        String query = "INSERT INTO Components (type, model, manufacturer, serialnumber) VALUES ('%s', '%s', '%s', '%s')";
        statement.execute(String.format(query, "GPU", "3070", "Asus", "123129381293sf"));
        statement.execute(String.format(query, "CPU", "8400", "Intel", "2222aaaaaaa"));
        statement.execute(String.format(query, "PSU", "123", "Corsair", "999sa9dnf"));
        statement.execute(String.format(query, "CPU", "2600", "AMD", "sad8as9f"));
        statement.execute(String.format(query, "Motherboard", "LGA1151", "Asus", "99sasda9dnf"));
    }

    public void initializeDatabase() {
        if (dbUrl != null) {
            return;
        }
        try {
            Statement statement = getConnection().createStatement();
            System.out.println("Creating Table: Users");
            statement.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, username TEXT NOT NULL UNIQUE, password TEXT)");
            System.out.println("Creating Table: Components");
            statement.execute("CREATE TABLE Components (id INTEGER PRIMARY KEY, type TEXT, model TEXT, manufacturer TEXT, serialnumber TEXT UNIQUE)");
        } catch (Exception e) {
            System.out.println("Error initializing database");
        }
    }
}
