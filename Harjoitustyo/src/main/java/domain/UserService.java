package domain;

import dao.UserDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService implements UserDao {

    public static String loggedUser = null;

    @Override
    public void create(User user) {
        try {
            Statement s = Database.getConnection().createStatement();
            String query = "INSERT INTO Users (username, password) VALUES ('%s', '%s')";
            s.execute(String.format(query, user.getUsername(), user.getPassword()));
            System.out.println("User added to database!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error adding user");
        }
    }

    @Override
    public User getUser(String username) throws SQLException, ClassNotFoundException {
        User user = null;
        for (User u : getAll()) {
            if (u.getUsername().equals(username)) {
                user = u;
            }
        }
        return user;
    }

    @Override
    public User update(int id) {
        return null;
    }

    public static List<User> getAll() throws SQLException, ClassNotFoundException {
        Statement statement = Database.getConnection().createStatement();
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
        }
        return users.size() > 0 ? users : new ArrayList<>();
    }

    public boolean validCredentials(String username, String password) throws SQLException, ClassNotFoundException {
        User user = getUser(username);
        if (user == null) {
            System.out.println("User does not exist.");
            return false;
        }
        boolean usernameIsCorrect = user.getUsername().equals(username);
        boolean passwordIsCorrect = user.getPassword().equals(password);

        if (usernameIsCorrect && passwordIsCorrect) {
            return true;
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    public void login(String username) {
        loggedUser = username;
    }
}