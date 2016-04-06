package ir.visoft.accounting.ui.controller;

import com.itextpdf.text.DocumentException;
import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.Bill;
import ir.visoft.accounting.exception.DatabaseOperationException;
import ir.visoft.accounting.util.PdfUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;




/**
 * @author ghazaleh
 */
public class BillManagementController extends BaseController {
    
    
    private static Logger log = Logger.getLogger(BillManagementController.class.getName());

     @FXML
    private TableView<Bill> billTable;

    private Bill selectedBill;

    @FXML
    public void initialize() {
        setBillTableData();
    }
    
     private void setBillTableData() {
        List<Bill> Bill = null;
        try {
                            
            Bill = DatabaseUtil.getAll(new Bill());
            
            TableColumn userIdColumn = new TableColumn(resourceBundle.getString("userId"));
            userIdColumn.setCellValueFactory(new PropertyValueFactory("userId"));

            TableColumn billIdColumn = new TableColumn(resourceBundle.getString("billId"));
            billIdColumn.setCellValueFactory(new PropertyValueFactory("billId"));
  
            TableColumn preDateColumn = new TableColumn(resourceBundle.getString("preDate"));
            preDateColumn.setCellValueFactory(new PropertyValueFactory("previousDate"));

            TableColumn currentDateColumn = new TableColumn(resourceBundle.getString("currentDate"));
            currentDateColumn.setCellValueFactory(new PropertyValueFactory("newDate"));

            TableColumn preFigureColumn = new TableColumn(resourceBundle.getString("preFigure"));
            preFigureColumn.setCellValueFactory(new PropertyValueFactory("previousFigure"));

            TableColumn currentFigureColumn = new TableColumn(resourceBundle.getString("currentFigure"));
            currentFigureColumn.setCellValueFactory(new PropertyValueFactory("currentFigure"));

            TableColumn cunsumptionColumn = new TableColumn(resourceBundle.getString("cunsumption"));
            cunsumptionColumn.setCellValueFactory(new PropertyValueFactory("cunsumption"));

            TableColumn abonmanColumn = new TableColumn(resourceBundle.getString("abonman"));
            abonmanColumn.setCellValueFactory(new PropertyValueFactory("abonman"));
            
            TableColumn reductionColumn = new TableColumn(resourceBundle.getString("reduction"));
            reductionColumn.setCellValueFactory(new PropertyValueFactory("reduction"));
            
            TableColumn servicesColumn = new TableColumn(resourceBundle.getString("services"));
            servicesColumn.setCellValueFactory(new PropertyValueFactory("services"));
            
            TableColumn costWaterColumn = new TableColumn(resourceBundle.getString("cost_water"));
            costWaterColumn.setCellValueFactory(new PropertyValueFactory("costWater"));
            
            TableColumn costBalanceColumn = new TableColumn(resourceBundle.getString("cost_balance"));
            costBalanceColumn.setCellValueFactory(new PropertyValueFactory("costBalance"));

            TableColumn finalAmountColumn = new TableColumn(resourceBundle.getString("final_amount"));
            finalAmountColumn.setCellValueFactory(new PropertyValueFactory("finalAmount"));

            billTable.getItems().removeAll();
            billTable.getColumns().clear();
            billTable.getColumns().addAll(userIdColumn, billIdColumn, preDateColumn, currentDateColumn, preFigureColumn, currentFigureColumn, cunsumptionColumn, abonmanColumn,reductionColumn,servicesColumn, costWaterColumn,costBalanceColumn, finalAmountColumn);
            billTable.setItems(FXCollections.observableList(Bill));
        } catch (DatabaseOperationException e) {
            showOperationError();
        }
    }

    @FXML
    private void printBill() {
        selectedBill = billTable.getSelectionModel().getSelectedItem();
        Alert alert;
        if(selectedBill == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(resourceBundle.getString("selected_bill_is_null"));
            alert.showAndWait();
        } else {
            try {
                PdfUtil.createBillPdf(selectedBill);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("");
                alert.setContentText(resourceBundle.getString("requested_operation_has_been_done_successfully"));
                alert.showAndWait();
            } catch (DocumentException | IOException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(resourceBundle.getString("error_in_sys_operation"));
                alert.showAndWait();
                log.error(e.getMessage());
            }
        }
    }

}
