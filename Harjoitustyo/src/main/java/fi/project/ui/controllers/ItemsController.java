package fi.project.ui.controllers;

import fi.project.domain.services.UserService;
import fi.project.domain.Component;
import fi.project.domain.services.ComponentService;
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
 * This class has the functionality of items page.
 */
public class ItemsController implements Initializable {

    private UserService userService = new UserService();
    private ComponentService componentService = new ComponentService();
    private Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private Alert confirmRemoveAlert = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    Text userLoggedInText;
    @FXML
    Button itemsButton;
    @FXML
    Button addItemButton;
    @FXML
    Button logoutButton;
    @FXML
    Button editButton;
    @FXML
    Button removeButton;

    @FXML
    TableView<Component> componentTable;
    @FXML
    TableColumn<Component, String> typeColumn;
    @FXML
    TableColumn<Component, String> modelColumn;
    @FXML
    TableColumn<Component, String> manufacturerColumn;
    @FXML
    TableColumn<Component, String> serialNumberColumn;

    /**
     * Changes scene to add items page.
     */
    public void handleAddItemButtonClick() throws IOException {
        Stage stage = (Stage) addItemButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/addItem.fxml"));
        stage.setTitle("Add item");
        stage.setScene(new Scene(parent));
    }

    /**
     * Logs out user and sets scene to login page.
     */
    public void handleLogoutButtonClick() throws IOException {
        logoutAlert.setTitle("Sign out");
        logoutAlert.setHeaderText("Are you sure you want to sign out?");
        Optional<ButtonType> result = logoutAlert.showAndWait();

        if (result.get() == ButtonType.OK) {
            userService.logoutUser();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            stage.setScene(new Scene(parent));
        }
    }

    /**
     * Removes selected component from database
     */
    public void handleRemoveButtonClick() {
        Component component = componentTable.getSelectionModel().getSelectedItem();
        if (component == null) {
            errorAlert.setHeaderText("Error removing component");
            errorAlert.setContentText("Please select component from the table");
            errorAlert.showAndWait();
            return;
        }

        confirmRemoveAlert.setHeaderText("Are you sure you want to remove this component?");
        Optional<ButtonType> result = confirmRemoveAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                componentService.delete(component.getSerialNumber());
                initializeComponentTable();
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("Error deleting component");
            }
        }
    }

    /**
     * Shows alert that button does not work yet
     */
    public void handleEditButtonClick() {
        errorAlert.setContentText("This button does not work yet");
        errorAlert.showAndWait();
    }

    /**
     * Initializes component table and adds components from database to it
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String text = "Logged in as: " + UserService.loggedUser;
        userLoggedInText.setText(text);

        typeColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("type"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("model"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("manufacturer"));
        serialNumberColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("serialNumber"));

        initializeComponentTable();
    }

    private void initializeComponentTable() {
        ObservableList<Component> components = FXCollections.observableArrayList();
        try {
            components.addAll(componentService.getAll());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        componentTable.setItems(components);
    }
}
