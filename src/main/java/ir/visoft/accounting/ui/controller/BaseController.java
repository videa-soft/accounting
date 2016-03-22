package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.entity.BaseEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author Amir
 */
public abstract class BaseController {

    private static Logger log = Logger.getLogger(BaseEntity.class.getName());


    protected Stage getStage(ActionEvent event) {
        return (Stage)((Button)event.getSource()).getScene().getWindow();
    }


    protected void changeScene(String viewName, Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewName));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            stage.setScene(new Scene(root, 1200, 700));
            stage.show();
        } else {
            log.error("Root element is null on setting: " + viewName);
        }
    }

    public void init(Object data) {
        throw new UnsupportedOperationException();
    }
}
