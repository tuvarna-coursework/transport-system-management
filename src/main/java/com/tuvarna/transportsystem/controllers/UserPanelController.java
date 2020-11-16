package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.services.TripService;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
		loadquantity();
		loadTime();
		loadDepartureArrivalLocation();

	}
	public void loadquantity(){
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
	public void loadDepartureArrivalLocation(){
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
	public void logOut(javafx.event.ActionEvent event)throws IOException{
		Parent ticketPanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
		Scene ticketScene = new Scene(ticketPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(ticketScene);
		window.show();

	}
	public void buyTicket(javafx.event.ActionEvent event)throws IOException{

		/*String departureStation=departureChoiceBox.getValue();
		String arrivalStation = arrivalChoiceBox.getValue();
		TripService tripService= new TripService();
		Trip departure = (Trip) tripService.getByDepartureLocation(departureStation);
		Trip arrival = (Trip) tripService.getByArrivalLocation(arrivalStation);
		departure.getTripDepartureDate();
		arrival.getTripArrivalLocation();*/




		informationLabel.setText("You bought ticket");


	}
}
