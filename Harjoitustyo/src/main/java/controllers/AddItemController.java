package controllers;

import domain.UserService;
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
    private Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION);

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
        logoutAlert.setTitle("Logout");
        logoutAlert.setHeaderText("Are you sure you want to log out?");
        Optional<ButtonType> result = logoutAlert.showAndWait();

        if (result.get() == ButtonType.OK) {
            userService.logoutUser();
            Stage stage = (Stage) logoutButton.getScene().getWindow();;
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
        System.out.println("Add item button clicked");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLoggedInText.setText("Logged in as: "+UserService.loggedUser);

        String[] componentTypes = {
                "CPU", "GPU", "PSU", "Motherboard", "RAM", "HDD", "SSD"
        };
        componentTypeBox.setItems(
                FXCollections.observableArrayList(componentTypes)
        );
    }
}
