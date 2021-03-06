package fi.project.ui.controllers;

import fi.project.domain.User;
import fi.project.domain.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class has the functionality of the users page
 */
public class UsersController implements Initializable {

    private UserService userService = new UserService();
    private Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    Text userLoggedInText;
    @FXML
    Button itemsButton;
    @FXML
    Button addItemButton;
    @FXML
    Button logoutButton;

    @FXML
    TableView<User> userTable;
    @FXML
    TableColumn<User, String> usernameColumn;

    /**
     * Changes scene to the add items page.
     * @throws IOException if addItem.fxml is not found.
     */
    public void handleAddItemButtonClick() throws IOException {
        Stage stage = (Stage) addItemButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/addItem.fxml"));
        stage.setTitle("Add item");
        stage.setScene(new Scene(parent));
    }

    /**
     * Logs out the user and sets scene to the login page.
     * @throws IOException if login.fxml is not found.
     */
    public void handleLogoutButtonClick() throws IOException {
        confirmationAlert.setTitle("Sign out");
        confirmationAlert.setHeaderText("Are you sure you want to sign out?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.get() == ButtonType.OK) {
            userService.logoutUser();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            stage.setScene(new Scene(parent));
        }
    }

    /**
     * Changes scene to the items page when the items button is clicked
     * @throws IOException if items.fxml is not found
     */
    public void handleItemsButtonClick() throws IOException {
        Stage stage = (Stage) itemsButton.getScene().getWindow();
        stage.setTitle("Items");
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/items.fxml"));
        stage.setScene(new Scene(parent));
    }

    /**
     * Initializes the component table and adds components from the database to it
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String text = "Logged in as: " + UserService.loggedUser;
        userLoggedInText.setText(text);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        initializeUsersTable();
    }

    private void initializeUsersTable() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            users.addAll(userService.getAll());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        userTable.setItems(users);
    }
}
