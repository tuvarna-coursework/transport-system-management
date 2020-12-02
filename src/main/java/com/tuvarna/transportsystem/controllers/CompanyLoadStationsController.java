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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
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
	
	
	@FXML
	private Button applyButton;
	@FXML
	private Label informationLabel;

	String arrival = "";
	String departure = "";
	Route route;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadLocation();
	}

	ObservableList list = FXCollections.observableArrayList();

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
		RouteService routeService = new RouteService();
		
		Stage stage = (Stage) applyButton.getScene().getWindow();
		if (stationOneChoiceBox.getValue() != null && stationOneChoiceBox.getValue().trim().length() != 0) {
			String st1 = stationOneChoiceBox.getValue();
			LocationService locationService = new LocationService();
			Location locationStationOne = locationService.getByName(st1).get();
			
			if (timeOneChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select time of arrival for all locations.");
				return;
			}

			if (locationStationOne.getLocationName().equals(departure)
					|| locationStationOne.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 1 matches with arrival or departure location");
				return;
			}
			
			routeService.addAttachmentLocation(route, locationStationOne, timeOneChoiceBox.getSelectionModel().getSelectedItem());
		}
		if (stationTwoChoiceBox.getValue() != null && stationTwoChoiceBox.getValue().trim().length() != 0) {
			Location locationStationTwo = new LocationService().getByName(stationTwoChoiceBox.getValue().toString())
					.get();

			if (locationStationTwo.getLocationName().equals(departure)
					|| locationStationTwo.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 2 matches with arrival or departure location");
				return;
			}
			
			if (timeTwoChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select time of arrival for all locations.");
				return;
			}
	
			routeService.addAttachmentLocation(route, locationStationTwo, timeTwoChoiceBox.getSelectionModel().getSelectedItem());
		}
		if (stationThreeChoiceBox.getValue() != null && stationThreeChoiceBox.getValue().trim().length() != 0) {
			Location locationStationThree = new LocationService().getByName(stationThreeChoiceBox.getValue().toString())
					.get();

			if (locationStationThree.getLocationName().equals(departure)
					|| locationStationThree.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 3 matches with arrival or departure location");
				return;
			}
			
			if (timeThreeChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select time of arrival for all locations.");
				return;
			}
		
			routeService.addAttachmentLocation(route, locationStationThree, timeThreeChoiceBox.getSelectionModel().getSelectedItem());
		}
		if (stationFourChoiceBox.getValue() != null && stationFourChoiceBox.getValue().trim().length() != 0) {
			Location locationStationFour = new LocationService().getByName(stationFourChoiceBox.getValue().toString())
					.get();

			if (locationStationFour.getLocationName().equals(departure)
					|| locationStationFour.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 4 matches with arrival or departure location");
				return;
			}
			
			if (timeFourChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select time of arrival for all locations.");
				return;
			}
			
			routeService.addAttachmentLocation(route, locationStationFour, timeFourChoiceBox.getSelectionModel().getSelectedItem());
		}
		if (stationFiveChoiceBox.getValue() != null && stationFiveChoiceBox.getValue().trim().length() != 0) {
			Location locationStationFive = new LocationService().getByName(stationFiveChoiceBox.getValue().toString())
					.get();

			if (locationStationFive.getLocationName().equals(departure)
					|| locationStationFive.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 5 matches with arrival or departure location");
				return;
			}
			
			if (timeFiveChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select time of arrival for all locations.");
				return;
			}
			
			routeService.addAttachmentLocation(route, locationStationFive, timeFiveChoiceBox.getSelectionModel().getSelectedItem());
		}
		if (stationSixChoiceBox.getValue() != null && stationSixChoiceBox.getValue().trim().length() != 0) {
			Location locationStationSix = new LocationService().getByName(stationSixChoiceBox.getValue().toString())
					.get();

			if (locationStationSix.getLocationName().equals(departure)
					|| locationStationSix.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 6 matches with arrival or departure location");
				return;
			}
			
			if (timeSixChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select time of arrival for all locations.");
				return;
			}
			
			routeService.addAttachmentLocation(route, locationStationSix, timeSixChoiceBox.getSelectionModel().getSelectedItem());
		}
		if (stationSevenChoiceBox.getValue() != null && stationSevenChoiceBox.getValue().trim().length() != 0) {
			Location locationStationSeven = new LocationService().getByName(stationSevenChoiceBox.getValue().toString())
					.get();

			if (locationStationSeven.getLocationName().equals(departure)
					|| locationStationSeven.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 7 matches with arrival or departure location");
				return;
			}
			
			if (timeSevenChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select time of arrival for all locations.");
				return;
			}
			
			routeService.addAttachmentLocation(route, locationStationSeven, timeSevenChoiceBox.getSelectionModel().getSelectedItem());
		}
		if (stationEightChoiceBox.getValue() != null && stationEightChoiceBox.getValue().trim().length() != 0) {
			Location locationStationEight = new LocationService().getByName(stationEightChoiceBox.getValue().toString())
					.get();

			if (locationStationEight.getLocationName().equals(departure)
					|| locationStationEight.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 8 matches with arrival or departure location");
				return;
			}
			
			if (timeEightChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select time of arrival for all locations.");
				return;
			}
			
			routeService.addAttachmentLocation(route, locationStationEight, timeEightChoiceBox.getSelectionModel().getSelectedItem());
		}
		if (stationNineChoiceBox.getValue() != null && stationNineChoiceBox.getValue().trim().length() != 0) {
			Location locationStationNine = new LocationService().getByName(stationNineChoiceBox.getValue().toString())
					.get();

			if (locationStationNine.getLocationName().equals(departure)
					|| locationStationNine.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 9 matches with arrival or departure location");
				return;
			}
			
			if (timeNineChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select time of arrival for all locations.");
				return;
			}
		
			routeService.addAttachmentLocation(route, locationStationNine, timeNineChoiceBox.getSelectionModel().getSelectedItem());
		}
		if (stationTenChoiceBox.getValue() != null && stationTenChoiceBox.getValue().trim().length() != 0) {
			Location locationStationTen = new LocationService().getByName(stationTenChoiceBox.getValue().toString())
					.get();

			if (locationStationTen.getLocationName().equals(departure)
					|| locationStationTen.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 10 matches with arrival or departure location");
				return;
			}
			
			if (timeTenChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select time of arrival for all locations.");
				return;
			}
			
			routeService.addAttachmentLocation(route, locationStationTen, timeTenChoiceBox.getSelectionModel().getSelectedItem());
		}
		if (stationElevenChoiceBox.getValue() != null && stationElevenChoiceBox.getValue().trim().length() != 0) {
			Location locationStationEleven = new LocationService()
					.getByName(stationElevenChoiceBox.getValue().toString()).get();

			if (locationStationEleven.getLocationName().equals(departure)
					|| locationStationEleven.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 11 matches with arrival or departure location");
				return;
			}
			
			if (timeElevenChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select time of arrival for all locations.");
				return;
			}
		
			routeService.addAttachmentLocation(route, locationStationEleven, timeElevenChoiceBox.getSelectionModel().getSelectedItem());
		}
		if (stationTwelveChoiceBox.getValue() != null && stationTwelveChoiceBox.getValue().trim().length() != 0) {
			Location locationStationTwelve = new LocationService()
					.getByName(stationTwelveChoiceBox.getValue().toString()).get();

			if (locationStationTwelve.getLocationName().equals(departure)
					|| locationStationTwelve.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 12 matches with arrival or departure location");
				return;
			}
			
			if (timeTwelveChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select time of arrival for all locations.");
				return;
			}
	
			routeService.addAttachmentLocation(route, locationStationTwelve, timeTwelveChoiceBox.getSelectionModel().getSelectedItem());
		}
		stage.close();

	}

}
