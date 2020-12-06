package fi.project.ui.controllers;

import fi.project.domain.services.UserService;
import fi.project.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


/**
 * This class has the functionality of log in page.
 */
public class LoginController {

    private UserService userService = new UserService();

    @FXML
    Button loginButton;
    @FXML
    Button registerButton;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;


    Alert alert = new Alert(Alert.AlertType.ERROR);

    /**
     * Logs user in if correct credentials are given.
     * This method is called when log in button is clicked.
     */
    @FXML
    public void handleLoginButtonClick() throws SQLException, ClassNotFoundException, IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = new User(username, password);

        if (!userService.validCredentials(user)) {
            alert.setHeaderText("Invalid username or password.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        } else if (userService.validCredentials(user)) {
            userService.loginUser(user);
        }
        if (UserService.loggedUser == null) {
            return;
        }

        Stage stage = (Stage) loginButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/items.fxml"));
        stage.setScene(new Scene(parent));
        stage.setTitle("Items");
        stage.setResizable(false);
    }

    /**
     * Sets scene to register page when sign up button is clicked.
     */
    public void handleRegisterButtonClick() throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
        stage.setTitle("Sign up");
        stage.setScene(new Scene(parent));
    }
}
