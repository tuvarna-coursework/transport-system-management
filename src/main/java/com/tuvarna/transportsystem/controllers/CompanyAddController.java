package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.*;
import com.tuvarna.transportsystem.services.*;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompanyAddController implements Initializable {

	ObservableList list = FXCollections.observableArrayList();
	@FXML
	private ChoiceBox<String> departureChoiceBox;
	@FXML
	private ChoiceBox<String> arrivalChoiceBox;
	@FXML
	private ChoiceBox<String> timeChoiceBox;
	@FXML
	private ToggleGroup radioBusType;
	@FXML
	private RadioButton radioBigBus;
	@FXML
	private RadioButton radioVan;
	@FXML
	private Label informationLabel;
	@FXML
	private DatePicker departureDatePicker;
	@FXML
	private TextField seatsCapacityTextField;
	@FXML
	private DatePicker arrivalDatePicker;
	@FXML
	private ChoiceBox restrictionChoiceBox;
	@FXML
	private TextField ticketsQuantityTextField;
	@FXML
	private ToggleGroup radioTypeTrip;
	@FXML
	private RadioButton radioTypeExpress;
	@FXML
	private RadioButton radioTypeNormal;
	@FXML
	private TextField durationTextField;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadDeparture();
		loadArrival();
		loadTime();
		loadRestrictionQuantity();

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

	public void loadArrival() {
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
		arrivalChoiceBox.getItems().addAll(list);

	}

	public void loadDeparture() {
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

	}

	public void loadRestrictionQuantity() {
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
		restrictionChoiceBox.getItems().addAll(list);

	}

	public void goToScheduleCompany(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/CompanySchedulePanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}

	public void goToRequest(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/CompanyRequestPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

	}

	public void backToLogIn(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

	}

	public void addTrip() throws IOException, ParseException {
		Pattern pattern = Pattern.compile("^\\d+$");

		/*
		 * Validate the text fields. In this case the pattern should be only a full
		 * number between 0-int.maxvalue
		 */
		if (!pattern.matcher(ticketsQuantityTextField.getText().trim()).matches()) {
			informationLabel.setText("Invalid quantity!");
			// display error messages
			return;
		}

		if (!pattern.matcher(seatsCapacityTextField.getText().trim()).matches()) {
			informationLabel.setText("Invalid seats capacity!");
			// display error messages
			return;
		}

		if (!pattern.matcher(durationTextField.getText().trim()).matches()) {
			informationLabel.setText("Invalid duration!");
			// display error messages
			return;
		}

		// departure date
		TextField departureDate = departureDatePicker.getEditor();
		String departure = departureDate.getText();
		DateFormat formatDepartureDate = new SimpleDateFormat("MM.dd.yyyy");
		Date dateDeparture = formatDepartureDate.parse(departure);

		// arrival date
		TextField arrivalDate = arrivalDatePicker.getEditor();
		String arrival = arrivalDate.getText();
		DateFormat formatArrivalDate = new SimpleDateFormat("MM.dd.yyyy");
		Date dateArrival = formatArrivalDate.parse(arrival);
		// SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH).parse(arrival);

		/* Date validation */
		if (dateDeparture.after(dateArrival) || dateDeparture.before(new Date(System.currentTimeMillis()))
				|| dateArrival.before(new Date(System.currentTimeMillis()))) {
			informationLabel.setText("Invalid interval!");

			// display error message here
			return;
		}

		// tickets restriction
		int ticketsPerPerson = Integer.parseInt(restrictionChoiceBox.getValue().toString());
		// int ticketRestriction=Integer.parseInt(ticketPerPerson);

		// trip capacity
		String seatsCapacity = seatsCapacityTextField.getText();
		int chechedSeatsCapacity = Integer.parseInt(seatsCapacity);

		/* Validate locations; they cannot be the same */
		if (departureChoiceBox.getValue() == arrivalChoiceBox.getValue()) {
			informationLabel.setText("Invalid locations!");
			// display error message
			return;
		}

		// departure location
		String departureLocation = departureChoiceBox.getValue();
		Location departureLocationObj = (Location) new LocationService().getByName(departureLocation);

		// arrival location
		String arrivalLocation = arrivalChoiceBox.getValue();
		Location arrivalLocationObj = (Location) new LocationService().getByName(arrivalLocation);

		// tickets quantity for trip
		String ticketsQuantity = ticketsQuantityTextField.getText().trim();
		int checkedTicketsQuantity = Integer.parseInt(ticketsQuantity);

		// trip type radio buttons
		RadioButton selectedTripType = (RadioButton) radioTypeTrip.getSelectedToggle();
		String tripType = selectedTripType.getText().trim();
		TripType tripTypeClass = new TripTypeService().getByName(tripType);

		// departure time
		String departureTime = timeChoiceBox.getValue();

		// trip BUS type
		RadioButton selectedBusType = (RadioButton) radioBusType.getSelectedToggle();
		String busType = selectedBusType.getText();
		TransportType transportTypeClass = new TransportTypeService().getByName(busType);

		// Duration
		int duration = Integer.parseInt(durationTextField.getText().trim().toString());

		Trip newTrip = new Trip(tripTypeClass, departureLocationObj, arrivalLocationObj, dateDeparture, dateArrival,
				chechedSeatsCapacity, transportTypeClass, ticketsPerPerson, checkedTicketsQuantity, duration, departureTime);
		TripService tripService = new TripService();
		tripService.save(newTrip);

		informationLabel.setText("You added new trip!");

		/*
		 * Parent userPanel = FXMLLoader.load(getClass().getResource("/views/.fxml"));
		 * Scene adminScene = new Scene(userPanel);
		 * 
		 * Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		 * window.setScene(adminScene); window.show();
		 */
	}
}
