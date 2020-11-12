package ui.scenes;

import domain.Database;
import domain.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class RegisterPage extends GridPane {

    public Button returnButton = new Button("Return");
    public Button createUserButton = new Button("Register");

    public TextField usernameField = new TextField();
    public PasswordField passwordField = new PasswordField();

    public RegisterPage() {
        super.setAlignment(Pos.CENTER);
        super.setHgap(10);
        super.setVgap(10);
        super.setPadding(new Insets(10, 10, 10, 10));

        Text registerPageTitle = new Text("Create new account");
        registerPageTitle.setFont(Font.font(20));
        super.add(registerPageTitle, 1, 0, 3, 1);

        Label username = new Label("Username:");
        Label password = new Label("Password:");
        super.add(username, 1,1);
        super.add(password, 1, 2);

        super.add(usernameField,2,1);
        super.add(passwordField, 2, 2);

        super.add(returnButton, 1, 3);
        super.add(createUserButton, 2, 3);
    }
}
