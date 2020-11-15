package controllers;

import domain.User;
import domain.UserService;
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

public class RegisterController {

    @FXML
    Button returnButton;
    @FXML
    Button createAccountButton;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;

    private final UserService userService = new UserService();

    Alert invalidCredentialsAlert = new Alert(Alert.AlertType.ERROR);
    Alert userCreatedAlert = new Alert(Alert.AlertType.INFORMATION);


    public RegisterController()  {
        super();
    }

    public void handleReturnButtonClick() throws IOException {
        Stage stage = (Stage) returnButton.getScene().getWindow();;
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage.setScene(new Scene(parent));
    }

    public void handleCreateAccountButtonClick() throws SQLException, ClassNotFoundException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean usernameIsNotUnique = userService.getUser(username) != null;

        if (username.length() < 3 || password.length() < 6) {
            invalidCredentialsAlert.setTitle("Error");
            invalidCredentialsAlert.setHeaderText("Username or password too short.");
            invalidCredentialsAlert.setContentText("Please try again.");
            invalidCredentialsAlert.showAndWait();
        } else if (usernameIsNotUnique) {
            invalidCredentialsAlert.setTitle("Error");
            invalidCredentialsAlert.setHeaderText("Username "+username+" is already taken.");
            invalidCredentialsAlert.setContentText("Please try again.");
            invalidCredentialsAlert.showAndWait();
        } else {
            userService.create(new User(username, password));
            userCreatedAlert.setHeaderText("New user "+username+" created!");
            userCreatedAlert.showAndWait();
            usernameField.clear();
            passwordField.clear();
        }
    }
}
