package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

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
        username.setText(selectedUser.getUsername());
        firstName.setText(selectedUser.getFirstName());
        lastName.setText(selectedUser.getLastName());
        nationalCode.setText(selectedUser.getNationalCode());
        homeAddress.setText(selectedUser.getHomeAddress());
    }

    @FXML
    private void save() {

    }
}
