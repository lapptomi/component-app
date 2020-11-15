package controllers;

import domain.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    Text helloWorldText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String text = "User "+UserService.loggedUser+" logged in";
        helloWorldText.setText(text);
    }
}
