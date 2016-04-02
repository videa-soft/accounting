/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ir.visoft.accounting.ui.controller;

import com.sun.javafx.scene.control.skin.IntegerFieldSkin;
import com.sun.org.apache.xerces.internal.parsers.IntegratedParserConfiguration;
import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.Bill;
import ir.visoft.accounting.entity.User;
import ir.visoft.accounting.exception.DatabaseOperationException;
import ir.visoft.accounting.exception.DeveloperFaultException;
import ir.visoft.accounting.ui.UTF8Control;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
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
    
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.message", new Locale("fa"), new UTF8Control());
    
    private final int pelekan = 10; 
    private final int meter = 1000; 
    private final int perAbonman = 40000; 
     
    @FXML
    private TableView<Bill> billTable;

    private User selectedUser;
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
        
        Date PreDate = convertStringToDate(this.preDate.getText().toString());
        java.sql.Date sqlPreDate = null;
        if(PreDate == null){
            this.preDate.setText("");
            return;
        }
        else
            sqlPreDate = new java.sql.Date( PreDate.getTime() );
        
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
        if(!this.preFigure.getText().equals(""))
            preFigure = Integer.parseInt(this.preFigure.getText());
        if(!this.currentFigure.getText().equals(""))
            currentFigure =Integer.parseInt(this.currentFigure.getText());
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
        if (currentFigure == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(messageTitle);
            alert.setHeaderText(null);
            alert.setContentText(resourceBundle.getString("currentFigure_is_null"));
            alert.showAndWait();
            return ;
        }

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
                    b = b + checkMount(currentmount - i).doubleValue();
                    diffMount--;
                }
                b = b + (checkMount(mount).doubleValue() - day) + currentday;
            }
        }
        else{
            b = checkMount(mount).doubleValue() - day ;
            for (int i = 1; (mount + i) < 13; i++) {
                b = b + checkMount(mount + i).doubleValue();
            }
            for(int i = 1 ; (currentmount - i) > 0 ; i++){
                b = b + checkMount(currentmount - i ).doubleValue();
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
            bill.setNewDate(sqlCurrentDate);
            bill.setPreviousDate(sqlPreDate);
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
                    DatabaseUtil.create(bill);
                }
                if (stage != null) {
                    stage.close();
                }
                refreshView(BillManagementController.class);
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
    
    public static void main (String[] args) throws DatabaseOperationException,DeveloperFaultException{
        Set<Field> set = new HashSet<Field>();
        Bill bill = new Bill();
        List<Field> results = DatabaseUtil.getLastUpdated(bill);
//        System.out.println("------------------->"+DatabaseUtil.getLastUpdated(bill));
//        results.get(1);
//        Set<Field> alphaSet = new HashSet<Field>(results);
//        for (Field fieldA : alphaSet) {
//            System.out.println(fieldA);
//        }
//        Field[] fields = results.get(0).getClass().getDeclaredFields();
         for(Field f : results){
             set.add(f);
         }
    }
    
}
