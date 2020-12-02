package fi.project.ui.controllers;

import fi.project.domain.UserService;
import fi.project.domain.Component;
import fi.project.domain.ComponentService;
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

public class AddItemController implements Initializable {

    private UserService userService = new UserService();
    private ComponentService componentService = new ComponentService();
    private Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private Alert componentCreatedAlert = new Alert(Alert.AlertType.INFORMATION);
    private Alert errorCreatingComponentAlert = new Alert(Alert.AlertType.ERROR);

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
    TextField modelTextField;
    @FXML
    TextField manufacturerTextField;
    @FXML
    TextField serialNumberTextField;
    @FXML
    ComboBox<String> componentTypeBox;

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

    public void handleClearButtonClick() {
        componentTypeBox.setValue("Type");
        modelTextField.clear();
        manufacturerTextField.clear();
        serialNumberTextField.clear();
    }

    public void handleItemsButtonClick() throws IOException {
        Stage stage = (Stage) itemsButton.getScene().getWindow();
        stage.setTitle("Items");
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/items.fxml"));
        stage.setScene(new Scene(parent));
    }

    public void handleAddItemButtonClick() {
        String type = componentTypeBox.getItems().contains(componentTypeBox.getValue())
                ? componentTypeBox.getValue() : null;
        String model = modelTextField.getText();
        String manufacturer = manufacturerTextField.getText();
        String serialNumber = serialNumberTextField.getText();

        if (validCredentials(type, model, manufacturer, serialNumber)) {
            Component newComponent = new Component(type, model, manufacturer, serialNumber);
            componentService.create(newComponent);
            componentCreatedAlert.setHeaderText("Component added to database!");
            handleClearButtonClick();
            componentCreatedAlert.showAndWait();
        } else {
            errorCreatingComponentAlert.setHeaderText("Error adding component to database!");
            errorCreatingComponentAlert.setContentText("Please try again.");
            errorCreatingComponentAlert.showAndWait();
        }
    }

    public boolean validCredentials(String type, String model, String manufacturer, String serialNumber) {
        return type != null && model.length() > 0 && manufacturer.length() > 0 && serialNumber.length() > 0 && !serialNumber.contains(" ");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLoggedInText.setText("Logged in as: " + UserService.loggedUser);
        String[] componentTypes = {"CPU", "GPU", "HDD", "Motherboard", "PSU", "RAM", "SSD"};
        componentTypeBox.setItems(FXCollections.observableArrayList(componentTypes));
    }
}
