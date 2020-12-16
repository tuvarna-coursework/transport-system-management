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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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

	private static final Logger logger = LogManager.getLogger(CompanyAddController.class.getName());
	private UserService userService;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		userService = new UserService();
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");

		// loadRoutes();
		loadTime();
		loadRestrictionQuantity();
		// loadAttachmentLocations();
		loadLocation();

		logger.info("Loaded times, purchase restrictions, locations.");
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

		logger.info("Schedule view loaded.");
	}

	public void goToNotifications(javafx.event.ActionEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/CompanyNotificationsPanel.fxml"));
		AnchorPane root = (AnchorPane) userPanel.load();
		Scene adminScene = new Scene(root);
		stage.setScene(adminScene);
		stage.setTitle("Transport Company");
		stage.showAndWait();

		logger.info("Notifications view loaded.");
	}

	public void goToRequest(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/CompanyRequestPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		logger.info("Requests view loaded.");
	}

	public void backToLogIn(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		DatabaseUtils.currentUser = null;
		logger.info("User logged out.");
	}

	public void addTrip() throws IOException, ParseException {
		if (globalRoute == null) {
			informationLabel.setText("Add stations for the trip first!");
			return;
		}

		String result = userService.companyAddTripProcessing(globalRoute, ticketsAvailabilityTextField.getText(),
				seatsCapacityTextField.getText(), durationTextField.getText(), priceTextField.getText(),
				departureDatePicker.getEditor(), arrivalDatePicker.getEditor(),
				restrictionChoiceBox.getSelectionModel().getSelectedItem(),
				timeChoiceBox.getSelectionModel().getSelectedItem(),
				departureChoiceBox.getSelectionModel().getSelectedItem(),
				arrivalChoiceBox.getSelectionModel().getSelectedItem(), radioTypeTrip.getSelectedToggle(),
				radioBusType.getSelectedToggle());

		if (!result.equals("Success")) {
			informationLabel.setText(result);
			return;
		}
		
		informationLabel.setText("Trip successfully created.");
	}

	public void addStations(javafx.event.ActionEvent event) throws IOException {
		String constraintCheck = userService.companyAddAttachmentLocationsEndPointValidation(
				departureChoiceBox.getSelectionModel().getSelectedItem(),
				arrivalChoiceBox.getSelectionModel().getSelectedItem());

		if (!constraintCheck.equals("Success")) {
			informationLabel.setText(constraintCheck); // display error message
			return;
		}

		globalRoute = userService.createGlobalTrip(departureChoiceBox.getSelectionModel().getSelectedItem(),
				arrivalChoiceBox.getSelectionModel().getSelectedItem());

		userService.companyAddTripHandleAttachmentLocations(arrivalChoiceBox.getSelectionModel().getSelectedItem(),
				departureChoiceBox.getSelectionModel().getSelectedItem(), globalRoute);
	}
}
