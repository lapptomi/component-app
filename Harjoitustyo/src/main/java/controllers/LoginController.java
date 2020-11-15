package controllers;

import domain.Database;
import domain.User;
import domain.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController extends UserService {

    @FXML
    Button loginButton;
    @FXML
    Button registerButton;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;

    Alert alert = new Alert(Alert.AlertType.ERROR);

    public void handleLoginButtonClick() throws SQLException, ClassNotFoundException, IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!super.validCredentials(username, password)) {
            alert.setTitle("Error");
            alert.setHeaderText("Invalid username or password.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        } else {
            login(username);
        }

        if (UserService.loggedUser == null) {
            return;
        }

        Stage stage = (Stage) loginButton.getScene().getWindow();;
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        stage.setScene(new Scene(parent));
    }

    @FXML
    private void handleRegisterButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();;
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
        stage.setScene(new Scene(parent));
    }
}
