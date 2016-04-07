package ir.visoft.accounting.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.image.Image;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.message", new Locale("fa"), new UTF8Control());
        Parent root = FXMLLoader.load(getClass().getResource("./view/login.fxml"), resourceBundle);

        primaryStage.setTitle(resourceBundle.getString("waterSubSys"));
        root.getStylesheets().add("/css/login.css");

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.getIcons().add(new Image(("file:logo.jpg")));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
