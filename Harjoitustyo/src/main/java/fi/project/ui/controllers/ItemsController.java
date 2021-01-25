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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This class has the functionality of the items page
 */
public class ItemsController implements Initializable {

    private UserService userService = new UserService();
    private ComponentService componentService = new ComponentService();
    private Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    Text userLoggedInText;
    @FXML
    Button itemsButton;
    @FXML
    Button addItemButton;
    @FXML
    Button logoutButton;
    @FXML
    Button saveButton;
    @FXML
    Button removeButton;
    @FXML
    Button usersButton;

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
     *
     * @throws IOException if addItem.fxml is not found.
     */
    public void handleAddItemButtonClick() throws IOException {
        Stage stage = (Stage) addItemButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/addItem.fxml"));
        stage.setTitle("Add item");
        stage.setScene(new Scene(parent));
    }

    /**
     * Changes scene to users page.
     *
     * @throws IOException if addItem.fxml is not found.
     */
    public void handleUsersButtonClick() throws IOException {
        Stage stage = (Stage) usersButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/users.fxml"));
        stage.setTitle("Users");
        stage.setScene(new Scene(parent));
    }

    /**
     * Logs out the user and sets the scene to the login page.
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
     * Removes selected component from the database
     */
    public void handleRemoveButtonClick() {
        Component component = componentTable.getSelectionModel().getSelectedItem();
        if (component == null) {
            errorAlert.setHeaderText("Error removing component");
            errorAlert.setContentText("Please select component from the table");
            errorAlert.showAndWait();
            return;
        }
        confirmationAlert.setHeaderText("Are you sure you want to remove this component?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
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
     * Checks that the component values are valid
     */
    private boolean validCredentials(Component component) {
        return component.getType() != null
                && component.getModel().length() > 0
                && component.getManufacturer().length() > 0
                && component.getSerialNumber().length() > 0
                && !component.getSerialNumber().contains(" ");
    }

    /**
     * Saves the changes on the components to the database
     */
    public void handleSaveButtonClick() {
        boolean componentsAreValid = true;
        List<Component> components = componentTable.getItems();
        for (Component component : components) {
            if (!validCredentials(component)) {
                componentsAreValid = false;
                break;
            }
        }

        confirmationAlert.setHeaderText("Save changes?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.get() == ButtonType.OK && componentsAreValid) {
            componentService.saveChanges(components);
        } else {
            errorAlert.setHeaderText("Error saving changes");
            errorAlert.setContentText("Check that fields are not empty");
            errorAlert.showAndWait();
        }
        initializeComponentTable();
    }

    /**
     * Initializes the component table and adds components from the database to it
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLoggedInText.setText("Logged in as: " + UserService.loggedUser);
        initTypeColumn();
        initModelColumn();
        initManufacturerColumn();
        initSerialNumberColumn();
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

    private void initTypeColumn() {
        typeColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("type"));
        typeColumn.setCellFactory(TextFieldTableCell.<Component>forTableColumn());
        typeColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Component, String> t) -> {
                    ((Component) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setType(t.getNewValue());
                }
        );
    }

    private void initModelColumn() {
        modelColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("model"));
        modelColumn.setCellFactory(TextFieldTableCell.<Component>forTableColumn());
        modelColumn.setOnEditCommit(
            (TableColumn.CellEditEvent<Component, String> t) -> {
                ((Component) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setModel(t.getNewValue());
            }
        );
    }

    private void initManufacturerColumn() {
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("manufacturer"));
        manufacturerColumn.setCellFactory(TextFieldTableCell.<Component>forTableColumn());
        manufacturerColumn.setOnEditCommit(
            (TableColumn.CellEditEvent<Component, String> t) -> {
                ((Component) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setManufacturer(t.getNewValue());
            }
        );
    }

    private void initSerialNumberColumn() {
        serialNumberColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("serialNumber"));
        serialNumberColumn.setCellFactory(TextFieldTableCell.<Component>forTableColumn());
        serialNumberColumn.setOnEditCommit(
            (TableColumn.CellEditEvent<Component, String> t) -> {
                ((Component) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setSerialNumber(t.getNewValue());
            }
        );
    }
}
