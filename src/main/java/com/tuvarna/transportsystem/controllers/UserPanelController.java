package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.services.*;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UserPanelController implements Initializable {
	ObservableList list = FXCollections.observableArrayList();
	boolean isSearchHidden = false;

	@FXML
	private ChoiceBox<String> departureChoiceBox;

	@FXML
	private ChoiceBox<String> arrivalChoiceBox;
	@FXML
	private ChoiceBox<String> quantityChoiceBox;
	@FXML
	private DatePicker tripDatePicker;
	@FXML
	private ChoiceBox<String> timeChoiceBox;
	@FXML
	private Label informationLabel;

	@FXML
	private Label departureLocationLabel;

	@FXML
	private Label arrivalLocationLabel;

	@FXML
	private Label quantityLabel;

	@FXML
	private Label timeLabel;

	@FXML
	private TableView<Trip> availableTripsTable;

	@FXML
	private Button hideShowToggle;

	@FXML
	private Button searchBuyToggle;

	@FXML
	private TableColumn<Trip, String> dateCol;

	@FXML
	private TableColumn<Trip, String> hourCol;

	@FXML
	private TableColumn<Trip, String> departureStationCol;

	@FXML
	private TableColumn<Trip, String> arrivalStationCol;

	@FXML
	private TableColumn<Trip, Integer> durationCol;

	@FXML
	private TableColumn<Trip, Integer> ticketsLeftCol;

	@FXML
	private TableColumn<Trip, Double> priceCol;

	private static final Logger logger = LogManager.getLogger(UserPanelController.class.getName());
	private UserService userService;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		userService = new UserService();
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");

		loadquantity();
		loadTime();
		departureChoiceBox.setItems(getLocation());
		arrivalChoiceBox.setItems(getLocation());

		dateCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripDepartureDate().toString());
					}
				});
		hourCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripDepartureHour());
					}
				});
		departureStationCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						/*
						 * If the departure station matches the searched station, add the departure
						 * station. If the route is Varna - Sofia and the customer wants a ticket from
						 * Varna.
						 * 
						 * But if the customer wants a ticket from a bus stop in Veliko Tarnovo - Sofia
						 * then display Veliko Tarnovo as departure station for the customer. At this
						 * point it is sure that this location is in the RouteAttachment table since it
						 * is validated elsewhere
						 */
						if (param.getValue().getRoute().getRouteDepartureLocation().getLocationName()
								.equals(departureChoiceBox.getSelectionModel().getSelectedItem())) {
							return new SimpleStringProperty(
									param.getValue().getRoute().getRouteDepartureLocation().getLocationName());
						}

						return new SimpleStringProperty(departureChoiceBox.getSelectionModel().getSelectedItem());
					}
				});

		arrivalStationCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						if (param.getValue().getRoute().getRouteArrivalLocation().getLocationName()
								.equals(departureChoiceBox.getSelectionModel().getSelectedItem())) {
							return new SimpleStringProperty(
									param.getValue().getRoute().getRouteArrivalLocation().getLocationName());
						}

						return new SimpleStringProperty(arrivalChoiceBox.getSelectionModel().getSelectedItem());
					}
				});

		durationCol.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("tripDuration"));
		priceCol.setCellValueFactory(new PropertyValueFactory<Trip, Double>("tripTicketPrice"));
		ticketsLeftCol.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("tripTicketAvailability"));

		logger.info("Loaded user panel.");
	}

	private List<Trip> getMatchingTrips() throws ParseException {
		String constraintCheck = userService.userPanelGetMatchingTripsValidation(
				departureChoiceBox.getSelectionModel().getSelectedItem(),
				arrivalChoiceBox.getSelectionModel().getSelectedItem(), tripDatePicker.getEditor(),
				quantityChoiceBox.getSelectionModel().getSelectedItem(),
				timeChoiceBox.getSelectionModel().getSelectedItem());

		if (!constraintCheck.equals("Success")) {
			informationLabel.setText(constraintCheck);
			return null;
		}

		logger.info("Returning a list of trips that match the search criteria of the customer.");
		return userService.userPanelGetMatchingTripsProcessing(departureChoiceBox.getSelectionModel().getSelectedItem(),
				arrivalChoiceBox.getSelectionModel().getSelectedItem(), tripDatePicker.getEditor(),
				quantityChoiceBox.getSelectionModel().getSelectedItem(),
				timeChoiceBox.getSelectionModel().getSelectedItem());
	}

	public void loadquantity() {
		list.removeAll(list);
		String number_01 = ("1");
		String number_02 = ("2");
		String number_03 = ("3");
		String number_04 = ("4");
		String number_05 = ("5");
		String number_06 = ("6");
		String number_07 = ("7");
		String number_08 = ("8");
		String number_09 = ("9");
		String number_10 = ("10");

		list.addAll(number_01, number_02, number_03, number_04, number_05, number_06, number_07, number_08, number_09,
				number_10);
		quantityChoiceBox.getItems().addAll(list);

	}

	public void loadTime() {
		list.removeAll(list);
		String time_01 = "00:00";
		String time_02 = "00:30";
		String time_03 = "02:00";
		String time_04 = "04:30";
		String time_05 = "06:00";
		String time_06 = "07:30";
		String time_07 = "08:00";
		String time_08 = "09:30";
		String time_09 = "10:45";
		String time_10 = "11:30";
		String time_11 = "12:15";
		String time_12 = "13:30";
		String time_13 = "14:00";
		String time_14 = "15:30";
		String time_15 = "17:00";
		String time_16 = "17:30";
		String time_17 = "18:00";
		String time_18 = "18:15";
		String time_19 = "19:00";
		String time_20 = "20:30";
		String time_21 = "21:15";
		String time_22 = "22:05";
		String time_23 = "22:30";
		String time_24 = "23:30";
		String time_25 = "23:45";

		list.addAll(time_01, time_02, time_03, time_04, time_05, time_06, time_07, time_08, time_09, time_10, time_11,
				time_12, time_13, time_14, time_15, time_16, time_17, time_18, time_19, time_20, time_21, time_22,
				time_23, time_24, time_25);

		timeChoiceBox.getItems().addAll(list);
	}

	private ObservableList<String> getLocation() {
		ObservableList<String> locationList = FXCollections.observableArrayList();
		LocationService locationService = new LocationService();
		/*
		 * Loading locations from data base.
		 */
		List<Location> eList = locationService.getAll();
		for (Location ent : eList) {
			locationList.add(ent.getLocationName());

		}
		return locationList;
	}

	public void goToSchedule(javafx.event.ActionEvent event) throws IOException {
		Parent schedulePanel = FXMLLoader.load(getClass().getResource("/views/SchedulePanel.fxml"));
		Scene scheduleScene = new Scene(schedulePanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scheduleScene);
		window.show();

		logger.info("Switched to schedule tab.");
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

	public void toggleButtonAction(javafx.event.ActionEvent event) throws IOException, ParseException {
		if (isSearchHidden) {
			buyTicket();
		} else {
			searchTickets(event);
		}
	}

	public void buyTicket() throws ParseException {
		String result = userService.userPanelBuyTicket(availableTripsTable.getSelectionModel().getSelectedItem(),
				DatabaseUtils.currentUser, departureChoiceBox.getSelectionModel().getSelectedItem(),
				arrivalChoiceBox.getSelectionModel().getSelectedItem(),
				quantityChoiceBox.getSelectionModel().getSelectedItem());

		if (!result.equals("Success")) {
			informationLabel.setText(result);
			return;
		}

		informationLabel.setText("You bought a ticket.");
	}

	public void searchTickets(javafx.event.ActionEvent event) throws ParseException, IOException {
		list.removeAll(list);
		if (this.getMatchingTrips() == null) {
			return;
		}

		list.addAll(this.getMatchingTrips());

		/* Filter all trips that match the search criteria */
		toggleView(event);
		availableTripsTable.setItems(list);
		logger.info("Search results returned.");
	}

	public void toggleView(javafx.event.ActionEvent event) throws IOException, ParseException {
		arrivalChoiceBox.setVisible(isSearchHidden);
		departureChoiceBox.setVisible(isSearchHidden);
		arrivalChoiceBox.setVisible(isSearchHidden);
		tripDatePicker.setVisible(isSearchHidden);
		timeChoiceBox.setVisible(isSearchHidden);
		quantityChoiceBox.setVisible(isSearchHidden);
		departureLocationLabel.setVisible(isSearchHidden);
		arrivalLocationLabel.setVisible(isSearchHidden);
		quantityLabel.setVisible(isSearchHidden);
		timeLabel.setVisible(isSearchHidden);

		availableTripsTable.setVisible(!isSearchHidden);

		hideShowToggle.setText((isSearchHidden) ? "Hide search" : "Show search");
		searchBuyToggle.setText((isSearchHidden) ? "Search" : "Buy");

		/* Expand to show the table in full view, shrink for the search */
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setWidth((!isSearchHidden) ? 760 : 620);

		isSearchHidden = !isSearchHidden;
	}
}
