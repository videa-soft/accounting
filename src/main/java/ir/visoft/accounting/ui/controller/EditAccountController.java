/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.AccountBalance;
import ir.visoft.accounting.entity.Bill;
import ir.visoft.accounting.entity.User;
import ir.visoft.accounting.exception.DatabaseOperationException;
import ir.visoft.accounting.exception.DeveloperFaultException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class EditAccountController extends BaseController {
    private static final Logger LOG = Logger.getLogger(EditAccountController.class.getName());

    private User selectedUser;
    @FXML
    private DatePicker createDate;
    @FXML
    private TextField debit;
    @FXML
    private TextField  credit;
    @FXML
    private TextField accountBalance;
    @FXML
    private TextField description;
    
      @Override
    public void init(Object data) {
        selectedUser = (User)data;
//        List<AccountBalance> accList = null;
//        try {
            if (selectedUser != null) {
//                accList = DatabaseUtil.getEntity(new AccountBalance(selectedUser.getUserId()));
//                customerNumber.setText(selectedUser.getCustomerNumber().toString());
            }
//        } catch (DatabaseOperationException e) {
////            messageTitle = "";
////            messageHeader = "Operation Exception!";
////            messageContent = "There is an error in system operation!";
//            e.printStackTrace();
        }
    
    @FXML
    private void saveAccount(){
        
        LocalDate createDate = this.createDate.getValue();
        Integer debitInt = Integer.parseInt(this.debit.getText());
        Integer credit = Integer.parseInt(this.credit.getText());
        String desc = this.description.getText();
        
        Integer accBalance = credit - debitInt ; // @TODO: az accBalance ghabli ham kam shavad
        
        accountBalance.setText(accBalance.toString());
        
        String messageTitle = "";
        String messageHeader = "Operation successful";
        String messageContent = "";
        boolean validate = true;
        
        Alert alert;
        if(validate) {
            AccountBalance accEntity = new AccountBalance();
            if(selectedUser != null) {
                accEntity.setUserId(selectedUser.getUserId());
            }
//            accEntity.setCreateDate(createDate);
            accEntity.setCredit(credit);
            accEntity.setDebit(credit);
            accEntity.setDescription(desc);
            accEntity.setAccountBalance(accBalance);
           
            try {
                Integer primaryKey = 1;//DatabaseUtil.getValidPrimaryKey(accEntity);
                if (primaryKey != null && primaryKey != 0) {
                    accEntity.setAccId(primaryKey);
                    DatabaseUtil.create(accEntity);
                }
                if (stage != null) {
                    stage.close();
                }
                refreshView(UserManagementController.class);
                alert = new Alert(Alert.AlertType.INFORMATION);
            } catch (DatabaseOperationException e) {
                e.printStackTrace();
                messageTitle = "";
                messageHeader = "Operation Exception!";
                messageContent = "There is an error in system operation!";
                alert = new Alert(Alert.AlertType.ERROR);
            }
//            catch (DeveloperFaultException e) {
//                messageTitle = "";
//                messageHeader = "Operation Exception!";
//                messageContent = "There is an error in system operation!";
//                alert = new Alert(Alert.AlertType.ERROR);
//            }
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
        }
        alert.setTitle(messageTitle);
        alert.setHeaderText(messageHeader);
        alert.setContentText(messageContent);
        alert.showAndWait();
    }
    
}
