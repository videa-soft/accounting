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

            TableColumn userIdColumn = new TableColumn("UserId");
            userIdColumn.setCellValueFactory(new PropertyValueFactory("userId"));
            
            TableColumn custNumColumn = new TableColumn("customerNumber");
            custNumColumn.setCellValueFactory(new PropertyValueFactory("customerNumber"));

            TableColumn firstNameColumn = new TableColumn("FirstName");
            firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));

            TableColumn lastNameColumn = new TableColumn("LastName");
            lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));

            TableColumn homeAddressColumn = new TableColumn("HomeAddress");
            homeAddressColumn.setCellValueFactory(new PropertyValueFactory("homeAddress"));

            TableColumn phoneNumberColumn = new TableColumn("PhoneNumber");
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory("phoneNumber"));

            TableColumn nationalCodeColumn = new TableColumn("NationalCode");
            nationalCodeColumn.setCellValueFactory(new PropertyValueFactory("nationalCode"));
            
            TableColumn familyCntColumn = new TableColumn("familyCount");
            familyCntColumn.setCellValueFactory(new PropertyValueFactory("familyCount"));

            usersTable.getItems().removeAll();
            usersTable.getColumns().clear();
            usersTable.getColumns().addAll(userIdColumn, custNumColumn, firstNameColumn, lastNameColumn, homeAddressColumn, phoneNumberColumn, nationalCodeColumn);
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
        if(chkNullUser(selectedUser))
            showEditUserDialog();
    }


    @FXML
    private void onRemoveUser(ActionEvent event) {
        selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (chkNullUser(selectedUser)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete User!");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    DatabaseUtil.delete(selectedUser);
                    refresh();
                } catch (DatabaseOperationException e) {
                    showOperationError();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/editUser.fxml"));
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
        dialogStage.setTitle("Edit User");
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/editBill.fxml"));
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
        dialogStage.setTitle("Edit Bill");
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/editAccount.fxml"));
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

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Account");
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
     
     private boolean chkNullUser(Object selectedUser){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(selectedUser == null){
              alert.setTitle("Error Dialog");
              alert.setHeaderText("");
              alert.setContentText("not selected any user!");
              alert.showAndWait();
              return false;
         }
        else
            return true;
     }
 
}
