package domain;

import dao.UserDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService extends Database implements UserDao {

    private final Statement statement;

    public UserService() throws ClassNotFoundException, SQLException {
        this.statement = super.getConnection().createStatement();
    }

    @Override
    public void create(User user) {
        try {
            String query = "INSERT INTO Users (username, password) VALUES ('%s', '%s')";
            statement.execute(String.format(query, user.getUsername(), user.getPassword()));
            System.out.println("User added to database");
        } catch (SQLException e) {
            System.out.println("Error adding user");
        }
    }

    @Override
    public User getUser(int id) {
        try {
            String query = String.format("SELECT * FROM Users WHERE id = %d", id);
            statement.execute(query);

            ResultSet resultSet = statement.getResultSet();
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            return new User(username, password);
        } catch (SQLException e) {
            System.out.println("Error getting user with id "+id);
        }
        return null;
    }

    @Override
    public User update(int id) {
        return null;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM Users");
            while (result.next()) {
                String username = result.getString("username");
                String password = result.getString("password");
                users.add(new User(username, password));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("SQLExeption");
        }
        return users.size() > 0 ? users : new ArrayList<>();
    }

    public void addTestUsers() {
        try {
            statement.execute("INSERT INTO Users (username, password) VALUES ('user1', 'password1')");
            statement.execute("INSERT INTO Users (username, password) VALUES ('user2', 'password2')");
        } catch (SQLException throwables) {
            System.out.println("Error initializing database");
        }
    }
}