package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.User;
import ir.visoft.accounting.exception.DatabaseOperationException;
import ir.visoft.accounting.exception.DeveloperFaultException;
import ir.visoft.accounting.ui.UTF8Control;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class EditUserController extends BaseController {

    private static Logger log = Logger.getLogger(EditUserController.class.getName());
    
    ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.message", new Locale("fa"), new UTF8Control());

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
    private TextField familyCnt;
    @FXML
    private TextArea workAddress;
    @FXML
    private TextField phoneNumber;



    @Override
    public void init(Object data) {
        selectedUser = (User)data;
        if(selectedUser != null) {
            username.setText(selectedUser.getUsername());
            firstName.setText(selectedUser.getFirstName());
            lastName.setText(selectedUser.getLastName());
            nationalCode.setText(selectedUser.getNationalCode());
            homeAddress.setText(selectedUser.getHomeAddress());
            familyCnt.setText(selectedUser.getFamilyCount().toString());
            workAddress.setText(selectedUser.getWorkAddress());
            phoneNumber.setText(selectedUser.getPhoneNumber());
        }
    }

    @FXML
    private void save() {
        String username = this.username.getText();
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String nationalCode = this.nationalCode.getText();
        String homeAddress = this.homeAddress.getText();
        String workAddress = this.workAddress.getText();
        String phoneNumber = this.phoneNumber.getText();
        Integer familyCnt = null;
        if(!this.familyCnt.getText().equals(""))
            familyCnt = Integer.parseInt(this.familyCnt.getText());

        String messageTitle = "";
        String messageHeader = resourceBundle.getString("Operation_successful").toString();
        String messageContent = "";

        boolean validate = true;
        if(username == null || username.equals("")) {
            messageTitle = "";
            messageHeader = resourceBundle.getString("usename_is_null").toString();
            messageContent = "";
            validate = false;

        } else if(firstName == null || firstName.equals("")) {
            messageTitle = "";
            messageHeader = resourceBundle.getString("firstname_is_null").toString();
            messageContent = "";
            validate = false;
        } else if(lastName == null || lastName.equals("")) {
            messageTitle = "";
            messageHeader = resourceBundle.getString("lastname_is_null").toString();
            messageContent = "";
            validate = false;
        } else if(nationalCode == null || nationalCode.equals("")) {
            messageTitle = "";
            messageHeader = resourceBundle.getString("nationalCode_is_null").toString();
            messageContent = "";
            validate = false;
        } else if(familyCnt == null || familyCnt.equals(null)) {
            messageTitle = "";
            messageHeader = resourceBundle.getString("familyCount_is_null").toString();
            messageContent = "";
            validate = false;
        }else {
            //check to see if any user with this userId exists!
            List<User> userList = null;
            try {
                userList = DatabaseUtil.getEntity(new User(username));
            } catch (DatabaseOperationException e) {
                messageTitle = "";
                messageHeader = resourceBundle.getString("operation_system_exception").toString();
                messageContent = resourceBundle.getString("error_in_sys_operation").toString();
                validate = false;
            }
            if(userList != null && !userList.isEmpty() && userList.size() == 1) {
                if (!userList.get(0).getUserId().equals(selectedUser.getUserId())) {
                    messageTitle = "";
                    messageHeader = resourceBundle.getString("Username_already_exists").toString();
                    messageContent = "";
                    validate = false;
                }
            }

        }

        Alert alert;
        if(validate) {
            User user = new User();
            if(selectedUser != null) {
                user.setUserId(selectedUser.getUserId());
            }
            user.setUsername(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setNationalCode(nationalCode);
            user.setHomeAddress(homeAddress);
            user.setFamilyCount(familyCnt); // @TODO :its done with ghazaleh
            user.setWorkAddress(workAddress);
            user.setPhoneNumber(phoneNumber);
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
                refreshView(UserManagementController.class);
                alert = new Alert(Alert.AlertType.INFORMATION);
            } catch (DatabaseOperationException e) {
                messageTitle = "";
                messageHeader = resourceBundle.getString("operation_system_exception").toString();
                messageContent = resourceBundle.getString("error_in_sys_operation").toString();
                alert = new Alert(Alert.AlertType.ERROR);
            } catch (DeveloperFaultException e) {
                messageTitle = "";
                messageHeader = resourceBundle.getString("operation_system_exception").toString();
                messageContent = resourceBundle.getString("error_in_sys_operation").toString();
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
