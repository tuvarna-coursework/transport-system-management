package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.services.TicketService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UserMyTicketController implements Initializable {
	ObservableList list = FXCollections.observableArrayList();
	
	@FXML
	private TextField ticketIDTextField;
	@FXML
	private TableView<Trip> myTicketTableView;
	@FXML
	private Label informationLabel;

	@FXML
	private TableColumn<Ticket, Integer> ticketIdCol;

	@FXML
	private TableColumn<Trip, String> ticketDateCol;

	@FXML
	private TableColumn<Trip, String> ticketHourCol;

	@FXML
	private TableColumn<Trip, String> ticketDepartureCol;

	@FXML
	private TableColumn<Trip, String> ticketArrivalCol;

	@FXML
	private TableColumn<Trip, Integer> ticketDurationCol;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ticketDateCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
					
						
						return new SimpleStringProperty(param.getValue().getTripDepartureDate().toString());
					}
				});

		ticketHourCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripDepartureHour());
					}
				});

		ticketDepartureCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getRoute()
								.getRouteDepartureLocation().getLocationName().toString());
					}
				});
		
		ticketArrivalCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getRoute()
								.getRouteArrivalLocation().getLocationName().toString());
					}
				});
		
		ticketDurationCol.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("tripDuration"));
		ticketIdCol.setCellValueFactory(new PropertyValueFactory<Ticket, Integer>("ticketId"));
		
		list.removeAll(list);
		List<Trip> trips = new ArrayList<>();
		DatabaseUtils.currentUser.getTickets().forEach(t -> trips.add(t.getTrip()));
		list.addAll(trips);
		
		myTicketTableView.getItems().addAll(list);
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

	public void cancelMyTrip() {

		informationLabel.setText("You cancel you trip.");
		// deleting user trip
	}

}
