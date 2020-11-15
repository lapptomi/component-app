package domain;

import java.sql.*;

public class Database {

    public static Connection getConnection() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        String DB_URL = "jdbc:sqlite:testi.db";
        try {
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void initializeDatabase() {
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
