package fi.project.ui;

import fi.project.domain.Database;
import fi.project.domain.User;
import fi.project.domain.services.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class AppUi extends Application {

    Database database;
    UserService userService;

    @Override
    public void init() throws Exception {
        this.userService = new UserService();
        this.database = new Database();

        database.initializeTestDatabase();
        database.initializeDatabase();

        System.out.println("Users in database:");
        for (User user : userService.getAll()) {
            String username = user.getUsername();
            String password = user.getPassword();
            System.out.println(String.format("Username: %s Password: %s", username, password));
        }
    }

    @Override
    public void stop() throws Exception {
        database.initializeTestDatabase();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Login");
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene loginScene = new Scene(loginRoot);

        stage.setScene(loginScene);
        stage.setResizable(false);
        stage.show();
    }

    public static void run(String[] args) {
        launch(args);
    }
}
