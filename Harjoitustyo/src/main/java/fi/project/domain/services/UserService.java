package fi.project.domain.services;

import fi.project.dao.UserDao;
import fi.project.domain.Database;
import fi.project.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class has methods to add, remove, edit, get and validate users
 */
public class UserService implements UserDao {

    public static String loggedUser = null;
    private Database database;

    public UserService() {
        this.database = new Database();
    }

    /**
     * Adds new user to the database
     * @param user User to add into database
     */
    @Override
    public void create(User user) {
        if (user.getUsername().length() < 4 || user.getPassword().length() < 6) {
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

    /**
     * Gets user from the database by the username given as parameter
     * @param username Input given by user
     * @throws ClassNotFoundException if method getAll throws exception
     * @throws SQLException if method getAll throws exception
     */
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

    /**
     * Gets all users from the database.
     * @return List of users found from the database
     * @throws ClassNotFoundException if getConnection throws exception
     * @throws SQLException if getConnection throws exception
     */
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
        return users;
    }

    /**
     * Verifies that the user exists and has correct username and password
     * @param userToCheck User to verify
     * @return True if user exists and has correct credentials, else returns false
     * @throws ClassNotFoundException if method getUser throws exception
     * @throws SQLException if method getUser throws exception
     */
    public boolean validCredentials(User userToCheck) throws SQLException, ClassNotFoundException {
        User user = getUser(userToCheck.getUsername());
        if (user == null) {
            System.out.println("User does not exist.");
            return false;
        }
        boolean usernameIsCorrect = userToCheck.getUsername().equals(user.getUsername());
        boolean passwordIsCorrect = userToCheck.getPassword().equals(user.getPassword());
        if (usernameIsCorrect && passwordIsCorrect) {
            return true;
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    /**
     * Logs the user in if the user given as parameter has correct credentials
     * @param user User to log in
     * @throws ClassNotFoundException if method validCredentials throws exception
     * @throws SQLException if method validCredentials throws exception
     */
    public void loginUser(User user) throws SQLException, ClassNotFoundException {
        if (validCredentials(user)) {
            loggedUser = user.getUsername();
        }
    }

    /**
     * Logs out the user
     */
    public void logoutUser() {
        loggedUser = null;
    }
}