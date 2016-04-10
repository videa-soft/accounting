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
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class EditAccountController extends BaseController {
    private static final Logger LOG = Logger.getLogger(EditAccountController.class.getName());
    

    private User selectedUser;
    @FXML
    private TextField createDate;
    @FXML
    private TextField debit;
    @FXML
    private TextField  credit;
    @FXML
    private TextField accountBalance;
    @FXML
    private TextArea description;
    @FXML
    private TextField customerNumber;
    
      @Override
    public void init(Object data) {
        selectedUser = (User)data;
        List<AccountBalance> accList = null;
        try {
            if (selectedUser != null) {
                customerNumber.setText(selectedUser.getCustomerNumber().toString());
                
                accList = DatabaseUtil.getEntity(new AccountBalance(selectedUser.getUserId()));
                 Integer maxAcc = 0;
                for(AccountBalance acc : accList){
                    if(maxAcc < acc.getAccId())
                        maxAcc = acc.getAccId();
                }
                List<AccountBalance> maxAccRecord = DatabaseUtil.getEntity(new AccountBalance(selectedUser.getUserId(), maxAcc));
                if(maxAccRecord.size() > 0){
                    accountBalance.setText(maxAccRecord.get(0).getAccountBalance().toString());
                }
            }
        } catch (DatabaseOperationException e) {
////            messageTitle = "";
////            messageHeader = "Operation Exception!";
////            messageContent = "There is an error in system operation!";
            e.printStackTrace();
        }
    }
    
    @FXML
    private void saveAccount(){
        
        Date createDate = convertStringToDate(this.createDate.getText().toString());
        java.sql.Date sqlDate = null ;
        if(createDate == null){
            this.createDate.setText("");
            return;
        }
        else
            sqlDate = new java.sql.Date( createDate.getTime() );
        
        Integer debitInt = 0;
        Integer credit = 0;
        Integer accBalance = 0;
        String desc = this.description.getText();
        if(!this.accountBalance.getText().equals(""))
            accBalance = Integer.parseInt(this.accountBalance.getText().toString());
        if(!this.debit.getText().equals(""))
            debitInt = Integer.parseInt(this.debit.getText());
        if(!this.credit.getText().equals(""))
            credit = Integer.parseInt(this.credit.getText());  
        
        Alert alert ;
        String messageTitle = "";
        String messageHeader = "Operation successful";
        String messageContent = "";
        boolean validate = true;
        
           if (createDate == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(messageTitle);
            alert.setHeaderText(null);
            alert.setContentText(resourceBundle.getString("createDate_is_null"));
            alert.showAndWait();
            return;
        }
           if (debitInt == 0 && credit == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(messageTitle);
            alert.setHeaderText(null);
            alert.setContentText(resourceBundle.getString("debit_and_credit_must_not_be_null"));
            alert.showAndWait();
            return;
        }
           else{
               accBalance = accBalance + (debitInt - credit);
//               if(accBalance < 0){
//                   alert = new Alert(Alert.AlertType.ERROR);
//                   alert.setTitle("accountBalance");
//                   alert.setHeaderText("amount of credit is more than debit!");
//                   alert.setContentText("please pay less than amount of this credit and try again.");
//                   alert.showAndWait();
//               }
               accountBalance.setText(accBalance.toString());
           }
               
        
        if(validate) {
            AccountBalance accEntity = new AccountBalance();
            if(selectedUser != null) {
                accEntity.setUserId(selectedUser.getUserId());
            }
            accEntity.setCreateDate(sqlDate);
            accEntity.setCredit(credit);
            accEntity.setDebit(debitInt);
            accEntity.setDescription(desc);
            accEntity.setAccountBalance(accBalance);
           
            try {
                Integer primaryKey = DatabaseUtil.getValidPrimaryKey(accEntity);
                if (primaryKey != null && primaryKey != 0) {
                    accEntity.setAccId(primaryKey);
                    DatabaseUtil.create(accEntity);
                }
                if (stage != null) {
                    stage.close();
                }
                refreshView(AccBalanceManagementController.class);
                alert = new Alert(Alert.AlertType.INFORMATION);
            } catch (DatabaseOperationException e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setHeaderText(resourceBundle.getString("operation_system_exception"));
                alert.setContentText(resourceBundle.getString("error_in_sys_operation"));
            }
            catch (DeveloperFaultException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setHeaderText(resourceBundle.getString("operation_system_exception"));
                alert.setContentText(resourceBundle.getString("error_in_sys_operation"));
            }
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
        }
        alert.setTitle(messageTitle);
        alert.setHeaderText(resourceBundle.getString("Operation_successful"));
        alert.setContentText(messageContent);
        alert.showAndWait();
    }
    
}
