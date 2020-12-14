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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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

	private static final Logger logger = LogManager.getLogger(UserScheduleController.class.getName());

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");

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

						return new SimpleStringProperty(param.getValue().getArrivalLocation().getLocationName());
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
						return new SimpleStringProperty(
								param.getValue().getTrip().getTripTransportType().getTransportTypeName());

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
						return new SimpleStringProperty(
								String.valueOf(param.getValue().getTrip().getTripTicketPrice()));

					}
				});

		scheduleTable.setItems(getTickets());
		logger.info("Loaded user schedule and populated with the trips they have tickets for.");
	}

	public ObservableList<Ticket> getTickets() {
		ObservableList<Ticket> ticketsList = FXCollections.observableArrayList();
		List<Ticket> userTickets = DatabaseUtils.currentUser.getTickets();

		userTickets.forEach(t -> ticketsList.add(t));

		return ticketsList;
	}

	public void logOut(javafx.event.ActionEvent event) throws IOException {
		Parent ticketPanel = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
		Scene ticketScene = new Scene(ticketPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(ticketScene);
		window.show();

		DatabaseUtils.currentUser = null;
		logger.info("User logged out.");
	}

	public void goToBuyTicket(javafx.event.ActionEvent event) throws IOException {
		Parent ticketPanel = FXMLLoader.load(getClass().getResource("/views/UserPanel.fxml"));
		Scene ticketScene = new Scene(ticketPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(ticketScene);
		window.show();
		logger.info("Switched to buy tickets tab.");
	}
}
