package fi.project.services;

import fi.project.domain.Database;
import fi.project.domain.User;
import fi.project.domain.services.UserService;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserServiceTest {

    Database database;
    UserService userService;

    @Before
    public void setUp() {
        System.setProperty("exec.args", "test");

        this.database = new Database();
        this.userService = new UserService();
        this.database.initializeTestDatabase();

        UserService.loggedUser = null;
    }

    @Test
    public void testDatabaseIsInitializedRightOnStart() throws SQLException, ClassNotFoundException {
        User user1 = userService.getAll().get(0);
        User user2 = userService.getAll().get(1);

        assertEquals("testUser1", user1.getUsername());
        assertEquals("password1", user1.getPassword());

        assertEquals("testUser2", user2.getUsername());
        assertEquals("password2", user2.getPassword());

        assertEquals(2, userService.getAll().size());
    }

    @Test
    public void userCanBeCreatedWithValidCredentials() throws SQLException, ClassNotFoundException {
        assertEquals(2, userService.getAll().size());
        userService.create(new User("username", "password"));
        assertEquals(3, userService.getAll().size());
    }

    @Test
    public void creatingUsersWithTooShortPasswordFails() throws SQLException, ClassNotFoundException {
        assertEquals(2, userService.getAll().size());
        userService.create(new User("username", "123"));
        assertEquals(2, userService.getAll().size());
    }

    @Test
    public void creatingUsersWithTooShortUsernameFails() throws SQLException, ClassNotFoundException {
        assertEquals(2, userService.getAll().size());
        userService.create(new User("12", "password"));
        assertEquals(2, userService.getAll().size());
    }

    @Test
    public void creatingUsersWithEmptyCredentialsFails() throws SQLException, ClassNotFoundException {
        assertEquals(2, userService.getAll().size());
        userService.create(new User("", ""));
        assertEquals(2, userService.getAll().size());
    }

    @Test
    public void userLoginWorksWithCorrectCredentials() throws SQLException, ClassNotFoundException {
        assertNull(UserService.loggedUser);

        User userToLogIn = userService.getAll().get(0);
        userService.loginUser(userToLogIn);

        assertNotNull(UserService.loggedUser);
    }

    @Test
    public void loginFailsWithNonExistingUser() throws SQLException, ClassNotFoundException {
        assertNull(UserService.loggedUser);

        User nonExistingUser = new User("randomUsername", "randomPassword");
        userService.loginUser(nonExistingUser);

        assertFalse(userService.validCredentials(nonExistingUser));
        assertNull(UserService.loggedUser);
    }

    @Test
    public void loginFailsWithWrongPassword() throws SQLException, ClassNotFoundException {
        assertNull(UserService.loggedUser);

        User userWithWrongPassword = userService.getAll().get(0);
        userWithWrongPassword.setPassword("wrongpassword");

        userService.loginUser(userWithWrongPassword);
        assertNull(UserService.loggedUser);
        assertFalse(userService.validCredentials(userWithWrongPassword));
    }
}
