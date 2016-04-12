package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.Bill;
import ir.visoft.accounting.entity.User;
import ir.visoft.accounting.exception.DatabaseOperationException;
import ir.visoft.accounting.ui.ApplicationContext;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Amir
 */
public class UserManagementController extends  BaseController {


    private static Logger log = Logger.getLogger(UserManagementController.class.getName());

    @FXML
    private TableView<User> usersTable;

    private User selectedUser;
    

    @FXML
    public void initialize() {
        setUsersTableData();
    }

    private void setUsersTableData() {
        List<User> users = null;
        try {
            users = DatabaseUtil.getAll(new User());


            TableColumn userIdColumn = new TableColumn(resourceBundle.getString("userId"));
            userIdColumn.setCellValueFactory(new PropertyValueFactory("userId"));
            
            TableColumn custNumColumn = new TableColumn(resourceBundle.getString("customerNumber"));
            custNumColumn.setCellValueFactory(new PropertyValueFactory("customerNumber"));

            TableColumn firstNameColumn = new TableColumn(resourceBundle.getString("firstName"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));

            TableColumn lastNameColumn = new TableColumn(resourceBundle.getString("lastName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));

            TableColumn homeAddressColumn = new TableColumn(resourceBundle.getString("HomeAddress"));
            homeAddressColumn.setCellValueFactory(new PropertyValueFactory("homeAddress"));

            TableColumn phoneNumberColumn = new TableColumn(resourceBundle.getString("phoneNumber"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory("phoneNumber"));

            TableColumn nationalCodeColumn = new TableColumn(resourceBundle.getString("nationalCode"));
            nationalCodeColumn.setCellValueFactory(new PropertyValueFactory("nationalCode"));
            
            TableColumn familyCntColumn = new TableColumn(resourceBundle.getString("familyCount"));
            familyCntColumn.setCellValueFactory(new PropertyValueFactory("familyCount"));
            
            TableColumn workAddressColumn = new TableColumn(resourceBundle.getString("workAddress"));
            workAddressColumn.setCellValueFactory(new PropertyValueFactory("workAddress"));


            usersTable.getItems().removeAll();
            usersTable.getColumns().clear();
            usersTable.getColumns().addAll(userIdColumn, firstNameColumn, lastNameColumn, nationalCodeColumn, familyCntColumn, homeAddressColumn ,phoneNumberColumn);

            usersTable.setItems(FXCollections.observableList(users));
        } catch (DatabaseOperationException e) {
            showOperationError();
        }
    }

    @Override
    public void refresh() {
        setUsersTableData();
    }

    @FXML
    private void onEditUser(ActionEvent event) {
        selectedUser = usersTable.getSelectionModel().getSelectedItem();

        if(chkNullUser(selectedUser)) {
            showEditUserDialog();
        }
    }


    @FXML
    private void onRemoveUser(ActionEvent event) {
        selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (chkNullUser(selectedUser)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(resourceBundle.getString("Confirmation_Dialog"));
            alert.setHeaderText(resourceBundle.getString("delete_user"));
            alert.setContentText(resourceBundle.getString("are_you_sure"));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    DatabaseUtil.delete(selectedUser);
                    refresh();
                } catch (DatabaseOperationException e) {
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setTitle(resourceBundle.getString("error"));
                    alertError.setHeaderText("");
                    alertError.setContentText(resourceBundle.getString("cannot_delete_for_dependency"));
                    alertError.showAndWait();
                }
            }
        }
    }


    @FXML
    private void onAddUser(ActionEvent event) {
        selectedUser = null;
        showEditUserDialog();
    }

    private void showEditUserDialog() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_BASE_URL + "editUser.fxml"), resourceBundle);
        Pane page = null;
        EditUserController controller = null;
        try {
            page = loader.load();
            controller = loader.<EditUserController>getController();
            ApplicationContext.addController(controller);
            controller.init(selectedUser);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        // Create the dialog Stage.
        Stage dialogStage = new Stage();

        if(selectedUser == null)
            dialogStage.setTitle(resourceBundle.getString("AddUser"));
        else
            dialogStage.setTitle(resourceBundle.getString("EditUser"));
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = null;
        if (page != null) {
            scene = new Scene(page);
        }
        if (controller != null) {
            controller.setStage(dialogStage);
        }
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }
    
    @FXML
    private void onEditBill(ActionEvent event) {
        selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (chkNullUser(selectedUser)) {
            showEditBillDialog();
        }
    }

    private void showEditBillDialog() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_BASE_URL + "editBill.fxml"), resourceBundle);
        Pane page = null;
        EditBillController controller = null;
        try {
            page = loader.load();
            controller = loader.<EditBillController>getController();
            ApplicationContext.addController(controller);
            controller.init(selectedUser);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle(resourceBundle.getString("EditBill"));
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = null;
        if (page != null) {
            scene = new Scene(page);
        }
        if (controller != null) {
            controller.setStage(dialogStage);
        }
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    @FXML
    private void onEditAccunt(ActionEvent event) {
        selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (chkNullUser(selectedUser)) {
            showEditAccDialog();
        }
    }

    private void showEditAccDialog() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_BASE_URL + "editAccount.fxml"), resourceBundle);
        Pane page = null;
        EditAccountController controller = null;
        try {
            page = loader.load();
            controller = loader.<EditAccountController>getController();
            ApplicationContext.addController(controller);
            controller.init(selectedUser);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        //Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle(resourceBundle.getString("EditAccount"));
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = null;
        if (page != null) {
            scene = new Scene(page);
        }
        if (controller != null) {
            controller.setStage(dialogStage);
        }
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    @FXML
    private void onEditLastBill(ActionEvent event) {
        selectedUser = usersTable.getSelectionModel().getSelectedItem();

        if(chkNullUser(selectedUser)) {
            showEditLastBillDialog();
        }
    }
    
    private void showEditLastBillDialog(){
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_BASE_URL + "editBill.fxml"), resourceBundle);
        Pane page = null;
        EditBillController controller = null;
        try {
            page = loader.load();
            controller = loader.<EditBillController>getController();
            ApplicationContext.addController(controller);
            controller.initEditLastBill(selectedUser);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle(resourceBundle.getString("EditLastBill"));
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = null;
        if (page != null) {
            scene = new Scene(page);
        }
        if (controller != null) {
            controller.setStage(dialogStage);
        }
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
        
    }

    @FXML
    private void onDeleteLastBill(ActionEvent event) {
        selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (chkNullUser(selectedUser)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(resourceBundle.getString("Confirmation_Dialog"));
            alert.setHeaderText(resourceBundle.getString("deleteBill"));
            alert.setContentText(resourceBundle.getString("are_you_sure"));

            Optional<ButtonType> result = alert.showAndWait();
            List<Bill> billList = null;
            if (result.get() == ButtonType.OK) {
                try {
                    billList = DatabaseUtil.getEntity(new Bill(selectedUser.getUserId()));
                    Integer maxBill = 0;
                    for (Bill bills : billList) {
                        if (maxBill < bills.getBillId()) {
                            maxBill = bills.getBillId();
                        }
                    }
                    List<Bill> maxBillRecord = DatabaseUtil.getEntity(new Bill(selectedUser.getUserId(), maxBill));
                    DatabaseUtil.delete(maxBillRecord.get(0));
                    refreshView(BillManagementController.class);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText("");
                    alert.setContentText(resourceBundle.getString("Last_Bill_Delete_Successfully"));
                    alert.showAndWait();
                } catch (DatabaseOperationException e) {
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setTitle(resourceBundle.getString("error"));
                    alertError.setHeaderText("");
                    alertError.setContentText(resourceBundle.getString("cannot_delete_for_dependency"));
                    alertError.showAndWait();
                }
            }
        }
    }

    private boolean chkNullUser(Object selectedUser) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (selectedUser == null) {
            alert.setTitle(resourceBundle.getString("error"));
            alert.setHeaderText("");
            alert.setContentText(resourceBundle.getString("not_selected_any_user"));
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

}
