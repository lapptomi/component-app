package ui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoginPage extends GridPane {

    public Alert alert = new Alert(Alert.AlertType.ERROR);
    public Button loginButton = new Button("Login");
    public Button registerPageButton = new Button("Create new account");
    public TextField usernameField = new TextField();
    public PasswordField passwordField = new PasswordField();

    public LoginPage() {
        super.setAlignment(Pos.CENTER);
        super.setHgap(10);
        super.setVgap(10);
        super.setPadding(new Insets(10, 10, 10, 10));

        Text title = new Text("Login");
        title.setFont(Font.font(20));
        super.add(title, 1, 0, 3, 1);
        
        super.add(new Label("Username:"), 1,1);
        super.add(new Label("Password:"), 1, 2);

        super.add(usernameField,2,1);
        super.add(passwordField, 2, 2);

        super.add(loginButton, 1, 3);
        super.add(registerPageButton, 2, 3);

        alert.setTitle("Error");
        alert.setHeaderText("Invalid username or password.");
        alert.setContentText("Please try again.");
    }
}
