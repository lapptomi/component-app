package ui;

import domain.Database;
import domain.User;
import domain.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AppUi extends Application {

    @Override
    public void init() throws Exception {
        Database.initializeDatabase();
        System.out.println("Users in database:");
        for (User user : UserService.getAll()) {
            System.out.println("Username: "+user.getUsername()+", Password: "+user.getPassword()+"");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Login");
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene loginScene = new Scene(loginRoot);

        stage.setScene(loginScene);
        stage.show();
    }

    public static void run(String[] args) {
        launch(args);
    }
}
