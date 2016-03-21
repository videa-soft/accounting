package ir.visoft.accounting.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {


    @FXML
    private TextField username;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        //((TextField)((Button)event.getSource()).getScene().getRoot().getChildren().get(3)).getText();

        System.out.println("");
    }

    public TextField getUsername() {
        return username;
    }

    public void setUsername(TextField username) {
        this.username = username;
    }
}
