package ir.visoft.accounting.ui;

import ir.visoft.accounting.util.FileUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    private static Logger log = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        //create bill directory on desktop if does not exists!
        FileUtil.createDirectory(System.getProperty("user.home") + "\\Desktop\\bill");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.message", new Locale("fa"), new UTF8Control());
        Parent root = FXMLLoader.load(getClass().getResource("/ir/visoft/accounting/ui/view/login.fxml"), resourceBundle);

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
