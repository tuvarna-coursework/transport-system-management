package com.tuvarna.transportsystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CashierNotificationsController implements Initializable {
    @FXML
    private Label informationLabel;
    //there is no class notifications that's why I comment this section.
   /* @FXML
    private TableView<Notification> notificationTableView;
   @FXML
   private TableColumn<Notification, String> notificationId_col;
   @FXML
   private TableColumn<Notification, String> company_col;
   @FXML
   private TableColumn<Notification, String> message_col;*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void deleteNotification(){
        informationLabel.setText("Select notification!");
    }
}
