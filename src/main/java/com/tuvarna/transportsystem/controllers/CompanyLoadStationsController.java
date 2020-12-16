package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Route;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.RouteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
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

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
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

		int previousHour = 0;

		if (stationOneChoiceBox.getValue() != null && stationOneChoiceBox.getValue().trim().length() != 0) {
			String st1 = stationOneChoiceBox.getValue();
			LocationService locationService = new LocationService();
			Location locationStationOne = locationService.getByName(st1).get();
			String timeOne = timeOneChoiceBox.getValue().trim().toString();

			previousHour = Integer.parseInt(timeOne.split(":")[0]);

			if (locationStationOne.getLocationName().equals(departure)
					|| locationStationOne.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 1 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			/*
			 * If a error happened for lets say 2nd station, on the next click we have a
			 * check so that the previous station doesn't get pushed again
			 */
			if (!routeService.getAttachmentLocationsInRouteById(route.getRouteId()).contains(locationStationOne)) {
				routeService.addAttachmentLocation(route, locationStationOne, timeOne);
				logger.info("Added attachment location to this route. (Insert into RouteAttachment");
			}
		}
		if (stationTwoChoiceBox.getValue() != null && stationTwoChoiceBox.getValue().trim().length() != 0) {
			Location locationStationTwo = new LocationService().getByName(stationTwoChoiceBox.getValue().toString())
					.get();
			String timeTwo = timeTwoChoiceBox.getValue().trim().toString();

			int currentHour = Integer.parseInt(timeTwo.split(":")[0]);

			if (currentHour <= previousHour) {
				informationLabel.setText("Following arrival time must be larger than the previous one.");
				return;
			}

			previousHour = currentHour;

			if (locationStationTwo.getLocationName().equals(departure)
					|| locationStationTwo.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 2 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();

			/*
			 * If a error happened for lets say 2nd station, on the next click we have a
			 * check so that the previous station doesn't get pushed again
			 */
			if (!routeService.getAttachmentLocationsInRouteById(route.getRouteId()).contains(locationStationTwo)) {
				routeService.addAttachmentLocation(route, locationStationTwo, timeTwo);
				logger.info("Added attachment location to this route. (Insert into RouteAttachment");
			}
		}
		if (stationThreeChoiceBox.getValue() != null && stationThreeChoiceBox.getValue().trim().length() != 0) {
			Location locationStationThree = new LocationService().getByName(stationThreeChoiceBox.getValue().toString())
					.get();
			String timeThree = timeThreeChoiceBox.getValue().trim().toString();

			int currentHour = Integer.parseInt(timeThree.split(":")[0]);

			if (currentHour <= previousHour) {
				informationLabel.setText("Following arrival time must be larger than the previous one.");
				return;
			}

			previousHour = currentHour;

			if (locationStationThree.getLocationName().equals(departure)
					|| locationStationThree.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 3 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();

			if (!routeService.getAttachmentLocationsInRouteById(route.getRouteId()).contains(locationStationThree)) {
				routeService.addAttachmentLocation(route, locationStationThree, timeThree);
				logger.info("Added attachment location to this route. (Insert into RouteAttachment");
			}
		}
		if (stationFourChoiceBox.getValue() != null && stationFourChoiceBox.getValue().trim().length() != 0) {
			Location locationStationFour = new LocationService().getByName(stationFourChoiceBox.getValue().toString())
					.get();
			String timeFour = timeFourChoiceBox.getValue().trim().toString();

			int currentHour = Integer.parseInt(timeFour.split(":")[0]);

			if (currentHour <= previousHour) {
				informationLabel.setText("Following arrival time must be larger than the previous one.");
				return;
			}

			previousHour = currentHour;

			if (locationStationFour.getLocationName().equals(departure)
					|| locationStationFour.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 4 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			if (!routeService.getAttachmentLocationsInRouteById(route.getRouteId()).contains(locationStationFour)) {
				routeService.addAttachmentLocation(route, locationStationFour, timeFour);
				logger.info("Added attachment location to this route. (Insert into RouteAttachment");
			}
		}
		if (stationFiveChoiceBox.getValue() != null && stationFiveChoiceBox.getValue().trim().length() != 0) {
			Location locationStationFive = new LocationService().getByName(stationFiveChoiceBox.getValue().toString())
					.get();
			String timeFive = timeFiveChoiceBox.getValue().trim().toString();

			int currentHour = Integer.parseInt(timeFive.split(":")[0]);

			if (currentHour <= previousHour) {
				informationLabel.setText("Following arrival time must be larger than the previous one.");
				return;
			}

			previousHour = currentHour;

			if (locationStationFive.getLocationName().equals(departure)
					|| locationStationFive.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 5 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();

			if (!routeService.getAttachmentLocationsInRouteById(route.getRouteId()).contains(locationStationFive)) {
				routeService.addAttachmentLocation(route, locationStationFive, timeFive);
				logger.info("Added attachment location to this route. (Insert into RouteAttachment");
			}
		}
		if (stationSixChoiceBox.getValue() != null && stationSixChoiceBox.getValue().trim().length() != 0) {
			Location locationStationSix = new LocationService().getByName(stationSixChoiceBox.getValue().toString())
					.get();
			String timeSix = timeSixChoiceBox.getValue().trim().toString();

			int currentHour = Integer.parseInt(timeSix.split(":")[0]);

			if (currentHour <= previousHour) {
				informationLabel.setText("Following arrival time must be larger than the previous one.");
				return;
			}

			previousHour = currentHour;

			if (locationStationSix.getLocationName().equals(departure)
					|| locationStationSix.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 6 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();

			if (!routeService.getAttachmentLocationsInRouteById(route.getRouteId()).contains(locationStationSix)) {
				routeService.addAttachmentLocation(route, locationStationSix, timeSix);
				logger.info("Added attachment location to this route. (Insert into RouteAttachment");
			}
		}
		if (stationSevenChoiceBox.getValue() != null && stationSevenChoiceBox.getValue().trim().length() != 0) {
			Location locationStationSeven = new LocationService().getByName(stationSevenChoiceBox.getValue().toString())
					.get();
			String timeSeven = timeSevenChoiceBox.getValue().trim().toString();

			int currentHour = Integer.parseInt(timeSeven.split(":")[0]);

			if (currentHour <= previousHour) {
				informationLabel.setText("Following arrival time must be larger than the previous one.");
				return;
			}

			previousHour = currentHour;

			if (locationStationSeven.getLocationName().equals(departure)
					|| locationStationSeven.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 7 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			
			if (!routeService.getAttachmentLocationsInRouteById(route.getRouteId()).contains(locationStationSeven)) {
				routeService.addAttachmentLocation(route, locationStationSeven, timeSeven);
				logger.info("Added attachment location to this route. (Insert into RouteAttachment");
			}
		}
		if (stationEightChoiceBox.getValue() != null && stationEightChoiceBox.getValue().trim().length() != 0) {
			Location locationStationEight = new LocationService().getByName(stationEightChoiceBox.getValue().toString())
					.get();
			String timeEight = timeEightChoiceBox.getValue().trim().toString();

			int currentHour = Integer.parseInt(timeEight.split(":")[0]);

			if (currentHour <= previousHour) {
				informationLabel.setText("Following arrival time must be larger than the previous one.");
				return;
			}

			previousHour = currentHour;

			if (locationStationEight.getLocationName().equals(departure)
					|| locationStationEight.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 8 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();

			if (!routeService.getAttachmentLocationsInRouteById(route.getRouteId()).contains(locationStationEight)) {
				routeService.addAttachmentLocation(route, locationStationEight, timeEight);
				logger.info("Added attachment location to this route. (Insert into RouteAttachment");
			}
		}
		if (stationNineChoiceBox.getValue() != null && stationNineChoiceBox.getValue().trim().length() != 0) {
			Location locationStationNine = new LocationService().getByName(stationNineChoiceBox.getValue().toString())
					.get();
			String timeNine = timeNineChoiceBox.getValue().trim().toString();

			int currentHour = Integer.parseInt(timeNine.split(":")[0]);

			if (currentHour <= previousHour) {
				informationLabel.setText("Following arrival time must be larger than the previous one.");
				return;
			}

			previousHour = currentHour;

			if (locationStationNine.getLocationName().equals(departure)
					|| locationStationNine.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 9 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();

			if (!routeService.getAttachmentLocationsInRouteById(route.getRouteId()).contains(locationStationNine)) {
				routeService.addAttachmentLocation(route, locationStationNine, timeNine);
				logger.info("Added attachment location to this route. (Insert into RouteAttachment");
			}
		}
		if (stationTenChoiceBox.getValue() != null && stationTenChoiceBox.getValue().trim().length() != 0) {
			Location locationStationTen = new LocationService().getByName(stationTenChoiceBox.getValue().toString())
					.get();
			String timeTen = timeTenChoiceBox.getValue().trim().toString();

			int currentHour = Integer.parseInt(timeTen.split(":")[0]);

			if (currentHour <= previousHour) {
				informationLabel.setText("Following arrival time must be larger than the previous one.");
				return;
			}

			previousHour = currentHour;

			if (locationStationTen.getLocationName().equals(departure)
					|| locationStationTen.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 10 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();

			if (!routeService.getAttachmentLocationsInRouteById(route.getRouteId()).contains(locationStationTen)) {
				routeService.addAttachmentLocation(route, locationStationTen, timeTen);
				logger.info("Added attachment location to this route. (Insert into RouteAttachment");
			}
		}
		if (stationElevenChoiceBox.getValue() != null && stationElevenChoiceBox.getValue().trim().length() != 0) {
			Location locationStationEleven = new LocationService()
					.getByName(stationElevenChoiceBox.getValue().toString()).get();
			String timeEleven = timeElevenChoiceBox.getValue().trim().toString();

			int currentHour = Integer.parseInt(timeEleven.split(":")[0]);

			if (currentHour <= previousHour) {
				informationLabel.setText("Following arrival time must be larger than the previous one.");
				return;
			}

			previousHour = currentHour;

			if (locationStationEleven.getLocationName().equals(departure)
					|| locationStationEleven.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 11 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();

			if (!routeService.getAttachmentLocationsInRouteById(route.getRouteId()).contains(locationStationEleven)) {
				routeService.addAttachmentLocation(route, locationStationEleven, timeEleven);
				logger.info("Added attachment location to this route. (Insert into RouteAttachment");
			}
		}
		if (stationTwelveChoiceBox.getValue() != null && stationTwelveChoiceBox.getValue().trim().length() != 0) {
			Location locationStationTwelve = new LocationService()
					.getByName(stationTwelveChoiceBox.getValue().toString()).get();
			String timeTwelve = timeTwelveChoiceBox.getValue().trim().toString();

			int currentHour = Integer.parseInt(timeTwelve.split(":")[0]);

			if (currentHour <= previousHour) {
				informationLabel.setText("Following arrival time must be larger than the previous one.");
				return;
			}

			previousHour = currentHour;

			if (locationStationTwelve.getLocationName().equals(departure)
					|| locationStationTwelve.getLocationName().equals(arrival)) {
				informationLabel.setText("Station 12 matches with arrival or departure location");
				return;
			}
			RouteService routeService = new RouteService();
			
			if (!routeService.getAttachmentLocationsInRouteById(route.getRouteId()).contains(locationStationTwelve)) {
				routeService.addAttachmentLocation(route, locationStationTwelve, timeTwelve);
				logger.info("Added attachment location to this route. (Insert into RouteAttachment");
			}
		}
		stage.close();

	}
}
