package fi.project.ui.controllers;

import fi.project.domain.Component;
import fi.project.domain.ComponentService;
import fi.project.domain.Database;
import fi.project.domain.UserService;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import static org.junit.Assert.*;


public class ItemsControllerTest {

    Database database;
    UserService userService;
    ComponentService componentService;

    @Before
    public void setUp() {
        this.database = new Database();
        this.userService = new UserService();
        this.componentService = new ComponentService();
        database.initializeTestDatabase();
        UserService.loggedUser = null;
    }

    @Test
    public void componentsCanBeAddedToDatabase() throws SQLException, ClassNotFoundException {
        int componentsOnStart = componentService.getAll().size();
        Component component = new Component("1", "2", "3", "4");
        componentService.create(component);
        assertEquals(componentsOnStart + 1, componentService.getAll().size());
    }

    @Test
    public void componentsCanBeFetchedFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(5, componentService.getAll().size());
    }

    @Test
    public void componentsWithEmptyFieldsAreNotAddedToDatabase() throws SQLException, ClassNotFoundException {
        int componentsOnStart = componentService.getAll().size();
        Component component = new Component("1", "2", "3", "4");
        componentService.create(component);
        assertEquals(componentsOnStart + 1, componentService.getAll().size());
        Component invalidComponent = new Component("", "", "", "");
        componentService.create(invalidComponent);
        assertEquals(componentsOnStart + 1, componentService.getAll().size());
    }
}
