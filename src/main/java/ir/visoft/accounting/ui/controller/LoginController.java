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
            title = resourceBundle.getString("Username_or_Password_Error").toString();
            header = "";//Username/Password Empty!";
            content = resourceBundle.getString("plz_insert_user_and_pass").toString();
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
                    title = resourceBundle.getString("login_error").toString();
                    header = "";// "Username/Password Wrong!";
                    content = resourceBundle.getString("credentials_wrong").toString();
                }
            } catch (DatabaseOperationException e) {
                title = resourceBundle.getString("login_error").toString();
                header = resourceBundle.getString("operation_system_exception").toString();
                content = resourceBundle.getString("error_in_sys_operation").toString();
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
