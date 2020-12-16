package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Route;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.RouteService;
import com.tuvarna.transportsystem.services.UserService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CompanyLoadStationsController implements Initializable {

	@FXML
	private ChoiceBox<String> stationOneChoiceBox;
	@FXML
	private ChoiceBox<String> stationTwoChoiceBox;
	@FXML
	private ChoiceBox<String> stationThreeChoiceBox;
	@FXML
	private ChoiceBox<String> stationFourChoiceBox;
	@FXML
	private ChoiceBox<String> stationFiveChoiceBox;
	@FXML
	private ChoiceBox<String> stationSixChoiceBox;
	@FXML
	private ChoiceBox<String> stationSevenChoiceBox;
	@FXML
	private ChoiceBox<String> stationEightChoiceBox;
	@FXML
	private ChoiceBox<String> stationNineChoiceBox;
	@FXML
	private ChoiceBox<String> stationTenChoiceBox;
	@FXML
	private ChoiceBox<String> stationElevenChoiceBox;
	@FXML
	private ChoiceBox<String> stationTwelveChoiceBox;
	@FXML
	private Button applyButton;
	@FXML
	private Label informationLabel;

	@FXML
	private ChoiceBox<String> timeOneChoiceBox;
	@FXML
	private ChoiceBox<String> timeTwoChoiceBox;
	@FXML
	private ChoiceBox<String> timeThreeChoiceBox;
	@FXML
	private ChoiceBox<String> timeFourChoiceBox;
	@FXML
	private ChoiceBox<String> timeFiveChoiceBox;
	@FXML
	private ChoiceBox<String> timeSixChoiceBox;
	@FXML
	private ChoiceBox<String> timeSevenChoiceBox;
	@FXML
	private ChoiceBox<String> timeEightChoiceBox;
	@FXML
	private ChoiceBox<String> timeNineChoiceBox;
	@FXML
	private ChoiceBox<String> timeTenChoiceBox;
	@FXML
	private ChoiceBox<String> timeElevenChoiceBox;
	@FXML
	private ChoiceBox<String> timeTwelveChoiceBox;

	String arrival = "";
	String departure = "";
	Route route;

	private static final Logger logger = LogManager.getLogger(CompanyLoadStationsController.class.getName());
	private UserService userService;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		userService = new UserService();
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");

		loadTime();
		/*
		Loading all choice boxes with locations from database.
		 */
		stationOneChoiceBox.setItems(getLocation());
		stationTwoChoiceBox.setItems(getLocation());
		stationThreeChoiceBox.setItems(getLocation());
		stationFourChoiceBox.setItems(getLocation());
		stationFiveChoiceBox.setItems(getLocation());
		stationSixChoiceBox.setItems(getLocation());
		stationSevenChoiceBox.setItems(getLocation());
		stationEightChoiceBox.setItems(getLocation());
		stationNineChoiceBox.setItems(getLocation());
		stationTenChoiceBox.setItems(getLocation());
		stationElevenChoiceBox.setItems(getLocation());
		stationTwelveChoiceBox.setItems(getLocation());
	}

	ObservableList list = FXCollections.observableArrayList();

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
		timeOneChoiceBox.getItems().addAll(list);
		timeTwoChoiceBox.getItems().addAll(list);
		timeThreeChoiceBox.getItems().addAll(list);
		timeFourChoiceBox.getItems().addAll(list);
		timeFiveChoiceBox.getItems().addAll(list);
		timeSixChoiceBox.getItems().addAll(list);
		timeSevenChoiceBox.getItems().addAll(list);
		timeEightChoiceBox.getItems().addAll(list);
		timeNineChoiceBox.getItems().addAll(list);
		timeTenChoiceBox.getItems().addAll(list);
		timeElevenChoiceBox.getItems().addAll(list);
		timeTwelveChoiceBox.getItems().addAll(list);

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
	public void getRouteLocations(String arrivalLoc, String departureLoc, Route routeCreated) {
		arrival = arrivalLoc;
		departure = departureLoc;
		informationLabel.setText("Add stations between " + departure + " and " + arrival);
		route = routeCreated;
	}

	public void makeRoute(javafx.event.ActionEvent event) throws IOException {
		Stage stage = (Stage) applyButton.getScene().getWindow();
		
		List<String> locationsSelected = new ArrayList<>();
		locationsSelected.add(stationOneChoiceBox.getSelectionModel().getSelectedItem());
		locationsSelected.add(stationTwoChoiceBox.getSelectionModel().getSelectedItem());
		locationsSelected.add(stationThreeChoiceBox.getSelectionModel().getSelectedItem());
		locationsSelected.add(stationFourChoiceBox.getSelectionModel().getSelectedItem());
		locationsSelected.add(stationFiveChoiceBox.getSelectionModel().getSelectedItem());
		locationsSelected.add(stationSixChoiceBox.getSelectionModel().getSelectedItem());
		locationsSelected.add(stationSevenChoiceBox.getSelectionModel().getSelectedItem());
		locationsSelected.add(stationEightChoiceBox.getSelectionModel().getSelectedItem());
		locationsSelected.add(stationNineChoiceBox.getSelectionModel().getSelectedItem());
		locationsSelected.add(stationTenChoiceBox.getSelectionModel().getSelectedItem());
		locationsSelected.add(stationElevenChoiceBox.getSelectionModel().getSelectedItem());
		locationsSelected.add(stationTwelveChoiceBox.getSelectionModel().getSelectedItem());
		
		List<String> hoursSelected = new ArrayList<>();
		hoursSelected.add(timeOneChoiceBox.getSelectionModel().getSelectedItem());
		hoursSelected.add(timeTwoChoiceBox.getSelectionModel().getSelectedItem());
		hoursSelected.add(timeThreeChoiceBox.getSelectionModel().getSelectedItem());
		hoursSelected.add(timeFourChoiceBox.getSelectionModel().getSelectedItem());
		hoursSelected.add(timeFiveChoiceBox.getSelectionModel().getSelectedItem());
		hoursSelected.add(timeSixChoiceBox.getSelectionModel().getSelectedItem());
		hoursSelected.add(timeSevenChoiceBox.getSelectionModel().getSelectedItem());
		hoursSelected.add(timeEightChoiceBox.getSelectionModel().getSelectedItem());
		hoursSelected.add(timeNineChoiceBox.getSelectionModel().getSelectedItem());
		hoursSelected.add(timeTenChoiceBox.getSelectionModel().getSelectedItem());
		hoursSelected.add(timeElevenChoiceBox.getSelectionModel().getSelectedItem());
		hoursSelected.add(timeTwelveChoiceBox.getSelectionModel().getSelectedItem());
		
		String result = userService.companyAddAttachmentLocationsProcessing(locationsSelected, hoursSelected, route);
		
		if (!result.equals("Success")) {
			informationLabel.setText(result);
			return;
		}

		stage.close();

	}
}
