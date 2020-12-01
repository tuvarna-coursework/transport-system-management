package com.tuvarna.transportsystem.controllers;

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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.RouteService;
import com.tuvarna.transportsystem.services.TicketService;
import com.tuvarna.transportsystem.services.TripService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

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

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadquantity();
		loadTime();
		loadDepartureArrivalLocation();

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

	}

	private List<Trip> getMatchingTrips() throws ParseException {
		if (departureChoiceBox.getSelectionModel().getSelectedItem().equals(null)) {
			informationLabel.setText("Please select departure station.");
			return null;
		}

		if (arrivalChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
			informationLabel.setText("Please select arrival station.");
			return null;
		}

		if (tripDatePicker.getValue().equals(null)) {
			informationLabel.setText("Please select a date.");
			return null;
		}

		if (quantityChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
			informationLabel.setText("Please select quantity.");
			return null;
		}

		if (timeChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
			informationLabel.setText("Please add time.");
			return null;
		}

		String departureStation = departureChoiceBox.getValue().trim();
		String arrivalStation = arrivalChoiceBox.getValue().trim();

		TripService tripService = new TripService();
		List<Trip> fullTrips = tripService.getAll();

		/*
		 * Uses local machine's format and since it contains HH:MM:SS as well, it is
		 * splitted and only the date is taken.
		 */
		String dateFormatPattern = new SimpleDateFormat().toLocalizedPattern().split(" ")[0];
		DateFormat formatDepartureDate = new SimpleDateFormat(dateFormatPattern);

		Date dateDeparture = formatDepartureDate.parse(tripDatePicker.getEditor().getText());

		List<Trip> filteredTrips = new ArrayList<>();

		/* Iterate through the trips and validate all the fields */
		for (Trip trip : fullTrips) {
			Date dbDate = trip.getTripDepartureDate();

			/*
			 * Probably the least intuitive approach to fix the difference in formats
			 * between the current machine and postgresql date but it works and is universal
			 */
			boolean matchesDates = dbDate.getYear() == dateDeparture.getYear()
					&& dbDate.getMonth() == dateDeparture.getMonth() && dbDate.getDay() == dateDeparture.getDay();
			boolean checkQuantity = Integer.parseInt(quantityChoiceBox.getValue()) <= trip.getMaxTicketsPerUser();
			boolean matchesTime = trip.getTripDepartureHour()
					.contentEquals(timeChoiceBox.getSelectionModel().getSelectedItem().trim());
			boolean tripEndPointsSearched = trip.getRoute().getRouteDepartureLocation().getLocationName()
					.equals(departureStation)
					&& trip.getRoute().getRouteArrivalLocation().getLocationName().equals(arrivalStation);
			boolean tripDepartureMiddlePointSearched = false;
			boolean tripArrivalMiddlePointSearched = false;
			boolean endPointToMiddlePointSearched = false;
			boolean middlePointToEndPointSearched = false;

			RouteService routeService = new RouteService();
			List<Location> attachmentLocations = routeService
					.getAttachmentLocationsInRouteById(trip.getRoute().getRouteId());

			/*
			 * Scenario: departure station matches start location of the route but the
			 * arrival station searched doesn't match the end of the route. We are searching
			 * for: an attachment location with the searched arrival location
			 */
			if (trip.getRoute().getRouteDepartureLocation().getLocationName().equals(departureStation)
					&& (!trip.getRoute().getRouteArrivalLocation().getLocationName().equals(arrivalStation))) {

				for (Location location : attachmentLocations) {
					if (location.getLocationName().equals(arrivalStation)) {
						endPointToMiddlePointSearched = true;
						break;
					}
				}
			}
			/*
			 * Scenario: Arrival station matches the end point of the route but the
			 * departure station is probably a middle point. Check it.
			 */
			if ((!trip.getRoute().getRouteDepartureLocation().getLocationName().equals(departureStation))
					&& (trip.getRoute().getRouteArrivalLocation().getLocationName().equals(arrivalStation))) {

				for (Location attachmentLocation : attachmentLocations) {
					if (attachmentLocation.getLocationName().equals(departureStation)) {
						middlePointToEndPointSearched = true;

						int routeId = trip.getRoute().getRouteId();
						int locationId = attachmentLocation.getLocationId();

						/*
						 * Initially, matchesTime compares the start point of the route with the
						 * searched time. If we are buying a ticket from a middle point, it takes time
						 * until the bus reaches that location and we are searching from another hour.
						 * In RouteAttachment it is logged when the bus arrives at the middle point.
						 */
						matchesTime = timeChoiceBox.getSelectionModel().getSelectedItem()
								.equals(routeService.getArrivalHourAtAttachmentLocation(routeId, locationId));

						break;
					}
				}
			}

			/*
			 * Scenario: We are searching for a trip between 2 middle points. Check if the
			 * departure location is present in the RouteAttachment table
			 */
			for (Location attachmentLocation : attachmentLocations) {
				if (attachmentLocation.getLocationName().equals(departureStation)) {
					tripDepartureMiddlePointSearched = true;

					int routeId = trip.getRoute().getRouteId();
					int locationId = attachmentLocation.getLocationId();

					matchesTime = timeChoiceBox.getSelectionModel().getSelectedItem()
							.equals(routeService.getArrivalHourAtAttachmentLocation(routeId, locationId));

					break;
				}
			}

			/* Same for arrival */
			for (Location attachmentLocation : attachmentLocations) {
				if (attachmentLocation.getLocationName().equals(arrivalStation)) {
					tripArrivalMiddlePointSearched = true;
					break;
				}
			}

			/* If all the criteria matches check if there are enough available tickets */
			if (matchesDates && checkQuantity && matchesTime) {

				/*
				 * If either the customer chose the start and end point of the route (Sofia -
				 * Varna) or they chose two valid middle points from the RouteAttachment table
				 * (for example Shumen - Veliko Tarnovo) then a purchase can proceed.
				 * 
				 * Also, if the customer chose (Sofia - Veliko Tarnovo) (start of route - middle
				 * point) or they chose (Veliko Tarnovo - Varna) (middle point - end of route)
				 * this is also a valid search
				 */
				if (tripEndPointsSearched || (tripDepartureMiddlePointSearched && tripArrivalMiddlePointSearched)
						|| (endPointToMiddlePointSearched || middlePointToEndPointSearched)) {

					int ticketsToPurchase = Integer.parseInt(quantityChoiceBox.getValue());

					/* If there are enough tickets substitute the bought tickets */
					if (trip.getTripTicketAvailability() >= ticketsToPurchase) {
						filteredTrips.add(trip);
					}
				}
			}
		}
		return filteredTrips;
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
		String time_02 = "03:15";
		String time_03 = "06:30";
		String time_04 = "08:00";
		String time_05 = "09:15";
		String time_06 = "11:00";
		String time_07 = "12:30";
		String time_08 = "14:10";
		String time_09 = "15:00";
		String time_10 = "17:05";
		String time_11 = "18:30";
		String time_12 = "19:55";
		String time_13 = "21:00";
		String time_14 = "22:30";
		list.addAll(time_01, time_02, time_03, time_04, time_05, time_06, time_07, time_08, time_09, time_10, time_11,
				time_12, time_13, time_14);
		timeChoiceBox.getItems().addAll(list);
	}

	public void loadDepartureArrivalLocation() {
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
				city_12, city_13, city_14, city_15, city_16, city_17, city_18, city_19, city_20, city_21);
		departureChoiceBox.getItems().addAll(list);
		arrivalChoiceBox.getItems().addAll(list);

	}

	public void goToSchedule(javafx.event.ActionEvent event) throws IOException {
		Parent schedulePanel = FXMLLoader.load(getClass().getResource("/views/SchedulePanel.fxml"));
		Scene scheduleScene = new Scene(schedulePanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scheduleScene);
		window.show();
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

	public void toggleButtonAction(javafx.event.ActionEvent event) throws IOException, ParseException {
		if (isSearchHidden) {
			buyTicket();
		} else {
			searchTickets(event);
		}
	}

	public void buyTicket() throws ParseException {
		if (!getMatchingTrips().isEmpty()) {
			Trip trip = availableTripsTable.getSelectionModel().getSelectedItem();

			if (trip == null) {
				informationLabel.setText("Please select a trip.");
				return;
			}

			TripService tripService = new TripService();
			int ticketsToPurchase = Integer.parseInt(quantityChoiceBox.getSelectionModel().getSelectedItem());

			tripService.updateTripTicketAvailability(trip, trip.getTripTicketAvailability() - ticketsToPurchase);

			LocationService locationService = new LocationService();

			if (!locationService.getByName(departureChoiceBox.getSelectionModel().getSelectedItem()).isPresent()) {
				informationLabel.setText("Unable to purchase ticket.");
				return;
			}

			if (!locationService.getByName(arrivalChoiceBox.getSelectionModel().getSelectedItem()).isPresent()) {
				informationLabel.setText("Unable to purchase ticket.");
				return;
			}

			Location departureLocation = locationService
					.getByName(departureChoiceBox.getSelectionModel().getSelectedItem()).get();
			Location arrivalLocation = locationService.getByName(arrivalChoiceBox.getSelectionModel().getSelectedItem())
					.get();

			TicketService ticketService = new TicketService();
			Ticket ticket = new Ticket(new Date(System.currentTimeMillis()), trip, departureLocation, arrivalLocation);
			ticketService.save(ticket);

			UserService userService = new UserService();
			userService.addTicket(DatabaseUtils.currentUser, ticket);

			// For every 5 purchased tickets, the user gains a rating of 0.2
			if (DatabaseUtils.currentUser.getTickets().size() % 5 == 0) {
				DatabaseUtils.currentUser.getUserProfile()
						.setUserProfileRating(DatabaseUtils.currentUser.getUserProfile().getUserProfileRating() + 0.2);
			}

			informationLabel.setText("You bought a ticket.");
		} else {
			informationLabel.setText("No available tickets for the specified trip.");
		}
	}

	public void searchTickets(javafx.event.ActionEvent event) throws ParseException, IOException {
		list.removeAll(list);
		list.addAll(this.getMatchingTrips());

		/* Filter all trips that match the search criteria */
		toggleView(event);
		availableTripsTable.setItems(list);
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
		searchBuyToggle.setText((isSearchHidden) ? "Buy" : "Search");

		/* Expand to show the table in full view, shrink for the search */
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setWidth((!isSearchHidden) ? 760 : 620);

		isSearchHidden = !isSearchHidden;
	}
}
