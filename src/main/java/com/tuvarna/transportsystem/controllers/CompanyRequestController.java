package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Request;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.services.RequestService;
import com.tuvarna.transportsystem.services.TripService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CompanyRequestController implements Initializable {
	@FXML
	private TableView<Request> requestCompanyTable;
	@FXML
	private TableColumn<Request, Integer> requestId_col;
	@FXML
	private TableColumn<Request, String> departure_col;
	@FXML
	private TableColumn<Request, String> arrival_col;
	@FXML
	private TableColumn<Request, String> time_col;
	@FXML
	private TableColumn<Request, String> capacity_col;
	@FXML
	private TableColumn<Request, String> available_col;
	@FXML
	private TableColumn<Request, Integer> requested_col;
	@FXML
	private TableColumn<Request, String> status_col;
	@FXML
	private Label informationLabel;
	
	
	private static final Logger logger = LogManager.getLogger(CompanyRequestController.class.getName());

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");
		
		departure_col.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Request, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Request, String> param) {
						return new SimpleStringProperty(
								param.getValue().getTrip().getRoute().getRouteDepartureLocation().getLocationName());
					}
				});

		arrival_col.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Request, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Request, String> param) {
						return new SimpleStringProperty(
								param.getValue().getTrip().getRoute().getRouteArrivalLocation().getLocationName());
					}
				});
		time_col.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Request, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Request, String> param) {
						return new SimpleStringProperty(param.getValue().getTrip().getTripDepartureHour());
					}
				});
		capacity_col.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Request, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Request, String> param) {
						return new SimpleStringProperty(String.valueOf(param.getValue().getTrip().getTripCapacity()));
					}
				});
		available_col.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Request, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Request, String> param) {
						return new SimpleStringProperty(
								String.valueOf(param.getValue().getTrip().getTripTicketAvailability()));
					}
				});

		status_col.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Request, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Request, String> param) {
						return new SimpleStringProperty(String.valueOf(param.getValue().getStatus()));
					}
				});

		requested_col.setCellValueFactory(new PropertyValueFactory<Request, Integer>("ticketsQuantity"));
		requestId_col.setCellValueFactory(new PropertyValueFactory<Request, Integer>("requestId"));
		requestCompanyTable.setItems(getRequests());
		
		logger.info("Successfully loaded table structure and populated available requests.");
	}

	public ObservableList<Request> getRequests() {
		ObservableList<Request> requestsList = FXCollections.observableArrayList();
		RequestService requestService = new RequestService();

		List<Request> eList = requestService.getAll();
		for (Request ent : eList) {
			requestsList.add(ent);
		}
		return requestsList;

	}

	public void goToScheduleCompany(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/CompanySchedulePanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
		
		logger.info("Switched to schedule tab.");
	}

	public void goToAddTrip(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/CompanyAddTripPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
		
		logger.info("Switched to add trip tab.");
	}

	public void backToLogIn(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
		
		DatabaseUtils.currentUser = null;
		logger.info("User successfully logged out.");
	}
	
	public void goToNotifications(javafx.event.ActionEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/CompanyNotificationsPanel.fxml"));
		AnchorPane root = (AnchorPane) userPanel.load();
		Scene adminScene = new Scene(root);
		stage.setScene(adminScene);
		stage.setTitle("Transport Company");
		stage.showAndWait();
		
		logger.info("Switched to notifications tab.");
	}

	public void acceptRequest(javafx.event.ActionEvent event) throws IOException {
		Request request = requestCompanyTable.getSelectionModel().getSelectedItem();

		if (request == null) {
			informationLabel.setText("Please select request from table!");
			return;
		}
		
		if (request.getStatus().equals("REJECTED")) {
			informationLabel.setText("Cannot accept a previously rejected request.");
			return;
		}
		
		if (request.getStatus().equals("ACCEPTED")) {
			informationLabel.setText("Request already accepted.");
			return;
		}

		int tickets = request.getTrip().getTripTicketAvailability();
		int requestedTickets = request.getTicketsQuantity();
		int newTickets = tickets + requestedTickets;

		Trip trip = request.getTrip();
		TripService tripService = new TripService();
		tripService.updateTripTicketAvailability(trip, newTickets);
		logger.info("Request accepted: updating tickets availability for the trip.");

		RequestService requestService = new RequestService();
		requestService.updateStatus(request, DatabaseUtils.REQUEST_STATUSACCEPTED);
		logger.info("Request was accepted.");

		// refresh table
		requestCompanyTable.setItems(getRequests());
		informationLabel.setText("Request was accepted!");
	}

	public void rejectRequest(javafx.event.ActionEvent event) throws IOException {
		Request request = requestCompanyTable.getSelectionModel().getSelectedItem();

		if (request == null) {
			informationLabel.setText("Please select request from table!");
			return;
		}
		
		if (request.getStatus().equals("ACCEPTED")) {
			informationLabel.setText("Cannot reject a previously accepted request.");
			return;
		}
		
		if (request.getStatus().equals("REJECTED")) {
			informationLabel.setText("Request already rejected.");
			return;
		}

		RequestService requestService = new RequestService();
		requestService.updateStatus(request, DatabaseUtils.REQUEST_STATUSREJECTED);
		logger.info("Request status updated: REJECTED");

		// refresh table
		requestCompanyTable.setItems(getRequests());
		informationLabel.setText("Request was rejected!");
		logger.info("Table refreshed. Request rejected.");
	}
}
