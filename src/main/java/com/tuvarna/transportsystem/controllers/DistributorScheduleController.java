package com.tuvarna.transportsystem.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DistributorScheduleController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void goToRequest(javafx.event.ActionEvent event) throws IOException {
        Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorRequestPanel.fxml"));
        Scene adminScene = new Scene(userPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.show();

    }
    public void goToAddCashier(javafx.event.ActionEvent event) throws IOException {
        Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorAddPanel.fxml"));
        Scene adminScene = new Scene(userPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.show();

    }
    public void goToLogIn(javafx.event.ActionEvent event) throws IOException {
        Parent userPanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
        Scene adminScene = new Scene(userPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.show();

    }
}
