package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.services.UserService;
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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.services.RouteService;
import com.tuvarna.transportsystem.services.TripService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

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

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadLocations();
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

	}
	public void loadLocations(){
		list.removeAll(list);
		String city_01 = "Varna";
		String city_02 = "Sofia";
		String city_03 = "Shumen";
		String city_04 = "Veliko Turnovo";
		String city_05 = "Razgrad";
		String city_06 = "Gabrovo";
		String city_07 = "Plovdiv";
		String city_08 = "Burgas";
		String city_09 = "Stara Zagora";
		String city_10 = "Blagoevgrad";
		String city_11 = "Sliven";
		String city_12 = "Pleven";
		String city_13 = "Omurtag";
		String city_14 = "Ruse";
		String city_15 = "Dobrich";
		String city_16 = "Montana";
		String city_17 = "Vraca";
		String city_18 = "Yambol";
		String city_19 = "Pernik";
		String city_20 = "Lovech";
		String city_21 = "Turgovishte";
		list.addAll(city_01, city_02, city_03, city_04, city_05, city_06, city_07, city_08, city_09, city_10, city_11,
				city_12, city_13, city_14, city_15, city_16, city_17, city_18, city_19, city_20,city_21);
		locationChoiceBox.getItems().addAll(list);
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
	}

	public void goToRequest(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorRequestPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

	}

	public void goToAddCashier(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorAddPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

	}

	public void goToLogIn(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

	}

	public void showAttachmentLocations(javafx.event.ActionEvent event) throws IOException {
		Trip selectedTrip = scheduleTable.getSelectionModel().getSelectedItem();

		if (selectedTrip.equals(null)) {
			informationLabel.setText("Please select a trip.");
			return;
		}

		/* Await .fxml and controller for the new popup */

	}
	public ObservableList<String> getCashiers() {
		ObservableList<String> userList = FXCollections.observableArrayList();
		UserService userService = new UserService();
		String type = "Cashier";
		List<User> eList = userService.getByUserType(type);
		for (User ent : eList) {
			userList.add(ent.getUserFullName());
		}
		return userList;
	}
	public void assignCashier(){
		Trip trip = scheduleTable.getSelectionModel().getSelectedItem();
		if(trip == null ) {
			informationLabel.setText("Please select trip!");
			return;
		}

		if(locationChoiceBox.getSelectionModel().getSelectedIndex() < 0){
			informationLabel.setText("Please select location!");
			return;
		}
		if(cashierComboBox.getSelectionModel().getSelectedIndex() < 0){
			informationLabel.setText("Please select cashier!");
			return;
		}
		// NOT FINISHED!
	}
}
