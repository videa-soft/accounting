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
import javafx.event.EventType;

public class EditUserController extends BaseController {

    private static Logger log = Logger.getLogger(EditUserController.class.getName());


    private User selectedUser;
//    @FXML
//    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField nationalCode;
    @FXML
    private TextField homeAddress;
    @FXML
    private TextField familyCnt;
//    @FXML
//    private TextArea workAddress;
    @FXML
    private TextField phoneNumber;



    @Override
    public void init(Object data) {
        selectedUser = (User)data;
        if(selectedUser != null) {
//            username.setText(selectedUser.getUsername());
            firstName.setText(selectedUser.getFirstName());
            lastName.setText(selectedUser.getLastName());
            nationalCode.setText(selectedUser.getNationalCode());
            homeAddress.setText(selectedUser.getHomeAddress());
            familyCnt.setText(selectedUser.getFamilyCount().toString());
//            workAddress.setText(selectedUser.getWorkAddress());
            phoneNumber.setText(selectedUser.getPhoneNumber());
        }
    }

    @FXML
    private void save() {
        String messageTitle = "";
        String messageHeader = resourceBundle.getString("Operation_successful").toString();
        String messageContent = "";
        boolean validate = true;
        
//        String username = this.username.getText();
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String nationalCode = this.nationalCode.getText();
        String homeAddress = null ;
        String workAddress = null ;
        String phoneNumber = null ;
        Integer familyCnt = null;
        if(this.homeAddress.getText().equals("")){
            homeAddress = null ;
            this.homeAddress.setText("");
        }else
            homeAddress = this.homeAddress.getText();
        
//        if(this.workAddress.getText().equals("")){
//            workAddress = null;
//            this.workAddress.setText("");
//        }else
//            workAddress = this.workAddress.getText();
        
        if( this.phoneNumber.getText().equals("")){
            phoneNumber = null;
            this.phoneNumber.setText("");
        }else
            phoneNumber = this.phoneNumber.getText();
        
        
        if(firstName == null || firstName.equals("")) {
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
        }
        else if (!nationalCode.matches("[0-9]+")) {
            messageTitle = "";
            messageHeader = resourceBundle.getString("nationalCode_must_be_num").toString();
            messageContent = "";
            validate = false;
        }
        else if(this.nationalCode.getText().length() != 10 ){
            messageTitle = "";
            messageHeader = resourceBundle.getString("nationalCode_must_be_ten").toString();
            messageContent = "";
            validate = false;
        }
        else if(this.familyCnt.getText().equals("")){
            messageTitle = "";
            messageHeader = resourceBundle.getString("familyCount_is_null").toString();
            messageContent = "";
            validate = false;
        }
        else if (!this.familyCnt.getText().equals("")) {
            if (this.familyCnt.getText().matches("[0-9]+")) {
                familyCnt = Integer.parseInt(this.familyCnt.getText());
            } else {
                messageTitle = "";
                messageHeader = resourceBundle.getString("familyCnt_must_be_num").toString();
                messageContent = "";
                validate = false;
            }
        } else if (!phoneNumber.matches("[0-9]+")) {
            messageTitle = "";
            messageHeader = resourceBundle.getString("phoneNum_must_be_num").toString();
            messageContent = "";
            validate = false;
        } 
        
        if (validate) {
            //check to see if any user with this userId exists!
            List<User> userList = null;
            try {
                userList = DatabaseUtil.getEntity(new User(nationalCode));
            } catch (DatabaseOperationException e) {
                messageTitle = "";
                messageHeader = resourceBundle.getString("operation_system_exception").toString();
                messageContent = resourceBundle.getString("error_in_sys_operation").toString();
                validate = false;
            }
            if (userList != null && !userList.isEmpty() && userList.size() == 1) {
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
            user.setUsername("");
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setNationalCode(nationalCode);
            user.setHomeAddress(homeAddress);
            user.setFamilyCount(familyCnt);
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
                messageHeader = "Operation Exception!";
                messageContent = "There is an error in system operation!";
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
