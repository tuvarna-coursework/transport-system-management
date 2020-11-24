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

public class UserScheduleController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void goToMyTicket(javafx.event.ActionEvent event) throws IOException {
        Parent ticketPanel = FXMLLoader.load(getClass().getResource("/views/UserMyTicketPanel.fxml"));
        Scene ticketScene = new Scene(ticketPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ticketScene);
        window.show();
    }

    public void logOut(javafx.event.ActionEvent event) throws IOException {
        Parent ticketPanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
        Scene ticketScene = new Scene(ticketPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ticketScene);
        window.show();

    }

    public void goToBuyTicket(javafx.event.ActionEvent event) throws IOException {
        Parent ticketPanel = FXMLLoader.load(getClass().getResource("/views/UserPanel.fxml"));
        Scene ticketScene = new Scene(ticketPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ticketScene);
        window.show();
    }


}
