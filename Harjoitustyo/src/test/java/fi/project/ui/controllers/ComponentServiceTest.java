package fi.project.ui.controllers;

import fi.project.domain.Component;
import fi.project.domain.services.ComponentService;
import fi.project.domain.Database;
import fi.project.domain.services.UserService;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ComponentServiceTest {

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

    @Test
    public void componentsCanBeDeletedFromDatabase() throws SQLException, ClassNotFoundException {
        List<Component> componentsOnStart = componentService.getAll();
        int listSizeOnStart = componentsOnStart.size();
        componentService.delete(componentsOnStart.get(0).getSerialNumber());
        assertEquals(listSizeOnStart - 1, componentService.getAll().size());
    }

    @Test
    public void componentWithNullTypeIsNotAddedToDatabase() throws SQLException, ClassNotFoundException {
        Component invalidComponent = new Component(null, "model", "manufacturer", "randomserial");
        int listSizeOnStart = componentService.getAll().size();
        componentService.create(invalidComponent);
        assertEquals(listSizeOnStart, componentService.getAll().size());
    }

    @Test
    public void componentWithEmptyManufacturerIsNotAddedToDatabase() throws SQLException, ClassNotFoundException {
        Component invalidComponent = new Component("CPU", "model", "", "randomserial");
        int listSizeOnStart = componentService.getAll().size();
        componentService.create(invalidComponent);
        assertEquals(listSizeOnStart, componentService.getAll().size());
    }

    @Test
    public void componentWithEmptySerialNumberIsNotAddedToDatabase() throws SQLException, ClassNotFoundException {
        Component invalidComponent = new Component("GPU", "model", "manufacturer", "");
        int listSizeOnStart = componentService.getAll().size();
        componentService.create(invalidComponent);
        assertEquals(listSizeOnStart, componentService.getAll().size());
    }

    @Test
    public void componentsWithoutUniqueSerialNumberAreNotAddedToDatabase() throws SQLException, ClassNotFoundException {
        Component component = new Component("GPU", "model", "manufacturer", "1234");
        Component component2 = new Component("CPU", "randomModel", "MSI", "1234");

        int listSizeOnStart = componentService.getAll().size();
        componentService.create(component);
        assertEquals(listSizeOnStart + 1, componentService.getAll().size());
        componentService.create(component2);
        assertEquals(listSizeOnStart + 1, componentService.getAll().size());
    }

    @Test
    public void componentWithNullSerialNumberIsNotAddedToDatabase() throws SQLException, ClassNotFoundException {
        Component invalidComponent = new Component("GPU", "model", "manufacturer", null);
        int listSizeOnStart = componentService.getAll().size();
        componentService.create(invalidComponent);
        assertEquals(listSizeOnStart, componentService.getAll().size());
    }

    @Test
    public void componentWithSpacesOnSerialNumberIsNotAddedToDatabase() throws SQLException, ClassNotFoundException {
        Component invalidComponent = new Component("GPU", "model", "manufacturer", "123 123");
        int listSizeOnStart = componentService.getAll().size();
        componentService.create(invalidComponent);
        assertEquals(listSizeOnStart, componentService.getAll().size());
    }
}
