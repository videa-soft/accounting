package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.BaseEntity;
import ir.visoft.accounting.entity.User;
import ir.visoft.accounting.exception.DatabaseOperationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author Amir
 */
public class LoginController extends BaseController {

    private static Logger log = Logger.getLogger(LoginController.class.getName());


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
            List<BaseEntity> userList = null;
            try {
                userList = DatabaseUtil.getEntity(user);
                if(userList != null && !userList.isEmpty() && userList.size() == 1) {
                    authenticationSuccess = true;
                } else {
                    title = "Login Error";
                    header = "Username/Password Wrong!";
                    content = "You entered wrong credentials.";
                }
            } catch (DatabaseOperationException e) {
                title = "Login Error";
                header = "Operation Exception";
                content = "There is an error in system operation.";
            }

        }

        if(!authenticationSuccess) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        } else {
            changeScene("../view/main.fxml", getStage(event));
        }
    }


}
