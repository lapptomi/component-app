package fi.project.ui.controllers;

import fi.project.domain.services.UserService;
import fi.project.domain.Database;
import fi.project.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;


public class LoginControllerTest {

    Database database;
    UserService userService;

    @Before
    public void setUp() {
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
