package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.*;
import com.tuvarna.transportsystem.services.*;
import com.tuvarna.transportsystem.utils.DatabaseUtils;
import com.tuvarna.transportsystem.utils.NotificationUtils;

import javafx.collections.FXCollections;

import java.awt.*;
import java.lang.String;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.scene.control.ButtonBar.ButtonData.*;

public class CompanyAddController implements Initializable {

	ObservableList list = FXCollections.observableArrayList();
	@FXML
	private ChoiceBox<String> routeChoiceBox;
	@FXML
	private ChoiceBox<String> timeChoiceBox;

	@FXML
	private ComboBox<String> attachmentComboBox;
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
	@FXML
	private TextField priceTextField;
	@FXML
	private TextField ticketsAvailabilityTextField;
	@FXML
	private ChoiceBox<String> departureChoiceBox;
	@FXML
	private ChoiceBox<String> arrivalChoiceBox;

	Route globalRoute;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// loadRoutes();
		loadTime();
		loadRestrictionQuantity();
		// loadAttachmentLocations();
		loadLocation();
	}

	public void loadLocation() {
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

	private void loadTime() {
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

	private void loadRestrictionQuantity() {
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
	public void goToNotifications(javafx.event.ActionEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/CompanyNotificationsPanel.fxml"));
		AnchorPane root = (AnchorPane) userPanel.load();
		Scene adminScene = new Scene(root);
		stage.setScene(adminScene);
		stage.setTitle("Transport Company");
		stage.showAndWait();
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
		if (!pattern.matcher(ticketsAvailabilityTextField.getText().trim()).matches()) {
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
		// DateFormat formatDepartureDate = new SimpleDateFormat("MM/dd/yyyy");

		String dateFormatPattern = new SimpleDateFormat().toLocalizedPattern().split(" ")[0];
		DateFormat formatDepartureDate = new SimpleDateFormat(dateFormatPattern);
		Date dateDeparture = formatDepartureDate.parse(departure);

		// arrival date
		TextField arrivalDate = arrivalDatePicker.getEditor();
		String arrival = arrivalDate.getText();
		// DateFormat formatArrivalDate = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat formatArrivalDate = new SimpleDateFormat(dateFormatPattern);
		Date dateArrival = formatArrivalDate.parse(arrival);
		// SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH).parse(arrival);

		/* Date validation */
		if (dateDeparture.after(dateArrival) || dateDeparture.before(new Date(System.currentTimeMillis()))
				|| dateArrival.before(new Date(System.currentTimeMillis()))) {
			informationLabel.setText("Invalid interval!");
			return;
		}

		// tickets restriction
		int ticketsPerPerson = Integer.parseInt(restrictionChoiceBox.getValue().toString());

		// trip capacity
		String seatsCapacity = seatsCapacityTextField.getText();
		int chechedSeatsCapacity = Integer.parseInt(seatsCapacity);

		// validation for departure and arrival. !=null
		if (departureChoiceBox.getValue() == null) {
			informationLabel.setText("Please select departure station!");
			return;
		}
		if (arrivalChoiceBox.getValue() == null) {
			informationLabel.setText("Please select arrival station!");
			return;
		}

		// trip type EXPRESS/NORMAL
		RadioButton selectedTripType = (RadioButton) radioTypeTrip.getSelectedToggle();
		if (radioTypeTrip.getSelectedToggle() == null) {
			informationLabel.setText("Please select trip type (Express or Normal).");
			return;
		}
		String tripType = selectedTripType.getText().trim();
		TripType tripTypeClass = new TripTypeService().getByName(tripType).get();

		// departure time
		String departureTime = timeChoiceBox.getValue();

		// trip BUS type
		RadioButton selectedBusType = (RadioButton) radioBusType.getSelectedToggle();
		if (radioBusType.getSelectedToggle() == null) {
			informationLabel.setText("Please select bus type (Regular or Big bus)!");
			return;
		}
		String busType = selectedBusType.getText();
		TransportType transportTypeClass = new TransportTypeService().getByName(busType).get();

		// Duration
		int duration = Integer.parseInt(durationTextField.getText().trim().toString());

		// price
		String getPrice = priceTextField.getText();
		double price = Double.parseDouble(getPrice);

		// tickets availability
		int ticketsAvailability = Integer.parseInt(ticketsAvailabilityTextField.getText().trim().toString());

		/*
		 * Validation to add stations first, before make the trip!
		 */
		if (globalRoute == null) {
			informationLabel.setText("Add stations for the trip first!");
			return;
		}

		Trip newTrip = new Trip(tripTypeClass, globalRoute, dateDeparture, dateArrival, chechedSeatsCapacity,
				transportTypeClass, ticketsPerPerson, ticketsAvailability, price, duration, departureTime);
		TripService tripService = new TripService();
		tripService.save(newTrip);

		/*
		 * In the UserTrip table a new entry will be added with the logged in user
		 * (owner in this case) and the newly created trip
		 */
		new UserService().addTrip(DatabaseUtils.currentUser, newTrip);
		
		/* Distributor gets a notification */
		NotificationUtils.generateNewTripNotification(newTrip);

		informationLabel.setText("You added new trip!");

	}

	public void addStations(javafx.event.ActionEvent event) throws IOException {
		if (departureChoiceBox.getValue() == null) {
			informationLabel.setText("Please select departure station!");
			return;
		}
		if (arrivalChoiceBox.getValue() == null) {
			informationLabel.setText("Please select arrival station!");
			return;
		}

		// creating the route
		String departureStation = departureChoiceBox.getValue().toString();
		String arrivalStation = arrivalChoiceBox.getValue().toString();
		LocationService locationService = new LocationService();
		Location locationDeparture = locationService.getByName(departureStation).get();
		Location locationArrival = locationService.getByName(arrivalStation).get();
		RouteService routeService = new RouteService();
		Route route = new Route(locationDeparture, locationArrival);

		routeService.save(route);
		globalRoute = route;

		try {
			Stage stage = new Stage();
			FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/CompanyLoadStations.fxml"));
			DialogPane root = (DialogPane) userPanel.load();

			// sending the route and stations to other controller
			CompanyLoadStationsController controller = (CompanyLoadStationsController) userPanel.getController();
			String ar = arrivalChoiceBox.getValue().toString();
			String dp = departureChoiceBox.getValue().toString();
			controller.getRouteLocations(ar, dp, route);

			Scene adminScene = new Scene(root);
			stage.setScene(adminScene);
			stage.setTitle("Transport Company");
			stage.showAndWait();

		} catch (Exception e) {
			System.out.println("Problem");

		}
	}
}
