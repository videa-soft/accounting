package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.Bill;
import ir.visoft.accounting.entity.User;
import ir.visoft.accounting.exception.DatabaseOperationException;
import java.util.List;
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

            TableColumn billIdColumn = new TableColumn("billId");
            billIdColumn.setCellValueFactory(new PropertyValueFactory("billId"));
            
            TableColumn preDateColumn = new TableColumn("previousDate");
            preDateColumn.setCellValueFactory(new PropertyValueFactory("previousDate"));

            TableColumn currentDateColumn = new TableColumn("newDate");
            currentDateColumn.setCellValueFactory(new PropertyValueFactory("newDate"));

            TableColumn preFigureColumn = new TableColumn("previousFigure");
            preFigureColumn.setCellValueFactory(new PropertyValueFactory("previousFigure"));

            TableColumn currentFigureColumn = new TableColumn("currentFigure");
            currentFigureColumn.setCellValueFactory(new PropertyValueFactory("currentFigure"));

            TableColumn cunsumptionColumn = new TableColumn("cunsumption");
            cunsumptionColumn.setCellValueFactory(new PropertyValueFactory("cunsumption"));

            TableColumn abonmanColumn = new TableColumn("abonman");
            abonmanColumn.setCellValueFactory(new PropertyValueFactory("abonman"));

            TableColumn finalAmountColumn = new TableColumn("finalAmount");
            finalAmountColumn.setCellValueFactory(new PropertyValueFactory("finalAmount"));

            billTable.getItems().removeAll();
            billTable.getColumns().clear();
            billTable.getColumns().addAll(billIdColumn, preDateColumn, currentDateColumn, preFigureColumn, currentFigureColumn, cunsumptionColumn, abonmanColumn, finalAmountColumn);
            billTable.setItems(FXCollections.observableList(Bill));
        } catch (DatabaseOperationException e) {
            showOperationError();
        }
    }
}
