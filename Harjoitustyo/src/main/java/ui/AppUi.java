package ui;

import domain.Database;
import domain.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.scenes.LoginPage;
import ui.scenes.RegisterPage;


public class AppUi extends Application {

    private final RegisterPage registerPage = new RegisterPage();
    private final LoginPage loginPage = new LoginPage();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Login");

        // Database connectionType 1 runs database in memory
        // Database connectionType 2 runs database in file "testi.db"
        Database database = new Database(1);
        if (database.getAll().size() == 0) {
            database.initializeDatabase();
        }
        for (User user : database.getAll()) {
            System.out.println(user.getUsername()+" "+user.getPassword());
        }

        // Login page
        Scene loginPageScene = new Scene(loginPage, 500, 300);
        // Register page
        Scene registerPageScene = new Scene(registerPage, 500, 300);

        // Login page button actions
        loginPage.loginButton.setOnAction(event -> {
            String username = loginPage.usernameField.getText();
            String password = loginPage.passwordField.getText();
            System.out.println(username+" "+password);
        });
        loginPage.registerPageButton.setOnAction(event -> {
            stage.setScene(registerPageScene);
        });

        // Register page button actions
        registerPage.returnButton.setOnAction(event -> {
            stage.setScene(loginPageScene);
        });
        registerPage.createUserButton.setOnAction(event -> {
            String username = registerPage.usernameField.getText();
            String password = registerPage.passwordField.getText();
            System.out.println(username+" "+password);
            database.addUser(new User(username, password));
        });

        stage.setScene(loginPageScene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Stop");
    }

    public static void run(String[] args) {
        launch(args);
    }
}
