package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Route;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.RouteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

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

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadLocation();
		loadTime();
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
				time_12, time_13, time_14, time_15, time_16, time_17, time_18, time_19, time_20, time_21, time_22, time_23, time_24, time_25);
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
		stationOneChoiceBox.getItems().addAll(list);
		stationTwoChoiceBox.getItems().addAll(list);
		stationThreeChoiceBox.getItems().addAll(list);
		stationFourChoiceBox.getItems().addAll(list);
		stationFiveChoiceBox.getItems().addAll(list);
		stationSixChoiceBox.getItems().addAll(list);
		stationSevenChoiceBox.getItems().addAll(list);
		stationEightChoiceBox.getItems().addAll(list);
		stationNineChoiceBox.getItems().addAll(list);
		stationTenChoiceBox.getItems().addAll(list);
		stationElevenChoiceBox.getItems().addAll(list);
		stationTwelveChoiceBox.getItems().addAll(list);
	}

	public void getRouteLocations(String arrivalLoc, String departureLoc, Route routeCreated) {
		arrival = arrivalLoc;
		departure = departureLoc;
		informationLabel.setText("Add stations between " + departure + " and " + arrival);
		route = routeCreated;

	}

	public void makeRoute(javafx.event.ActionEvent event) throws IOException {
		Stage stage = (Stage) applyButton.getScene().getWindow();
		if (stationOneChoiceBox.getValue() != null && stationOneChoiceBox.getValue().trim().length() != 0) {
			String st1 = stationOneChoiceBox.getValue();
			LocationService locationService = new LocationService();
			Location locationStationOne = locationService.getByName(st1).get();
			String timeOne=timeOneChoiceBox.getValue().trim().toString();


			if (locationStationOne.getLocationName().equals(departure)
					|| locationStationOne.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 1 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			routeService.addAttachmentLocation(route, locationStationOne, timeOne);
		}
		if (stationTwoChoiceBox.getValue() != null && stationTwoChoiceBox.getValue().trim().length() != 0) {
			Location locationStationTwo = new LocationService().getByName(stationTwoChoiceBox.getValue().toString())
					.get();
			String timeTwo=timeTwoChoiceBox.getValue().trim().toString();


			if (locationStationTwo.getLocationName().equals(departure)
					|| locationStationTwo.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 2 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			routeService.addAttachmentLocation(route, locationStationTwo, timeTwo);
		}
		if (stationThreeChoiceBox.getValue() != null && stationThreeChoiceBox.getValue().trim().length() != 0) {
			Location locationStationThree = new LocationService().getByName(stationThreeChoiceBox.getValue().toString())
					.get();
			String timeThree=timeThreeChoiceBox.getValue().trim().toString();

			if (locationStationThree.getLocationName().equals(departure)
					|| locationStationThree.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 3 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			routeService.addAttachmentLocation(route, locationStationThree, timeThree);
		}
		if (stationFourChoiceBox.getValue() != null && stationFourChoiceBox.getValue().trim().length() != 0) {
			Location locationStationFour = new LocationService().getByName(stationFourChoiceBox.getValue().toString())
					.get();
			String timeFour=timeFourChoiceBox.getValue().trim().toString();

			if (locationStationFour.getLocationName().equals(departure)
					|| locationStationFour.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 4 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			routeService.addAttachmentLocation(route, locationStationFour, timeFour);
		}
		if (stationFiveChoiceBox.getValue() != null && stationFiveChoiceBox.getValue().trim().length() != 0) {
			Location locationStationFive = new LocationService().getByName(stationFiveChoiceBox.getValue().toString())
					.get();
			String timeFive=timeFiveChoiceBox.getValue().trim().toString();

			if (locationStationFive.getLocationName().equals(departure)
					|| locationStationFive.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 5 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			routeService.addAttachmentLocation(route, locationStationFive, timeFive);
		}
		if (stationSixChoiceBox.getValue() != null && stationSixChoiceBox.getValue().trim().length() != 0) {
			Location locationStationSix = new LocationService().getByName(stationSixChoiceBox.getValue().toString())
					.get();
			String timeSix=timeSixChoiceBox.getValue().trim().toString();

			if (locationStationSix.getLocationName().equals(departure)
					|| locationStationSix.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 6 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			routeService.addAttachmentLocation(route, locationStationSix, timeSix);
		}
		if (stationSevenChoiceBox.getValue() != null && stationSevenChoiceBox.getValue().trim().length() != 0) {
			Location locationStationSeven = new LocationService().getByName(stationSevenChoiceBox.getValue().toString())
					.get();
			String timeSeven=timeSevenChoiceBox.getValue().trim().toString();


			if (locationStationSeven.getLocationName().equals(departure)
					|| locationStationSeven.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 7 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			routeService.addAttachmentLocation(route, locationStationSeven, timeSeven);
		}
		if (stationEightChoiceBox.getValue() != null && stationEightChoiceBox.getValue().trim().length() != 0) {
			Location locationStationEight = new LocationService().getByName(stationEightChoiceBox.getValue().toString())
					.get();
			String timeEight=timeEightChoiceBox.getValue().trim().toString();

			if (locationStationEight.getLocationName().equals(departure)
					|| locationStationEight.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 8 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			routeService.addAttachmentLocation(route, locationStationEight, timeEight);
		}
		if (stationNineChoiceBox.getValue() != null && stationNineChoiceBox.getValue().trim().length() != 0) {
			Location locationStationNine = new LocationService().getByName(stationNineChoiceBox.getValue().toString())
					.get();
			String timeNine=timeNineChoiceBox.getValue().trim().toString();

			if (locationStationNine.getLocationName().equals(departure)
					|| locationStationNine.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 9 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			routeService.addAttachmentLocation(route, locationStationNine, timeNine);
		}
		if (stationTenChoiceBox.getValue() != null && stationTenChoiceBox.getValue().trim().length() != 0) {
			Location locationStationTen = new LocationService().getByName(stationTenChoiceBox.getValue().toString())
					.get();
			String timeTen=timeTenChoiceBox.getValue().trim().toString();

			if (locationStationTen.getLocationName().equals(departure)
					|| locationStationTen.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 10 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			routeService.addAttachmentLocation(route, locationStationTen, timeTen);
		}
		if (stationElevenChoiceBox.getValue() != null && stationElevenChoiceBox.getValue().trim().length() != 0) {
			Location locationStationEleven = new LocationService()
					.getByName(stationElevenChoiceBox.getValue().toString()).get();
			String timeEleven=timeElevenChoiceBox.getValue().trim().toString();

			if (locationStationEleven.getLocationName().equals(departure)
					|| locationStationEleven.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 11 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			routeService.addAttachmentLocation(route, locationStationEleven, timeEleven);
		}
		if (stationTwelveChoiceBox.getValue() != null && stationTwelveChoiceBox.getValue().trim().length() != 0) {
			Location locationStationTwelve = new LocationService()
					.getByName(stationTwelveChoiceBox.getValue().toString()).get();
			String timeTwelve=timeTwelveChoiceBox.getValue().trim().toString();

			if (locationStationTwelve.getLocationName().equals(departure)
					|| locationStationTwelve.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 12 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			routeService.addAttachmentLocation(route, locationStationTwelve, timeTwelve);
		}
		stage.close();

	}

}
