package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.entity.BaseEntity;
import ir.visoft.accounting.ui.ApplicationContext;
import ir.visoft.accounting.ui.UTF8Control;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Amir
 */
public abstract class BaseController {

    private static Logger log = Logger.getLogger(BaseEntity.class.getName());

    public static final String VIEW_BASE_URL = "/ir/visoft/accounting/ui/view/";

    protected static final ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.message", new Locale("fa"), new UTF8Control());

    protected Stage stage;
    
    private static final int currentYear = 1395 ;
    

    public static Date convertStringToDate(String dateString) {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (dateString.equals("")) {
            alert.setTitle(resourceBundle.getString("error"));
            alert.setHeaderText("");
            alert.setContentText(resourceBundle.getString("createDate_is_null"));
            alert.showAndWait();
            return null;
        }
        Date date = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = df.parse(dateString);
            if (dateString.indexOf("-") != 4 || dateString.lastIndexOf("-") != 7) {
                alert.setTitle(resourceBundle.getString("error"));
                alert.setHeaderText("");
                alert.setContentText(resourceBundle.getString("format_is_not_correct_example"));
                alert.showAndWait();
                return null;
            }
            Integer year = Integer.parseInt(dateString.subSequence(0, 4).toString());
            Integer mount = Integer.parseInt(dateString.substring(5, 7));
            Integer day = Integer.parseInt(dateString.substring(8, dateString.length()));
            if (year > currentYear || year < 1300) {
                alert.setTitle(resourceBundle.getString("error"));
                alert.setHeaderText("");
                alert.setContentText(resourceBundle.getString("year_is_more_than_current"));
                alert.showAndWait();
                return null;
            } else if (mount > 12) {
                alert.setTitle(resourceBundle.getString("error"));
                alert.setHeaderText("");
                alert.setContentText(resourceBundle.getString("mount_is_not_correct"));
                alert.showAndWait();
                return null;
            } else if (day > checkMonth(mount)) {
                alert.setTitle(resourceBundle.getString("error"));
                alert.setHeaderText("");
                alert.setContentText(resourceBundle.getString("day_is_not_correct"));
                alert.showAndWait();
                return null;
            }
        } catch (Exception e) {
            alert.setTitle(resourceBundle.getString("error"));
            alert.setHeaderText("");
            alert.setContentText(resourceBundle.getString("format_is_not_correct"));
            alert.showAndWait();
        }
        return date;
    }
    
    public static Integer checkMonth(Integer mount){
        if(mount.equals(01) || mount.equals(02) || mount.equals(03) || mount.equals(04) || mount.equals(05) || mount.equals(06))
            return 31 ;
        else
            return 30;
    }
    
    

    protected Stage getStage(ActionEvent event) {
        return (Stage)((Button)event.getSource()).getScene().getWindow();
    }

    
     protected void changeScene(String viewName, Stage stage) {
         
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_BASE_URL + viewName), resourceBundle);
        Parent root = null;
        try {
            root = loader.load();
            BaseController controller = loader.<BaseController>getController();
            ApplicationContext.addController(controller);
            controller.setStage(stage);
        } catch (IOException e) {
            log.error(e.getMessage());
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


        String title = resourceBundle.getString("login_error");
        String header = resourceBundle.getString("operation_system_exception");
        String content = resourceBundle.getString("error_in_sys_operation");

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
