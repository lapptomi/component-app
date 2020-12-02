package fi.project.ui.controllers;

import fi.project.domain.Component;
import fi.project.domain.ComponentService;
import fi.project.domain.Database;
import fi.project.domain.UserService;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

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

}
