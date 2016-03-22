package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.BaseEntity;
import ir.visoft.accounting.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class loginController {

    private static Logger log = Logger.getLogger(loginController.class.getName());


    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private void login(ActionEvent event) {

        String title = null;
        String header = null;
        String content = null;
        boolean authenticationSuccess = false;

        if((username == null || username.getText().equals("")) || (password == null || password.getText().equals(""))) {
            title = "Username/Password Error";
            header = "Username/Password Empty!";
            content = "Please Enter both username and password.";
        } else {
            User user = new User();
            user.setUsername(username.getText());
            user.setPassword(password.getText());
            try {
                List<BaseEntity> userList = DatabaseUtil.getEntity(user);
                if(userList != null && !userList.isEmpty() && userList.size() == 1) {
                    authenticationSuccess = true;
                } else {
                    title = "Login Error";
                    header = "Username/Password Wrong!";
                    content = "You entered wrong credentials.";
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
            } catch (InvocationTargetException e) {
                log.error(e.getMessage());
            } catch (NoSuchMethodException e) {
                log.error(e.getMessage());
            } catch (InstantiationException e) {
                log.error(e.getMessage());
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            } catch (NoSuchFieldException e) {
                log.error(e.getMessage());
            }
        }

        if(!authenticationSuccess) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Authentication");
            alert.setHeaderText("Login success");
            alert.setContentText("You successfully logged in.");
            alert.showAndWait();
        }
    }

    public TextField getUsername() {
        return username;
    }

    public void setUsername(TextField username) {
        this.username = username;
    }
}
