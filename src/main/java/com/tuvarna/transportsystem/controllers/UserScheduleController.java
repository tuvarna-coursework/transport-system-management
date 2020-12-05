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
	private TableView<Trip> scheduleTable;

	@FXML
	private TableColumn<Trip, String> scheduleDepartureSt;

	@FXML
	private TableColumn<Trip, String> scheduleArrivalSt;

	@FXML
	private TableColumn<Trip, String> scheduleCompanyName;

	@FXML
	private TableColumn<Trip, String> scheduleTripType;

	@FXML
	private TableColumn<Trip, String> scheduleBusType;

	@FXML
	private TableColumn<Trip, Double> schedulePriceCol;

	@FXML
	private TableColumn<Trip, Integer> scheduleDurationCol;

	@FXML
	private TableColumn<Trip, String> scheduleDate;

	@FXML
	private TableColumn<Trip, String> scheduleHourOfDeparture;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		scheduleDate.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripDepartureDate().toString());
					}
				});
		scheduleDepartureSt.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripDepartureHour());
					}
				});
		scheduleDepartureSt.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(
								param.getValue().getRoute().getRouteDepartureLocation().getLocationName());

					}
				});

		scheduleArrivalSt.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {

						return new SimpleStringProperty(
								param.getValue().getRoute().getRouteArrivalLocation().getLocationName());
					}
				});

		scheduleCompanyName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {

						int tripId = param.getValue().getTripId();

						UserService userService = new UserService();

						if (userService.getUserByTripId(tripId) != null) {
							return new SimpleStringProperty(
									userService.getUserByTripId(tripId).get().getUserFullName());
						}

						return new SimpleStringProperty(" - ");
					}
				});

		scheduleTripType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripType().getTripTypeName());

					}
				});

		scheduleBusType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripTransportType().getTransportTypeName());

					}
				});

		scheduleHourOfDeparture.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripDepartureHour());

					}
				});

		scheduleDurationCol.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("tripDuration"));
		schedulePriceCol.setCellValueFactory(new PropertyValueFactory<Trip, Double>("tripTicketPrice"));

		/*
		 * list.removeAll(list); List<Trip> trips = new ArrayList<>();
		 * DatabaseUtils.currentUser.getTickets().forEach(t -> trips.add(t.getTrip()));
		 * list.addAll(trips);
		 */

		scheduleTable.setItems(getTickets());
	}

	public ObservableList<Trip> getTickets() {
		ObservableList<Trip> tripsList = FXCollections.observableArrayList();
		TripService tripService = new TripService();

		List<Trip> eList = tripService.getAll();
		for (Trip ent : eList) {
			tripsList.add(ent);
		}
		return tripsList;
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
