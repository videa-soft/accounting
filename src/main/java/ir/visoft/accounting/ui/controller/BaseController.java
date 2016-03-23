package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.entity.BaseEntity;
import ir.visoft.accounting.ui.ApplicationContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Amir
 */
public abstract class BaseController {

    private static Logger log = Logger.getLogger(BaseEntity.class.getName());

    protected Stage stage;


    protected Stage getStage(ActionEvent event) {
        return (Stage)((Button)event.getSource()).getScene().getWindow();
    }


    protected void changeScene(String viewName, Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewName));
        Parent root = null;
        try {
            root = loader.load();
            BaseController controller = loader.<BaseController>getController();
            ApplicationContext.addController(controller);
            controller.setStage(stage);
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

    protected void showOperationError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String title = "Login Error";
        String header = "Operation Exception";
        String content = "There is an error in system operation.";

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void refresh() {
        throw new UnsupportedOperationException();
    }

    protected void refreshView(Class controllerClass) {
        callControllerMethod("refresh", controllerClass);
    }

    protected void callControllerMethod(String methodName, Class controllerClass, Object... args) {
        try {
            BaseController controller = ApplicationContext.getController(controllerClass);
            if(methodName != null && controller != null) {
                Method method = controller.getClass().getMethod(methodName);
                method.invoke(controller, args);
            }
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage());
        } catch (InvocationTargetException e) {
            log.error(e.getMessage());
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
