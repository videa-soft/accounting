/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ir.visoft.accounting.ui.controller;

import com.itextpdf.text.DocumentException;
import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.AccountBalance;
import ir.visoft.accounting.entity.Bill;
import ir.visoft.accounting.entity.User;
import ir.visoft.accounting.exception.DatabaseOperationException;
import ir.visoft.accounting.exception.DeveloperFaultException;
import ir.visoft.accounting.util.FileUtil;
import ir.visoft.accounting.util.PdfUtil;
import ir.visoft.accounting.util.PropUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class EditBillController extends BaseController {

    private static Logger log = Logger.getLogger(EditUserController.class.getName());

    private final int pelekan = 10; 
    private final int meter = 1000; 
    private final int perAbonman = 40000; 
     
    @FXML
    private TableView<Bill> billTable;

    private User selectedUser;
    
    private Bill bill;
    
    @FXML
    private TextField preDate;
    @FXML
    private TextField currentDate;
    @FXML
    private TextField  preFigure;
    @FXML
    private TextField currentFigure;
    @FXML
    private TextField cunsumption;
    @FXML
    private TextField abonman;
    @FXML
    private TextField reduction;
    @FXML
    private TextField services;
    @FXML
    private TextField cost_water;
    @FXML
    private TextField cost_balance;
    @FXML
    private TextField final_amount;
    @FXML
    private TextField customerNumber;
    @FXML
    private TextField familyCnt;
    @FXML
    private TextField preBedehi ;
    @FXML
    private Button computationId;
    @FXML
    private Button showBillId;
    
    @Override
    public void init(Object data) {
        selectedUser = (User)data;
        List<Bill> billList = null;
        List<AccountBalance> accList = null;
        try {
            if (selectedUser != null) {
                billList = DatabaseUtil.getEntity(new Bill(selectedUser.getUserId()));
                Integer maxBill = 0;
                for(Bill bills : billList){
                    if(maxBill < bills.getBillId())
                        maxBill = bills.getBillId();
                }
                List<Bill> maxBillRecord = DatabaseUtil.getEntity(new Bill(selectedUser.getUserId(), maxBill));
                if(maxBillRecord.size() > 0){
                    preDate.setText(maxBillRecord.get(0).getNewDate().toString());
                    preFigure.setText(maxBillRecord.get(0).getCurrentFigure().toString());
                    cost_balance.setText(maxBillRecord.get(0).getReduction().toString());
                }

                customerNumber.setText(selectedUser.getCustomerNumber().toString());
                familyCnt.setText(selectedUser.getFamilyCount().toString());

                if(preDate.getText().equals(""))
                    preDate.setDisable(false);
                else
                    preDate.setDisable(true);
                
                if(preFigure.getText().equals(""))
                    preFigure.setDisable(false);
                else
                    preFigure.setDisable(true);
                
                accList = DatabaseUtil.getEntity(new AccountBalance(selectedUser.getUserId()));
                Integer maxAcc = 0 ;
                for(AccountBalance account : accList){
                    if(maxAcc < account.getAccId())
                        maxAcc = account.getAccId();
                }
                List<AccountBalance> maxAccRecord = DatabaseUtil.getEntity(new AccountBalance(selectedUser.getUserId(),maxAcc));
                if(maxAccRecord.size() > 0){
                    preBedehi.setText(maxAccRecord.get(0).getAccountBalance().toString());
                }
            }
        } catch (DatabaseOperationException e) {
            showOperationError();
        }
    }
   
    @FXML
    private void computationBtn() {
        
        Date PreDate = convertStringToDate(this.preDate.getText().toString());
        java.sql.Date sqlPreDate = null;
        if(PreDate == null){
            this.preDate.setText("");
            preDate.setDisable(false);
            return;
        }
        else{
            sqlPreDate = new java.sql.Date( PreDate.getTime() );
        }
        
        Date currentDate = convertStringToDate(this.currentDate.getText().toString());  
        java.sql.Date sqlCurrentDate = null;
        if(currentDate == null){
            this.currentDate.setText("");
            return;
        }
        else
            sqlCurrentDate = new java.sql.Date( currentDate.getTime() );
        
        Integer preFigure = null;
        Integer currentFigure = null;
        Integer familyCnt = null;
        Integer servicesInt;
        Integer preBedehi = 0;
        Integer costBalance = 0;
        if(!this.preBedehi.getText().equals(""))
            preBedehi = Integer.parseInt(this.preBedehi.getText());       
        if(!this.cost_balance.getText().equals(""))
            costBalance = Integer.parseInt(this.cost_balance.getText());    
        if(!this.preFigure.getText().equals(""))
            preFigure = Integer.parseInt(this.preFigure.getText());
//        if(!this.currentFigure.getText().equals(""))
//            currentFigure =Integer.parseInt(this.currentFigure.getText());
        if(!this.familyCnt.getText().equals(""))
             familyCnt = Integer.parseInt(this.familyCnt.getText());
        
        Alert alert;
        String messageTitle = "";
        String messageHeader = "";
        String messageContent = "";
        
        if (currentDate == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(messageTitle);
            alert.setHeaderText(null);
            alert.setContentText(resourceBundle.getString("currentDate_is_null!"));
            alert.showAndWait();
            return;
        }
        if (currentDate.getTime() <= PreDate.getTime()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(messageTitle);
            alert.setHeaderText(null);
            alert.setContentText(resourceBundle.getString("currentDate_is_equal_less_than_preDate!"));
            alert.showAndWait();
            return;
        }
        if(preFigure == null) {
            this.preFigure.setDisable(false);
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(messageTitle);
            alert.setHeaderText(null);
            alert.setContentText(resourceBundle.getString("preFigure_is_null"));
            alert.showAndWait();
            return;
        }
        if (this.currentFigure.getText().equals("")) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(messageTitle);
            alert.setHeaderText(null);
            alert.setContentText(resourceBundle.getString("currentFigure_is_null"));
            alert.showAndWait();
            return;
        }
        else if (!this.currentFigure.getText().matches("[0-9]+")) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(messageTitle);
            alert.setHeaderText(null);
            alert.setContentText(resourceBundle.getString("currentFigure_must_be_num"));
            alert.showAndWait();
            return;
        }else
            currentFigure =Integer.parseInt(this.currentFigure.getText());
        
        if (this.services.getText() == null || this.services.getText().equals("")) {
            servicesInt = 0;
            services.setText("0");
        } else {
            servicesInt = Integer.parseInt(this.services.getText());
        }
                
        Integer cunsumptionInt = currentFigure - preFigure;
        
        String txtPreDate = this.preDate.getText();
        Integer year = Integer.parseInt(txtPreDate.subSequence(0, 4).toString());
        Integer mount = Integer.parseInt(txtPreDate.substring(5, 7).toString());
        Integer day = Integer.parseInt(txtPreDate.substring(8, txtPreDate.length()).toString());
        
        String txtCurrentDate = this.currentDate.getText();
        Integer currentyear = Integer.parseInt(txtCurrentDate.subSequence(0, 4).toString());
        Integer currentmount = Integer.parseInt(txtCurrentDate.substring(5, 7).toString());
        Integer currentday = Integer.parseInt(txtCurrentDate.substring(8, txtCurrentDate.length()).toString());
        
        Double b = 0.0;
        if(year.equals(currentyear)){
            if(mount.equals(currentmount)){
                 b = currentday.doubleValue() - day.doubleValue() ;
            }
            else{
                Integer diffMount = currentmount - mount;
                for (int i = 1; diffMount != 1; i++) {
                    b = b + checkMonth(currentmount - i).doubleValue();
                    diffMount--;
                }
                b = b + (checkMonth(mount).doubleValue() - day) + currentday;
            }
        }
        else{
            b = checkMonth(mount).doubleValue() - day ;
            for (int i = 1; (mount + i) < 13; i++) {
                b = b + checkMonth(mount + i).doubleValue();
            }
            for(int i = 1 ; (currentmount - i) > 0 ; i++){
                b = b + checkMonth(currentmount - i ).doubleValue();
            }
            b = b + currentday;
        }
        
        b = b/30;
        Double x = (double)cunsumptionInt / (double)(familyCnt * b); //masraf har khanevar dar 1 maah      
        Double x2 = x / pelekan ;
        Integer xInt = x2.intValue();
        /*------------------mohasebeye ghabli :
        Integer firstFig = Integer.parseInt(xInt.toString().substring(0, xInt.toString().length() - 1));
        Integer finalamount = (int)((familyCnt * b) * 1000 *(x - (5*firstFig))*(firstFig+1)); -----------------------*/
        Integer finalamount = (int) ((familyCnt * b) * meter * (x - (5 * xInt)) * (xInt + 1));   
        finalamount = finalamount + servicesInt + costBalance + preBedehi ;    
        Integer reductionInt = finalamount % 1000 ;
        finalamount = finalamount - reductionInt;
        
        Double abonmanD = Double.parseDouble(new DecimalFormat("######.##").format(b * perAbonman * familyCnt));
        Double costWater = Double.parseDouble(new DecimalFormat("##.##").format(x));
        
        abonman.setText(abonmanD.toString());
        cost_water.setText(costWater.toString());
        cunsumption.setText(cunsumptionInt.toString());
        reduction.setText(reductionInt.toString());
        final_amount.setText(finalamount.toString());
        
        boolean validate = true;
        
        if(validate) {
            bill = new Bill();
            if(selectedUser != null) {
                bill.setUserId(selectedUser.getUserId());
            }
            bill.setNewDate(sqlCurrentDate);
            bill.setPreviousDate(sqlPreDate);
            bill.setPreviousFigure(preFigure);
            bill.setCurrentFigure(currentFigure);
            bill.setCunsumption(cunsumptionInt);
            bill.setAbonman(abonmanD);
            bill.setReduction(reductionInt);
            bill.setServices(servicesInt);
            bill.setCostWater(costWater);
            bill.setCostBalance(costBalance);
            bill.setFinalAmount(finalamount);
           
            try {
                Integer primaryKey = DatabaseUtil.getValidPrimaryKey(bill);
                if (primaryKey != null && primaryKey != 0) {
                    bill.setBillId(primaryKey);
                    DatabaseUtil.create(bill);
                }
//                if (stage != null) {
//                    stage.close();
//                }
                refreshView(BillManagementController.class);
//                refreshView(UserManagementController.class);
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
        computationId.setDisable(true);
        showBillId.setDisable(false);
        alert.setTitle(messageTitle);
        alert.setHeaderText(resourceBundle.getString("Operation_successful"));
        alert.setContentText(messageContent);
        alert.showAndWait();
    }
    
      @FXML
    private void showBill() {
        Alert alert;
        if(bill == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(resourceBundle.getString("selected_bill_is_null"));
            alert.showAndWait();
        } else {
            try {
                String fileName = PropUtil.getString("bill.report.base.path") +bill.getUserId()+ ".pdf";
                PdfUtil.createBillPdf(bill, fileName);
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    FileUtil.openFile(fileName);
                                } catch (IOException e) {
                                    log.error(e.getMessage());
                                }
                            }
                        },
                        1000
                );

            } catch (DocumentException | IOException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(resourceBundle.getString("error_in_sys_operation"));
                alert.showAndWait();
                log.error(e.getMessage());
            }
        }
    }
 
}
