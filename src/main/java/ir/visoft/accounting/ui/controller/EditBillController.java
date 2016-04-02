/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ir.visoft.accounting.ui.controller;

import com.sun.javafx.scene.control.skin.IntegerFieldSkin;
import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.Bill;
import ir.visoft.accounting.entity.User;
import ir.visoft.accounting.exception.DatabaseOperationException;
import ir.visoft.accounting.exception.DeveloperFaultException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import org.apache.log4j.Logger;

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
    @FXML
    private DatePicker preDate;
    @FXML
    private DatePicker currentDate;
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
    private TextField billId;


    @Override
    public void init(Object data) {
        selectedUser = (User)data;
        List<Bill> billList = null;
        try {
            if (selectedUser != null) {
                billList = DatabaseUtil.getEntity(new Bill(selectedUser.getUserId()));
                //@TODO : init set for  PreDate, cost-balance
                customerNumber.setText(selectedUser.getCustomerNumber().toString());
                familyCnt.setText(selectedUser.getFamilyCount().toString());
                if (billList == null) {

                }
            }
        } catch (DatabaseOperationException e) {
//            messageTitle = "";
//            messageHeader = "Operation Exception!";
//            messageContent = "There is an error in system operation!";
            e.printStackTrace();
        }
    }
    
    @FXML
    private void computationBtn() {
        LocalDate preDate = this.preDate.getValue();
        LocalDate currentDate = this.currentDate.getValue();
        Integer preFigure = Integer.parseInt(this.preFigure.getText());
        Integer currentFigure =Integer.parseInt(this.currentFigure.getText());
        Integer familyCnt = Integer.parseInt(this.familyCnt.getText());
        Integer servicesInt;
        
        Alert alert;
        String messageTitle = "";
        String messageHeader = "Operation successful";
        String messageContent = "";
        
        if (currentDate == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(messageTitle);
            alert.setHeaderText(null);
            alert.setContentText("currentDate is null!");
            alert.showAndWait();
        }
        if (currentFigure == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(messageTitle);
            alert.setHeaderText(null);
            alert.setContentText("currentFigure is null!");
            alert.showAndWait();
        }

        if (this.services.getText() == null || this.services.getText().equals("")) {
            servicesInt = 0;
            services.setText("0");
        } else {
            servicesInt = Integer.parseInt(this.services.getText());
        }
                
        Integer cunsumptionInt = currentFigure - preFigure;
        Double b = null;
        if (currentDate.getYear() == preDate.getYear()) {
            b = (double) currentDate.getDayOfYear() - preDate.getDayOfYear();
        }
        else{
          //@TODO: set last day of years
//            Calendar cal = Calendar.getInstance();
//            cal.set(Calendar.YEAR, preDate.getYear());
//            cal.set(Calendar.MONTH, 12);
//            cal.set(Calendar.DAY_OF_MONTH, preDate.getMonth().maxLength()); // new years eve
//
//            Date end = cal.getTime();
//            System.out.println("year==============>"+preDate.getYear());
//            System.out.println("end==============>"+end);
            
        }
        
        System.out.println("b==============>"+b);
        System.out.println("max lenght==============>"+preDate.getMonth().maxLength());
        b = b/30;
        Double x = (double)cunsumptionInt / (double)(familyCnt * b); //masraf har khanevar dar 1 maah      
        Double x2 = x / pelekan ;
        Integer xInt = x2.intValue();
        /*------------------mohasebeye ghabli :
        Integer firstFig = Integer.parseInt(xInt.toString().substring(0, xInt.toString().length() - 1));
        Integer finalamount = (int)((familyCnt * b) * 1000 *(x - (5*firstFig))*(firstFig+1)); -----------------------*/
        Integer finalamount = (int) ((familyCnt * b) * meter * (x - (5 * xInt)) * (xInt + 1));
        
//@TODO:        finalamount = finalamount + servicesInt + cost_balance + bedehie gozashte - bestankarie gozashte
        
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
            Bill bill = new Bill();
            if(selectedUser != null) {
                bill.setUserId(selectedUser.getUserId());
            }
//            bill.setNewDate(currentDate);
            bill.setPreviousDate(preDate);
            bill.setPreviousFigure(preFigure);
            bill.setCurrentFigure(currentFigure);
            bill.setCunsumption(cunsumptionInt);
            bill.setAbonman(abonmanD);
            bill.setReduction(reductionInt);
            bill.setServices(servicesInt);
            bill.setCostWater(costWater);
            bill.setCostBalance(bill.getReduction());
            bill.setFinalAmount(finalamount);
           
            try {
                Integer primaryKey = DatabaseUtil.getValidPrimaryKey(bill);
                if (primaryKey != null && primaryKey != 0) {
                    bill.setBillId(primaryKey);
                    billId.setText(primaryKey.toString());
                    DatabaseUtil.create(bill);
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
            catch (DeveloperFaultException e) {
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
    
    
    public static void main (String[] args) throws DatabaseOperationException{
        
        Bill bill = new Bill();
        List results = DatabaseUtil.getLastUpdated(bill);
    }
    
}
