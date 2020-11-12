package domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final Connection database;

    public Database(int connectionType) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        String connection = null;

        if (connectionType == 1) {
            connection =  "jdbc:sqlite::memory:";
        } else if (connectionType == 2){
            connection = "jdbc:sqlite:testi.db";
        } else {
            System.out.println("Error setting connetion type");
            System.exit(-1);
        }
        this.database = DriverManager.getConnection(connection);
    }

    public void addUser(User user) {
        try {
            Statement statement = database.createStatement();
            String query = "INSERT INTO Users (username, password) VALUES ('%s', '%s')";
            statement.execute(String.format(query, user.getUsername(), user.getPassword()));
        } catch (SQLException e) {
            System.out.println("Error adding user");
        }
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = database.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Users");
            while (result.next()) {
                String username = result.getString("username");
                String password = result.getString("password");
                users.add(new User(username, password));
            }
        } catch (SQLException e) {
            System.out.println("SQLExeption");
            return new ArrayList<>();
        }
        return users;
    }

    public void initializeDatabase() throws ClassNotFoundException {
       try {
           Statement s = database.createStatement();
           s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)");
           s.execute("INSERT INTO Users (username, password) VALUES ('user1', 'password1')");
           s.execute("INSERT INTO Users (username, password) VALUES ('user2', 'password2')");
       } catch (SQLException throwables) {
           System.out.println("Error initializing database");
       }
   }
}
