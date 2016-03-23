package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.User;
import ir.visoft.accounting.exception.DatabaseOperationException;
import ir.visoft.accounting.exception.DeveloperFaultException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.util.List;

public class EditUserController extends BaseController {

    private static Logger log = Logger.getLogger(EditUserController.class.getName());

    private User selectedUser;
    @FXML
    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField nationalCode;
    @FXML
    private TextArea homeAddress;



    @FXML
    public void initialize() {

    }

    @Override
    public void init(Object data) {
        selectedUser = (User)data;
        if(selectedUser != null) {
            username.setText(selectedUser.getUsername());
            firstName.setText(selectedUser.getFirstName());
            lastName.setText(selectedUser.getLastName());
            nationalCode.setText(selectedUser.getNationalCode());
            homeAddress.setText(selectedUser.getHomeAddress());
        }
    }

    @FXML
    private void save() {
        String username = this.username.getText();
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String nationalCode = this.nationalCode.getText();
        String homeAddress = this.homeAddress.getText();

        String messageTitle = "";
        String messageHeader = "Operation successful";
        String messageContent = "";

        boolean validate = true;
        if(username == null || username.equals("")) {
            messageTitle = "";
            messageHeader = "Empty username is not allowed!";
            messageContent = "";
            validate = false;

        } else if(firstName == null || firstName.equals("")) {
            messageTitle = "";
            messageHeader = "Empty first name is not allowed!";
            messageContent = "";
            validate = false;
        } else if(lastName == null || lastName.equals("")) {
            messageTitle = "";
            messageHeader = "Empty last name is not allowed!";
            messageContent = "";
            validate = false;
        } else if(nationalCode == null || nationalCode.equals("")) {
            messageTitle = "";
            messageHeader = "Empty national code is not allowed!";
            messageContent = "";
            validate = false;
        } else {
            //check to see if any user with this userId exists!
            List<User> userList = null;
            try {
                userList = DatabaseUtil.getEntity(new User(username));
            } catch (DatabaseOperationException e) {
                messageTitle = "";
                messageHeader = "Operation Exception!";
                messageContent = "There is an error in system operation!";
                validate = false;
            }
            if(userList != null && !userList.isEmpty() && userList.size() == 1) {
                if (!userList.get(0).getUserId().equals(selectedUser.getUserId())) {
                    messageTitle = "";
                    messageHeader = "Username already exists!";
                    messageContent = "";
                    validate = false;
                }
            }

        }

        Alert alert;
        if(validate) {
            User user = new User();
            user.setUserId(selectedUser.getUserId());
            user.setUsername(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setNationalCode(nationalCode);
            user.setHomeAddress(homeAddress);
            user.setFamilyCount(2); // @TODO : @Ghazaleh, please get this value from the user input, validate it as well!
            try {
                if(selectedUser == null) {
                    Integer primaryKey = DatabaseUtil.getValidPrimaryKey(user);
                    if(primaryKey != null && primaryKey != 0) {
                        user.setUserId(primaryKey);
                        user.setCustomerNumber(primaryKey.toString());
                        DatabaseUtil.create(user);
                    }
                } else {
                    DatabaseUtil.update(user);
                }
                if(stage != null) {
                    stage.close();
                }
                refreshView(MainController.class);
                alert = new Alert(Alert.AlertType.INFORMATION);
            } catch (DatabaseOperationException e) {
                messageTitle = "";
                messageHeader = "Operation Exception!";
                messageContent = "There is an error in system operation!";
                alert = new Alert(Alert.AlertType.ERROR);
            } catch (DeveloperFaultException e) {
                messageTitle = "";
                messageHeader = "Operation Exception!";
                messageContent = "There is an error in system operation!";
                alert = new Alert(Alert.AlertType.ERROR);
            }
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
        }
        alert.setTitle(messageTitle);
        alert.setHeaderText(messageHeader);
        alert.setContentText(messageContent);
        alert.showAndWait();
    }
}
