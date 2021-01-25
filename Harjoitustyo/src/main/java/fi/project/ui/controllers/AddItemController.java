package fi.project.ui.controllers;

import fi.project.domain.services.UserService;
import fi.project.domain.Component;
import fi.project.domain.services.ComponentService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class has the functionality of the add item page
 */
public class AddItemController implements Initializable {

    private UserService userService = new UserService();
    private ComponentService componentService = new ComponentService();
    private Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    Text userLoggedInText;
    @FXML
    Button itemsButton;
    @FXML
    Button clearButton;
    @FXML
    Button addItemButton;
    @FXML
    Button logoutButton;
    @FXML
    Button usersButton;

    @FXML
    TextField modelTextField;
    @FXML
    TextField manufacturerTextField;
    @FXML
    TextField serialNumberTextField;
    @FXML
    ComboBox<String> componentTypeBox;

    /**
     * Logs out the user and sets scene to the login page
     * @throws IOException if login.fxml is not found
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
     * Clears all fields when the clear button is clicked
     */
    public void handleClearButtonClick() {
        componentTypeBox.setValue("Type");
        modelTextField.clear();
        manufacturerTextField.clear();
        serialNumberTextField.clear();
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
     * Changes scene to the users page when the users button is clicked
     * @throws IOException if addItem.fxml is not found.
     */
    public void handleUsersButtonClick() throws IOException {
        Stage stage = (Stage) addItemButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/users.fxml"));
        stage.setTitle("Users");
        stage.setScene(new Scene(parent));
    }

    /**
     * Checks that values given as parameters valid
     */
    private boolean validCredentials(Component component) {
        return component.getType() != null
                && component.getModel().length() > 0
                && component.getManufacturer().length() > 0
                && component.getSerialNumber().length() > 0
                && !component.getSerialNumber().contains(" ");
    }

    /**
     * Adds new component to the database when the add item button is clicked
     */
    public void handleAddItemButtonClick() {
        String type = componentTypeBox.getItems().contains(componentTypeBox.getValue())
                ? componentTypeBox.getValue()
                : null;
        String model = modelTextField.getText();
        String manufacturer = manufacturerTextField.getText();
        String serialNumber = serialNumberTextField.getText();

        Component componentToAdd = new Component(type, model, manufacturer, serialNumber);
        if (validCredentials(componentToAdd)) {
            Component newComponent = new Component(type, model, manufacturer, serialNumber);
            componentService.create(newComponent);
            informationAlert.setHeaderText("Component added to database!");
            handleClearButtonClick();
            informationAlert.showAndWait();
        } else {
            errorAlert.setHeaderText("Error adding component to database!");
            errorAlert.setContentText("Please try again.");
            errorAlert.showAndWait();
        }
    }

    /**
     * Sets component type values to combo box
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLoggedInText.setText("Logged in as: " + UserService.loggedUser);
        String[] componentTypes = { "CPU", "GPU", "HDD", "Motherboard", "PSU", "RAM", "SSD" };
        componentTypeBox.setItems(FXCollections.observableArrayList(componentTypes));
    }
}
