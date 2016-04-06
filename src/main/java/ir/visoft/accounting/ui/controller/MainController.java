package ir.visoft.accounting.ui.controller;

import ir.visoft.accounting.ui.ApplicationContext;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

public class MainController extends BaseController {

    private static Logger log = Logger.getLogger(MainController.class.getName());


    @FXML
    private AnchorPane userManagementTab;
    @FXML
    private AnchorPane billManagementTab;
    @FXML
    private AnchorPane accBalanceManagementTab;

    @FXML
    private UserManagementController userManagementTabController;
    @FXML
    private BillManagementController billManagementTabController;
    @FXML
    private AccBalanceManagementController accBalanceManagementTabController;

    @FXML
    private void initialize() {
        ApplicationContext.addController(userManagementTabController);
        ApplicationContext.addController(billManagementTabController);
        ApplicationContext.addController(accBalanceManagementTabController);
    }

}
