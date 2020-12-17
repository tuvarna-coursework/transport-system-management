package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.RouteService;
import com.tuvarna.transportsystem.services.TripService;
import com.tuvarna.transportsystem.services.UserService;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DistributorScheduleController implements Initializable {
	@FXML
	private TableView<Trip> scheduleTable;
	@FXML
	private TableColumn<Trip, String> col_departure;
	@FXML
	private TableColumn<Trip, Integer> col_capacity;
	@FXML
	private TableColumn<Trip, String> col_departureDate;
	@FXML
	private TableColumn<Trip, String> col_arrival;
	@FXML
	private TableColumn<Trip, Integer> col_availableTickets;
	@FXML
	private TableColumn<Trip, String> col_tripType;
	@FXML
	private TableColumn<Trip, String> col_transportType;
	@FXML
	private TableColumn<Trip, Double> col_price;
	@FXML
	private TableColumn<Trip, Integer> col_duration;

	@FXML
	private TableColumn<Trip, String> col_hourOfDeparture;

	@FXML
	private Button viewAttachmentLocationsButton;

	@FXML
	private Label informationLabel;
	@FXML
	private ChoiceBox<String> locationChoiceBox;
	@FXML
	private ComboBox<String> cashierComboBox;

	ObservableList list = FXCollections.observableArrayList();

	private static final Logger logger = LogManager.getLogger(DistributorScheduleController.class.getName());
	private UserService userService;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		userService = new UserService();
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");

		locationChoiceBox.setItems(getLocation());
		cashierComboBox.setItems(getCashiers());
		col_departure.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(
								param.getValue().getRoute().getRouteDepartureLocation().getLocationName());
					}
				});
		col_departureDate.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripDepartureDate().toString());
					}
				});
		col_arrival.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(
								param.getValue().getRoute().getRouteArrivalLocation().getLocationName());
					}
				});

		col_tripType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripType().getTripTypeName());
					}
				});
		col_transportType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripTransportType().getTransportTypeName());
					}
				});

		col_hourOfDeparture.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripDepartureHour());
					}
				});

		col_duration.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("tripDuration"));
		col_price.setCellValueFactory(new PropertyValueFactory<Trip, Double>("tripTicketPrice"));
		col_availableTickets.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("tripTicketAvailability"));
		col_capacity.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("tripCapacity"));
		scheduleTable.setItems(getTripSchedule());

		logger.info("Loaded distributor schedule.");
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

	public ObservableList<Trip> getTripSchedule() {
		ObservableList<Trip> tripList = FXCollections.observableArrayList();
		TripService tripService = new TripService();
		RouteService routeService = new RouteService();

		List<Trip> eList = tripService.getAll();
		for (Trip ent : eList) {
			tripList.add(ent);
		}
		return tripList;
	}

	public void goToNotifications(javafx.event.ActionEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/DistributorNotificationsPanel.fxml"));
		AnchorPane root = (AnchorPane) userPanel.load();
		Scene adminScene = new Scene(root);
		stage.setScene(adminScene);
		stage.setTitle("Transport Company");
		stage.showAndWait();

		logger.info("Switched to notifications tab.");
	}

	public void goToRequest(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorRequestPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		logger.info("Switched to requests tab.");
	}

	public void goToAddCashier(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorAddPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		logger.info("Switched to add cashier tab.");
	}

	public void goToLogIn(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		DatabaseUtils.currentUser = null;
		logger.info("User logged out.");
	}

	public void showStations(javafx.event.ActionEvent event) throws IOException {
		String result = userService
				.distributorScheduleLoadAttachments(scheduleTable.getSelectionModel().getSelectedItem());

		if (!result.equals("Success")) {
			informationLabel.setText(result);
		}
	}

	public ObservableList<String> getCashiers() {
		ObservableList<String> userList = FXCollections.observableArrayList();
		UserService userService = new UserService();
		String type = "Cashier";
		List<User> eList = userService.getByUserType(type);
		for (User ent : eList) {
			/*
			 * Displaying usernames and specify the user location for ease of use, if we use
			 * the full name there may be more than 1 result
			 */
			userList.add(ent.getUserLoginName() + "(" + ent.getUserLocation().getLocationName() + ")");
		}
		return userList;
	}

	public void assignCashier() {
		String result = userService.distributorAssignCashierProcessing(
				scheduleTable.getSelectionModel().getSelectedItem(),
				locationChoiceBox.getSelectionModel().getSelectedItem(),
				cashierComboBox.getSelectionModel().getSelectedItem());

		if (!result.equals("Success")) {
			informationLabel.setText(result);
			return;
		}

		informationLabel.setText("Assigned cashier for the selected location.");
	}
}
