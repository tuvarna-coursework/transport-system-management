package com.tuvarna.transportsystem.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CashierSoldController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void goToLogIn(javafx.event.ActionEvent event) throws IOException {
        Parent schedulePanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
        Scene scheduleScene = new Scene(schedulePanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scheduleScene);
        window.show();

    }
    public void goToSchedule(javafx.event.ActionEvent event) throws IOException {
        Parent schedulePanel = FXMLLoader.load(getClass().getResource("/views/CashierSchedulePanel.fxml"));
        Scene scheduleScene = new Scene(schedulePanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scheduleScene);
        window.show();

    }
    public void goToNotifications(javafx.event.ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/CashierNotificationsPanel.fxml"));
        AnchorPane root = (AnchorPane) userPanel.load();
        Scene adminScene = new Scene(root);
        stage.setScene(adminScene);
        stage.setTitle("Transport Company");
        stage.showAndWait();
    }

}
