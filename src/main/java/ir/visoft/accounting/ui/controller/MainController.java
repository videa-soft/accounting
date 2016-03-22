package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.db.DatabaseUtil;
import ir.visoft.accounting.entity.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class MainController extends BaseController {

    private static Logger log = Logger.getLogger(MainController.class.getName());


    @FXML
    private TableView<User> usersTable;

    private User selectedUser;


    @FXML
    public void initialize() {
        List<User> users = DatabaseUtil.getAll(new User());
        usersTable.setItems(FXCollections.observableList(users));

        TableColumn userIdColumn = new TableColumn("UserId");
        userIdColumn.setCellValueFactory(new PropertyValueFactory("userId"));

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

        usersTable.getColumns().setAll(userIdColumn, firstNameColumn, lastNameColumn, homeAddressColumn, phoneNumberColumn, nationalCodeColumn);

    }


    @FXML
    private void onEdit(ActionEvent event) {
        selectedUser = usersTable.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/editUser.fxml"));
        Pane page = null;
        try {
            page = loader.load();
            loader.<EditUserController>getController().init(selectedUser);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit User");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }


    @FXML
    private void onRemove(ActionEvent event) {
        selectedUser = usersTable.getSelectionModel().getSelectedItem();
    }


    @FXML
    private void onAdd(ActionEvent event) {
        selectedUser = usersTable.getSelectionModel().getSelectedItem();
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
}
