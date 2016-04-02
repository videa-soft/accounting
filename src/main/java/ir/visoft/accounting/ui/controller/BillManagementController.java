package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.Bill;
import ir.visoft.accounting.entity.User;
import ir.visoft.accounting.exception.DatabaseOperationException;
import ir.visoft.accounting.ui.UTF8Control;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author ghazaleh
 */
public class BillManagementController extends BaseController {
    
    
    private static final Logger LOG = Logger.getLogger(BillManagementController.class.getName());
    ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.message", new Locale("fa"), new UTF8Control());
    
     @FXML
    private TableView<Bill> billTable;

    private Bill selectedbill;

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

            TableColumn finalAmountColumn = new TableColumn(resourceBundle.getString("final_amount"));
            finalAmountColumn.setCellValueFactory(new PropertyValueFactory("finalAmount"));

            billTable.getItems().removeAll();
            billTable.getColumns().clear();
            billTable.getColumns().addAll(userIdColumn, billIdColumn, preDateColumn, currentDateColumn, preFigureColumn, currentFigureColumn, cunsumptionColumn, abonmanColumn,reductionColumn,servicesColumn, costWaterColumn, finalAmountColumn);
            billTable.setItems(FXCollections.observableList(Bill));
        } catch (DatabaseOperationException e) {
            showOperationError();
        }
    }
}
