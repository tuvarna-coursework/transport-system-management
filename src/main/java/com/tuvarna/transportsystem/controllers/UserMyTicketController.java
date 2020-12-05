package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Request;
import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.services.RequestService;
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
	private TableView<Ticket> myTicketTableView;
	@FXML
	private Label informationLabel;

	@FXML
	private TableColumn<Ticket, Integer> ticketIdCol;

	@FXML
	private TableColumn<Ticket, String> ticketDateCol;

	@FXML
	private TableColumn<Ticket, String> ticketHourCol;

	@FXML
	private TableColumn<Ticket, String> ticketDepartureCol;

	@FXML
	private TableColumn<Ticket, String> ticketArrivalCol;

	@FXML
	private TableColumn<Ticket, String> ticketDurationCol;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ticketDateCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(param.getValue().getTrip().getTripDepartureDate().toString());
					}
				});

		ticketDurationCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(String.valueOf(param.getValue().getTrip().getTripDuration()));
					}
				});

		ticketDepartureCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(param.getValue().getDepartureLocation().getLocationName());
					}
				});

		ticketArrivalCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(param.getValue().getArrivalLocation().getLocationName());
					}
				});

		ticketHourCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(param.getValue().getTrip().getTripDepartureHour());
					}
				});


		ticketIdCol.setCellValueFactory(new PropertyValueFactory<Ticket, Integer>("ticketId"));
		/*
		 * list.removeAll(list); List<Trip> trips = new ArrayList<>();
		 * DatabaseUtils.currentUser.getTickets().forEach(t -> trips.add(t.getTrip()));
		 * list.addAll(trips);
		 */

		myTicketTableView.setItems(getTickets());

	}

	public ObservableList<Ticket> getTickets() {
		ObservableList<Ticket> ticketsList = FXCollections.observableArrayList();
		TicketService ticketService = new TicketService();

		List<Ticket> eList = ticketService.getAll();
		for (Ticket ent : eList) {
			// if statement not working to filter only user's personal tickets.
			if (DatabaseUtils.currentUser.getTickets().contains(ent)) {
				ticketsList.add(ent);
			}
		}
		return ticketsList;
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
