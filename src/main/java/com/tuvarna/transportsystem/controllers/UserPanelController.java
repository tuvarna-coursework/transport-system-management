package com.tuvarna.transportsystem.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.services.TicketService;
import com.tuvarna.transportsystem.services.TripService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class UserPanelController implements Initializable {

	ObservableList list = FXCollections.observableArrayList();
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

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadQuantity();
		loadTime();
		loadDepartureArrivalLocation();

	}

	private boolean processTicketPurchase() throws ParseException {
		String departureStation = departureChoiceBox.getValue().trim();
		String arrivalStation = arrivalChoiceBox.getValue().trim();

		TripService tripService = new TripService();
		/* Get trips by both locations */
		List<Trip> trips = tripService.getByLocations(departureStation, arrivalStation);

		if (trips == null) {
			return false;
		}

		/*
		 * Uses local machine's format and since it contains HH:MM:SS as well, it is
		 * splitted and only the date is taken.
		 */
		String dateFormatPattern = new SimpleDateFormat().toLocalizedPattern().split(" ")[0];
		DateFormat formatDepartureDate = new SimpleDateFormat(dateFormatPattern);

		Date dateDeparture = formatDepartureDate.parse(tripDatePicker.getEditor().getText());


		List<Trip> filteredTrips = new ArrayList<>();

		/*
		 * Currently the logic assumes there is one trip only and not a selection of
		 * trips the customer can choose from. Will await front end changes.
		 */

		/* Iterate through the trips and validate all the fields */
		for (Trip trip : trips) {
		    boolean matchesDates = trip.getTripArrivalDate().compareTo(dateDeparture) == 1 ? true : false;
			boolean checkQuantity = Integer.parseInt(quantityChoiceBox.getValue()) <= trip.getMaxTicketsPerUser();
			boolean matchesTime = trip.getTripDepartureHour()
					.contentEquals(timeChoiceBox.getSelectionModel().getSelectedItem().trim());

			/* If all the criteria matches check if there are enough available tickets */
			if (matchesDates && checkQuantity && matchesTime) {
				int ticketsToPurchase = Integer.parseInt(quantityChoiceBox.getValue());

				/* If there are enough tickets substitute the bought tickets */
				if (trip.getTripTicketAvailability() >= ticketsToPurchase) {
					tripService.updateTripTicketAvailability(trip,
							(ticketsToPurchase < 0) ? 0 : trip.getTripTicketAvailability() - ticketsToPurchase);

					TicketService ticketService = new TicketService();
					Ticket ticket = new Ticket(new Date(System.currentTimeMillis()), trip);
					ticketService.save(ticket);

					UserService userService = new UserService();
					userService.addTicket(DatabaseUtils.currentUser, ticket);

					/* For every 5 purchased tickets, the user gains a rating of 0.2 */
					if (DatabaseUtils.currentUser.getTickets().size() % 5 == 0) {
						DatabaseUtils.currentUser.getUserProfile().setUserProfileRating(
								DatabaseUtils.currentUser.getUserProfile().getUserProfileRating() + 0.2);
					}

					return true;
				}
			}
		}
		return false;
	}

	public void loadQuantity() {
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
		String city_04 = "Veliko Tarnovo";
		String city_05 = "Razgrad";
		String city_06 = "Gabrovo";
		String city_07 = "Plovdiv";
		String city_08 = "Burgas";
		String city_09 = "Stara Zagora";
		String city_10 = "Blagoevgrad";
		String city_11 = "Sliven";

		list.addAll(city_01, city_02, city_03, city_04, city_05, city_06, city_07, city_08, city_09, city_10, city_11);
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

	public void goToTicket(javafx.event.ActionEvent event) throws IOException {
		Parent ticketPanel = FXMLLoader.load(getClass().getResource("/views/UserPanel.fxml"));
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

	public void buyTicket(javafx.event.ActionEvent event) throws IOException, ParseException {
		if (processTicketPurchase()) {
			informationLabel.setText("You bought a ticket.");
		} else {
			informationLabel.setText("No available tickets for the specified trip.");
		}
	}
}
