package controllers;

import domain.UserService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private UserService userService = new UserService();

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
    @FXML
    ListView<String> itemsList;

    public void handleAddItemButtonClick() {
        System.out.println("Clicked add item button");
    }

    public void handleLogoutButtonClick() {
        System.out.println("Clicked logout button");
    }

    public void handleItemsButtonClick() {
        System.out.println("Clicked items button");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String text = "Logged in as: "+UserService.loggedUser;
        userLoggedInText.setText(text);
        homePageTitle.setText("App");

        List<String> list = FXCollections.observableArrayList(
                "Item 1", "Item 2", "Item 3", "Item 4"
        );
        list.forEach(item -> itemsList.getItems().add(item));
    }
}
