package controllers;

import domain.Component;
import domain.ComponentService;
import domain.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemsController implements Initializable {

    private UserService userService = new UserService();
    private ComponentService componentService = new ComponentService();
    private Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    Text userLoggedInText;
    @FXML
    Button itemsButton;
    @FXML
    Button addItemButton;
    @FXML
    Button logoutButton;

    @FXML
    TableView<Component> componentTable;
    @FXML
    TableColumn<Component, String> typeColumn;
    @FXML
    TableColumn<Component, String> modelColumn;
    @FXML
    TableColumn<Component, String> manufacturerColumn;
    @FXML
    TableColumn<Component, String> serialNumberColumn;


    public void handleAddItemButtonClick() throws IOException {
        Stage stage = (Stage) addItemButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/addItem.fxml"));
        stage.setTitle("Add item");
        stage.setScene(new Scene(parent));
    }

    public void handleLogoutButtonClick() throws IOException {
        logoutAlert.setTitle("Logout");
        logoutAlert.setHeaderText("Are you sure you want to log out?");
        Optional<ButtonType> result = logoutAlert.showAndWait();

        if (result.get() == ButtonType.OK) {
            userService.logoutUser();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            stage.setScene(new Scene(parent));
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String text = "Logged in as: " + UserService.loggedUser;
        userLoggedInText.setText(text);

        typeColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("type"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("model"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("manufacturer"));
        serialNumberColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("serialNumber"));

        ObservableList<Component> components = FXCollections.observableArrayList();
        try {
            componentService.getAll().forEach(component ->
                components.add(component);
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        componentTable.setItems(components);
    }
}
