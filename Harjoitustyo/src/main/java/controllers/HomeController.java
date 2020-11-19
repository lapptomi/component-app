package controllers;

import domain.Component;
import domain.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HomeController {

    private UserService userService = new UserService();
    private Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    Text homePageTitle;
    @FXML
    Text userLoggedInText;
    @FXML
    Button itemsButton;
    @FXML
    Button addItemButton;
    @FXML
    Button logoutButton;

    public void handleAddItemButtonClick() {
        System.out.println("Clicked add item button");
    }

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

    public void handleItemsButtonClick() throws IOException {
        System.out.println("Clicked items button");
        Stage stage = (Stage) itemsButton.getScene().getWindow();
        stage.setTitle("Items");
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/items.fxml"));
        stage.setScene(new Scene(parent));
    }
}
