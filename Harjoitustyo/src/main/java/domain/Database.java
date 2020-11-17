package domain;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.sql.*;

public class Database {

    public static String DB_URL;

    public Database() {
        // Change to false if not running tests
        boolean runInTestMode = true;
        if (runInTestMode) {
            DB_URL = "jdbc:sqlite:test.db";
        } else {
            DB_URL = "jdbc:sqlite:app.db";
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
        return this.connection(DB_URL);
    }

    public void initializeTestDatabase() {
        Path path = FileSystems.getDefault().getPath("test.db");
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException x) {
            System.err.println(x);
        }
        try {
            Statement statement = getConnection().createStatement();
            System.out.println("Creating Table: Users");
            statement.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, username TEXT NOT NULL UNIQUE, password TEXT)");

            System.out.println("Adding user: testUser1, password1");
            statement.execute("INSERT INTO Users (username, password) VALUES ('testUser1', 'password1')");

            System.out.println("Adding user: testUser2, password2");
            statement.execute("INSERT INTO Users (username, password) VALUES ('testUser2', 'password2')");
        } catch (Exception e) {
            System.out.println("Error initializing test database");
        }
    }

    public void initializeDatabase() {
        try {
            Statement statement = getConnection().createStatement();
            System.out.println("Creating Table: Users");
            statement.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, username TEXT NOT NULL UNIQUE, password TEXT)");

            System.out.println("Adding user: user1, password1");
            statement.execute("INSERT INTO Users (username, password) VALUES ('user1', 'password1')");

            System.out.println("Adding user: user2, password2");
            statement.execute("INSERT INTO Users (username, password) VALUES ('user2', 'password2')");
        } catch (Exception e) {
            System.out.println("Error initializing database");
        }
    }
}
