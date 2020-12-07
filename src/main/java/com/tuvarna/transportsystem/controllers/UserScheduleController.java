package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.services.TicketService;
import com.tuvarna.transportsystem.services.TripService;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class UserScheduleController implements Initializable {
	ObservableList list = FXCollections.observableArrayList();

	@FXML
	private TableView<Ticket> scheduleTable;

	@FXML
	private TableColumn<Ticket, String> scheduleDepartureSt;

	@FXML
	private TableColumn<Ticket, String> scheduleArrivalSt;

	@FXML
	private TableColumn<Ticket, String> scheduleCompanyName;

	@FXML
	private TableColumn<Ticket, String> scheduleTripType;

	@FXML
	private TableColumn<Ticket, String> scheduleBusType;

	@FXML
	private TableColumn<Ticket, String> schedulePriceCol;

	@FXML
	private TableColumn<Ticket, String> scheduleDate;

	@FXML
	private TableColumn<Ticket, String> scheduleHourOfDeparture;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		scheduleDate.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(param.getValue().getTrip().getTripDepartureDate().toString());
					}
				});
		scheduleDepartureSt.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(param.getValue().getDepartureLocation().getLocationName());
					}
				});
		
		scheduleArrivalSt.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {

						return new SimpleStringProperty(
								param.getValue().getArrivalLocation().getLocationName());
					}
				});

		scheduleCompanyName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {

						int tripId = param.getValue().getTrip().getTripId();

						UserService userService = new UserService();

						if (userService.getUserByTripId(tripId) != null) {
							return new SimpleStringProperty(
									userService.getUserByTripId(tripId).get().getUserFullName());
						}

						return new SimpleStringProperty(" - ");
					}
				});

		scheduleTripType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(param.getValue().getTrip().getTripType().getTripTypeName());

					}
				});

		scheduleBusType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(param.getValue().getTrip().getTripTransportType().getTransportTypeName());

					}
				});

		scheduleHourOfDeparture.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(param.getValue().getTrip().getTripDepartureHour());

					}
				});
		
		schedulePriceCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(String.valueOf(param.getValue().getTrip().getTripTicketPrice()));

					}
				});

		/*
		 * list.removeAll(list); List<Trip> trips = new ArrayList<>();
		 * DatabaseUtils.currentUser.getTickets().forEach(t -> trips.add(t.getTrip()));
		 * list.addAll(trips);
		 */

		scheduleTable.setItems(getTickets());
	}

	public ObservableList<Ticket> getTickets() {
		ObservableList<Ticket> ticketsList = FXCollections.observableArrayList();
		List<Ticket> userTickets = DatabaseUtils.currentUser.getTickets();
		
		userTickets.forEach(t -> ticketsList.add(t));
		
		return ticketsList;
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

		DatabaseUtils.currentUser = null;
	}

	public void goToBuyTicket(javafx.event.ActionEvent event) throws IOException {
		Parent ticketPanel = FXMLLoader.load(getClass().getResource("/views/UserPanel.fxml"));
		Scene ticketScene = new Scene(ticketPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(ticketScene);
		window.show();
	}
}
