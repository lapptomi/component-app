package fi.project.ui.controllers;

import fi.project.domain.services.UserService;
import fi.project.domain.User;
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
 * This class has the functionality of the register page
 */
public class RegisterController {

    private UserService userService = new UserService();

    @FXML
    Button returnButton;
    @FXML
    Button signUpButton;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;

    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);

    /**
     * Sets scene to the log in page when the return button is clicked
     * @throws IOException if login.fxml is not found
     */
    public void handleReturnButtonClick() throws IOException {
        Stage stage = (Stage) returnButton.getScene().getWindow();;
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(parent));
    }

    /**
     * Adds new user to the database when the sign up button is clicked
     * if valid credentials are given
     * @throws SQLException if method getUser throws exception.
     * @throws ClassNotFoundException if method getUser throws exception.
     */
    public void handleSignUpButtonClick() throws SQLException, ClassNotFoundException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        errorAlert.setTitle("Error");
        errorAlert.setContentText("Please try again.");

        if (username.length() < 4 || password.length() < 6) {
            errorAlert.setHeaderText("Username or password too short.");
            errorAlert.showAndWait();
        } else if (userService.getUser(username) != null) {
            errorAlert.setHeaderText("Username " + username + " is already taken.");
            errorAlert.showAndWait();
        } else {
            userService.create(new User(username, password));
            informationAlert.setHeaderText("New user " + username + " created!");
            usernameField.clear();
            passwordField.clear();
            informationAlert.showAndWait();
        }
    }
}
