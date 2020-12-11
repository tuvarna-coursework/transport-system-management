package com.tuvarna.transportsystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
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
		cashierScheduleTable.setItems(getTripSchedule());

	}

	private ObservableList<Trip> getTripSchedule() {
		ObservableList<Trip> tripList = FXCollections.observableArrayList();
		TripService tripService = new TripService();

		/*
		 * Cashier accesses trips if they are the cashier assigned for it (exists in
		 * TripCashier table)
		 */
		List<Trip> eList = tripService.getAll();
		for (Trip ent : eList) {
			if (ent.getCashiers().contains(DatabaseUtils.currentUser)) {
				tripList.add(ent);
			}
		}
		return tripList;

	}

	private ObservableList<String> getDepartureLocations() {

		if (cashierScheduleTable.getSelectionModel().getSelectedItem().equals(null)) {
			informationLabel.setText("No route selected.");
			return null;
		}

		Trip trip = cashierScheduleTable.getSelectionModel().getSelectedItem();
		int routeId = trip.getRoute().getRouteId();

		RouteService routeService = new RouteService();

		/*
		 * A cashier can sell a ticket departuring from the location they work in.
		 * That's why we filter it. Most likely it will return 1 result
		 */

		/*
		 * DEBUG, correct code is below List<Location> departureLocation =
		 * routeService.getAttachmentLocationsInRouteById(routeId);
		 */
		List<Location> departureLocation = routeService.getAttachmentLocationsInRouteById(routeId).stream()
				.filter(l -> l.getLocationName().equals(DatabaseUtils.currentUser.getUserLocation().getLocationName()))
				.collect(Collectors.toList());

		ObservableList<String> locationList = FXCollections.observableArrayList();
		departureLocation.forEach(l -> locationList.add(l.getLocationName()));

		return locationList;
	}

	private ObservableList<String> getArrivalLocations() {
		Trip trip = cashierScheduleTable.getSelectionModel().getSelectedItem();
		int routeId = trip.getRoute().getRouteId();

		RouteService routeService = new RouteService();

		/*
		 * The arrival location cannot be the same as the current location of the
		 * cashier, so fetch everything else
		 */
		List<Location> departureLocation = routeService.getAttachmentLocationsInRouteById(routeId).stream().filter(
				l -> !(l.getLocationName().equals(DatabaseUtils.currentUser.getUserLocation().getLocationName())))
				.collect(Collectors.toList());
		
		departureLocation.add(trip.getRoute().getRouteArrivalLocation());

		ObservableList<String> locationList = FXCollections.observableArrayList();
		departureLocation.forEach(l -> locationList.add(l.getLocationName()));

		return locationList;
	}
	public void showAttachments(javafx.event.ActionEvent event) throws IOException {
		if(cashierScheduleTable.getSelectionModel().getSelectedItem() == null){
			informationLabel.setText("Select trip first!");
			return;
		}
		Trip tripSend = cashierScheduleTable.getSelectionModel().getSelectedItem();
		Stage stage = new Stage();
		FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/CashierShowRouteAttachments.fxml"));
		DialogPane root = (DialogPane) userPanel.load();
		//send trip to other controller
		CashierShowRouteAttachmentsController controller = (CashierShowRouteAttachmentsController) userPanel.getController();
		controller.getTrip(tripSend);

		Scene adminScene = new Scene(root);
		stage.setScene(adminScene);
		stage.setTitle("Transport Company");
		stage.showAndWait();
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
		UserService userService = new UserService();
		ObservableList<String> list = FXCollections.observableArrayList();

		userService.getAll().forEach(u -> list.add(u.getUserLoginName()));
		return list;
	}

	public void loadFields(javafx.event.ActionEvent event) throws IOException {
		if (cashierScheduleTable.getSelectionModel().getSelectedItem() == null) {
			informationLabel.setText("No route selected.");
			return;
		}

		departureChoiceBox.getItems().removeAll(departureChoiceBox.getItems());
		arrivalChoiceBox.getItems().removeAll(arrivalChoiceBox.getItems());
		quantityChoiceBox.getItems().removeAll(quantityChoiceBox.getItems());
		userChoiceBox.getItems().removeAll(userChoiceBox.getItems());

		departureChoiceBox.getItems().addAll(this.getDepartureLocations());
		arrivalChoiceBox.getItems().addAll(this.getArrivalLocations());
		quantityChoiceBox.getItems().addAll(this.getQuantities());

		userChoiceBox.getItems().addAll(this.getUsers());
	}

	public void sellTicket(javafx.event.ActionEvent event) throws IOException {
		if (departureChoiceBox.getValue() == null) {
			informationLabel.setText("Please select a departure location.");
			return;
		}
		
		if (arrivalChoiceBox.getValue() == null) {
			informationLabel.setText("Please select an arrival location.");
			return;
		}
		
		if (quantityChoiceBox.getValue() == null){
			informationLabel.setText("Please select ticket quantity.");
			return;
		}
		
		if (!customerIsGuest.isSelected() && (!customerIsRegistered.isSelected())) {
			informationLabel.setText("Please select if the customer is a guest or is registered.");
			return;
		}

		UserService userService = new UserService();
		User user = null;

		if (customerIsRegistered.isSelected()) {
			if (userChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select a customer from the dropdown menu.");
				return;
			}

			String userName = userChoiceBox.getSelectionModel().getSelectedItem();

			if (!userService.getByName(userName).isPresent()) {
				System.out.println("USER NOT FOUND IN DATABASE");
				return;
			}

			user = userService.getByName(userName).get();

		} else if (customerIsGuest.isSelected()) {
			if (userFullNameTextField.getText().equals(null)) {
				informationLabel.setText("Please enter the name of the customer.");
				return;
			}

			/*
			 * If the user is a guest, a new profile will be created. Generated username and
			 * password, the location is the location from where the ticket is bought
			 */

			String fullName = userFullNameTextField.getText().trim();

			UserProfileService userProfileService = new UserProfileService();
			UserProfile userProfile = new UserProfile();
			userProfileService.save(userProfile);

			Location userLocation = new LocationService()
					.getByName(departureChoiceBox.getSelectionModel().getSelectedItem()).get();

			user = new User(fullName, DatabaseUtils.generateUserName(fullName), DatabaseUtils.generatePassword(),
					userProfile, DatabaseUtils.USERTYPE_USER, userLocation);
		}

		Trip trip = cashierScheduleTable.getSelectionModel().getSelectedItem();
		int ticketsToPurchase = Integer.parseInt(quantityChoiceBox.getSelectionModel().getSelectedItem());

		LocationService locationService = new LocationService();
		Location departureLocation = locationService.getByName(departureChoiceBox.getSelectionModel().getSelectedItem())
				.get();
		Location arrivalLocation = locationService.getByName(arrivalChoiceBox.getSelectionModel().getSelectedItem())
				.get();

		TripService tripService = new TripService();
		tripService.updateTripTicketAvailability(trip, trip.getTripTicketAvailability() - ticketsToPurchase);

		TicketService ticketService = new TicketService();
		Ticket ticket = new Ticket(new Date(System.currentTimeMillis()), trip, departureLocation, arrivalLocation);
		ticketService.save(ticket);

		userService.addTicket(user, ticket);

		// For every 5 purchased tickets, the user gains a rating of 0.2
		if (DatabaseUtils.currentUser.getTickets().size() % 5 == 0) {
			new UserProfileService().increaseRating(DatabaseUtils.currentUser.getUserProfile(), 0.2);
		}

		User company = userService.getUserByTripId(trip.getTripId()).get();

		/*
		 * Iterate through the tickets, check if the trip they belong to matches the
		 * user id of this company's id. Basically, get all tickets sold by this
		 * company.
		 */
		List<Ticket> ticketsSoldByCompany = ticketService.getAll().stream().filter(
				t -> userService.getUserByTripId(t.getTrip().getTripId()).get().getUserId() == company.getUserId())
				.collect(Collectors.toList());

		if (ticketsSoldByCompany.size() % 5 == 0) {
			new UserProfileService().increaseRating(company.getUserProfile(), 0.1);
		}

		informationLabel.setText("You sold a ticket.");
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

	}
	public void goToLogIn(javafx.event.ActionEvent event) throws IOException {
		Parent schedulePanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
		Scene scheduleScene = new Scene(schedulePanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scheduleScene);
		window.show();
		
		DatabaseUtils.currentUser = null;

	}
	public void goToNotifications(javafx.event.ActionEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/CashierNotificationsPanel.fxml"));
		AnchorPane root = (AnchorPane) userPanel.load();
		Scene adminScene = new Scene(root);
		stage.setScene(adminScene);
		stage.setTitle("Transport Company");
		stage.showAndWait();
	}
}
