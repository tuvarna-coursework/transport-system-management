package com.tuvarna.transportsystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.entities.UserType;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.RouteService;
import com.tuvarna.transportsystem.services.TicketService;
import com.tuvarna.transportsystem.services.TripService;
import com.tuvarna.transportsystem.services.UserProfileService;
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

public class CashierScheduleController implements Initializable {
	@FXML
	private TableView<Trip> cashierScheduleTable;
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
	private Button loadAttachmentRoutes;

	@FXML
	private Button sellTicket;

	@FXML
	private ChoiceBox<String> departureChoiceBox;

	@FXML
	private ChoiceBox<String> arrivalChoiceBox;

	@FXML
	private ChoiceBox<String> quantityChoiceBox;

	@FXML
	private RadioButton customerIsGuest;

	@FXML
	private RadioButton customerIsRegistered;

	@FXML
	private ChoiceBox<String> userChoiceBox;

	@FXML
	private TextField userFullNameTextField;

	@FXML
	private Label informationLabel;

	boolean isInitialized = false;

	private static final Logger logger = LogManager.getLogger(CashierScheduleController.class.getName());
	private UserService userService;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		userService = new UserService();
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");

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
		cashierScheduleTable.setItems(userService.getScheduleForCashier(DatabaseUtils.currentUser));

		logger.info("Configured table view and populated trips.");
	}

	private void loadDepartureLocations() {
		ObservableList<String> locations = userService.getDepLocationsForCashierBySelectedRow(DatabaseUtils.currentUser,
				cashierScheduleTable.getSelectionModel().getSelectedItem());

		if (locations.get(0).equals("No route selected.")) {
			informationLabel.setText(locations.get(0));
			return;
		}

		departureChoiceBox.getItems().addAll(locations);
		logger.info("Departure stations loaded.");
	}

	private void loadArrivalLocations() {
		ObservableList<String> locations = userService.getArrLocationsForCashierBySelectedRow(DatabaseUtils.currentUser,
				cashierScheduleTable.getSelectionModel().getSelectedItem());

		if (locations.get(0).equals("No route selected.")) {
			informationLabel.setText(locations.get(0));
			return;
		}

		arrivalChoiceBox.getItems().addAll(locations);
		logger.info("Arrival stations loaded.");
	}

	public void showAttachments(javafx.event.ActionEvent event) throws IOException {
		if (cashierScheduleTable.getSelectionModel().getSelectedItem() == null) {
			informationLabel.setText("Select trip first!");
			return;
		}
		Trip tripSend = cashierScheduleTable.getSelectionModel().getSelectedItem();
		Stage stage = new Stage();
		FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/CashierShowRouteAttachments.fxml"));
		DialogPane root = (DialogPane) userPanel.load();
		// send trip to other controller
		CashierShowRouteAttachmentsController controller = (CashierShowRouteAttachmentsController) userPanel
				.getController();
		controller.getTrip(tripSend);

		Scene adminScene = new Scene(root);
		stage.setScene(adminScene);
		stage.setTitle("Transport Company");
		stage.showAndWait();

		logger.info("Displaying attachment locations for selected trip.");
	}

	private ObservableList<String> getQuantities() {
		ObservableList<String> list = FXCollections.observableArrayList();

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

		return list;
	}

	private ObservableList<String> getUsers() {
		ObservableList<String> list = FXCollections.observableArrayList();

		userService.getAll().forEach(u -> list.add(u.getUserLoginName()));
		return list;
	}

	public void loadFields(javafx.event.ActionEvent event) throws IOException {
		departureChoiceBox.getItems().removeAll(departureChoiceBox.getItems());
		arrivalChoiceBox.getItems().removeAll(arrivalChoiceBox.getItems());
		quantityChoiceBox.getItems().removeAll(quantityChoiceBox.getItems());
		userChoiceBox.getItems().removeAll(userChoiceBox.getItems());

		loadDepartureLocations();
		loadArrivalLocations();

		quantityChoiceBox.getItems().addAll(this.getQuantities());
		userChoiceBox.getItems().addAll(this.getUsers());
		logger.info("Loaded locations and potential customers for selected trip.");
	}

	public void sellTicket(javafx.event.ActionEvent event) throws IOException {
		String result = userService.cashierSellTicketProcessing(
				cashierScheduleTable.getSelectionModel().getSelectedItem(),
				departureChoiceBox.getSelectionModel().getSelectedItem(),
				arrivalChoiceBox.getSelectionModel().getSelectedItem(),
				quantityChoiceBox.getSelectionModel().getSelectedItem(), customerIsGuest.isSelected(),
				customerIsRegistered.isSelected(), userChoiceBox.getSelectionModel().getSelectedItem(),
				userFullNameTextField.getText());
		
		if (!result.equals("Success")) {
			informationLabel.setText(result);
			return;
		}
		
		informationLabel.setText("You sold a ticket.");
		logger.info("Successfully sold ticket.");
	}

	public void radioButtonOnClick(javafx.event.ActionEvent event) throws IOException {
		if (customerIsGuest.isSelected()) {
			userChoiceBox.setDisable(true);
			userFullNameTextField.setDisable(false);
		} else if (customerIsRegistered.isSelected()) {
			userChoiceBox.setDisable(false);
			userFullNameTextField.setDisable(true);
		}
	}

	public void goToSoldTickets(javafx.event.ActionEvent event) throws IOException {
		Parent schedulePanel = FXMLLoader.load(getClass().getResource("/views/CashierSoldPanel.fxml"));
		Scene scheduleScene = new Scene(schedulePanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scheduleScene);
		window.show();

		logger.info("Loaded 'sold tickets' view.");
	}

	public void goToLogIn(javafx.event.ActionEvent event) throws IOException {
		Parent schedulePanel = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
		Scene scheduleScene = new Scene(schedulePanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scheduleScene);
		window.show();

		DatabaseUtils.currentUser = null;
		logger.info("User logged off.");

	}

	public void goToNotifications(javafx.event.ActionEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/CashierNotificationsPanel.fxml"));
		AnchorPane root = (AnchorPane) userPanel.load();
		Scene adminScene = new Scene(root);
		stage.setScene(adminScene);
		stage.setTitle("Transport Company");
		stage.showAndWait();

		logger.info("Loaded notifications view.");
	}
}
