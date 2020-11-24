package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserMyTicketController implements Initializable {
    @FXML
    private TextField ticketIDTextField;
    @FXML
    private TableView<Ticket> myTicketTableView;
    @FXML
    private Label informationLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void logOut(javafx.event.ActionEvent event) throws IOException {
        Parent ticketPanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
        Scene ticketScene = new Scene(ticketPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ticketScene);
        window.show();

    }
    public void goToSchedule(javafx.event.ActionEvent event) throws IOException {
        Parent schedulePanel = FXMLLoader.load(getClass().getResource("/views/SchedulePanel.fxml"));
        Scene scheduleScene = new Scene(schedulePanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scheduleScene);
        window.show();
    }
    public void goToBuyTicket(javafx.event.ActionEvent event) throws IOException {
        Parent ticketPanel = FXMLLoader.load(getClass().getResource("/views/UserPanel.fxml"));
        Scene ticketScene = new Scene(ticketPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ticketScene);
        window.show();
    }

    public void cancelMyTrip(){

        informationLabel.setText("You cancel you trip.");
        //deleting user trip
    }

}
