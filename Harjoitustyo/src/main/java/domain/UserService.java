package domain;

import dao.UserDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService implements UserDao {

    public static String loggedUser = null;
    private Database database;

    public UserService() {
        this.database = new Database();
    }

    @Override
    public void create(User user) {
        if (user.getUsername().length() < 3 || user.getPassword().length() < 6) {
            return;
        }
        try {
            Statement s = database.getConnection().createStatement();
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
                break;
            }
        }
        return user;
    }

    @Override
    public User update(String Username) {
        return null;
    }

    @Override
    public List<User> getAll() throws ClassNotFoundException, SQLException {
        Statement statement = database.getConnection().createStatement();
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

    public boolean validCredentials(User userToCheck) throws SQLException, ClassNotFoundException {
        User user = getUser(userToCheck.getUsername());
        if (user == null) {
            System.out.println("User does not exist.");
            return false;
        }
        String username = user.getUsername();
        String password = user.getPassword();
        boolean usernameIsCorrect = userToCheck.getUsername().equals(username);
        boolean passwordIsCorrect = userToCheck.getPassword().equals(password);

        if (usernameIsCorrect && passwordIsCorrect) {
            return true;
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    public void login(User user) throws SQLException, ClassNotFoundException {
        if (validCredentials(user)) {
            loggedUser = user.getUsername();
        }
    }
}