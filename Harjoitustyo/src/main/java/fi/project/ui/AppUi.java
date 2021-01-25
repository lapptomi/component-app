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
    public void init() {
        this.userService = new UserService();
        this.database = new Database();
        database.deleteTestDbFile();
        database.initializeDatabase();
    }

    @Override
    public void stop() {
        database.deleteTestDbFile();
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
