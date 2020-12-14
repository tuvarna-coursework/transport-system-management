package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.utils.DatabaseUtils;
import com.tuvarna.transportsystem.utils.NotificationUtils;
import com.tuvarna.transportsystem.dao.TripDAO;
import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.services.RequestService;
import com.tuvarna.transportsystem.services.TicketService;
import com.tuvarna.transportsystem.services.TripService;
import com.tuvarna.transportsystem.services.UserService;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CompanyScheduleController implements Initializable {

	@FXML
	private TableView<Trip> companyScheduleTable;
	@FXML
	private TableColumn<Trip, String> col_departure;
	@FXML
	private TableColumn<Trip, Integer> col_capacity;
	@FXML
	private TableColumn<Trip, String> col_departureDate;
	@FXML
	private TableColumn<Trip, String> col_arrival;
	@FXML
	private TableColumn<Trip, Integer> col_availableTickets;
	@FXML
	private TableColumn<Trip, String> col_tripType;
	@FXML
	private TableColumn<Trip, String> col_transportType;
	@FXML
	private TableColumn<Trip, Double> col_price;
	@FXML
	private TableColumn<Trip, Integer> col_duration;

	@FXML
	private TableColumn<Trip, String> col_hourOfDeparture;

	@FXML
	private Button btnCancel;

	@FXML
	private Label informationLabel;

	private static final Logger logger = LogManager.getLogger(CompanyScheduleController.class.getName());

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");

		col_departure.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(
								param.getValue().getRoute().getRouteDepartureLocation().getLocationName());
					}
				});
		col_departureDate.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripDepartureDate().toString());
					}
				});
		col_arrival.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(
								param.getValue().getRoute().getRouteArrivalLocation().getLocationName());
					}
				});

		col_tripType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripType().getTripTypeName());
					}
				});
		col_transportType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripTransportType().getTransportTypeName());
					}
				});

		col_hourOfDeparture.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trip, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
						return new SimpleStringProperty(param.getValue().getTripDepartureHour());
					}
				});
		col_duration.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("tripDuration"));
		col_price.setCellValueFactory(new PropertyValueFactory<Trip, Double>("tripTicketPrice"));
		col_availableTickets.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("tripTicketAvailability"));
		col_capacity.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("tripCapacity"));
		companyScheduleTable.setItems(getTripSchedule());

		logger.info("Loaded table structure and populated trips for this company.");

	}

	public ObservableList<Trip> getTripSchedule() {
		ObservableList<Trip> tripList = FXCollections.observableArrayList();
		TripService tripService = new TripService();

		List<Trip> eList = tripService.getAll();
		for (Trip ent : eList) {
			if (DatabaseUtils.currentUser.getTrips().contains(ent)) {
				tripList.add(ent);
			}
		}
		return tripList;
	}

	public void goToAddTrip(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/CompanyAddTripPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		logger.info("Switched to add trip tab.");
	}

	public void goToRequest(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/CompanyRequestPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		logger.info("Switched to request tab.");
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

	public void goToNotifications(javafx.event.ActionEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/CompanyNotificationsPanel.fxml"));
		AnchorPane root = (AnchorPane) userPanel.load();
		Scene adminScene = new Scene(root);
		stage.setScene(adminScene);
		stage.setTitle("Transport Company");
		stage.showAndWait();
	}

	public void showAttachments(javafx.event.ActionEvent event) throws IOException {
		if (companyScheduleTable.getSelectionModel().getSelectedItem() == null) {
			informationLabel.setText("Select trip first!");
			return;
		}
		Trip tripSend = companyScheduleTable.getSelectionModel().getSelectedItem();
		Stage stage = new Stage();
		FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/CompanyShowRouteAttachments.fxml"));
		DialogPane root = (DialogPane) userPanel.load();
		// send trip to other controller
		CompanyShowRouteAttachmentsController controller = (CompanyShowRouteAttachmentsController) userPanel
				.getController();
		controller.getTrip(tripSend);

		Scene adminScene = new Scene(root);
		stage.setScene(adminScene);
		stage.setTitle("Transport Company");
		stage.showAndWait();

		logger.info("Switched to attachment locations view.");
	}

	public void cancelOnClick(javafx.event.ActionEvent event) throws IOException {
		if (companyScheduleTable.getSelectionModel().getSelectedItem() == null) {
			informationLabel.setText("Please select a trip you would like to cancel.");
			return;
		}

		Trip trip = companyScheduleTable.getSelectionModel().getSelectedItem();

		if (trip.getTripDepartureDate().compareTo(new Date(System.currentTimeMillis())) <= 0) {
			informationLabel.setText("Cannot cancel a live trip or a completed trip.");
			return;
		}

		int tripId = trip.getTripId();

		/*
		 * Couldn't make a working REMOVE cascade no matter what, that's why I need to
		 * manually cascade everything... 1. Remove every ticket from the UsersTicket
		 * table 2. Remove every ticket associated with the trip from Ticket table 3.
		 * Remove the trip from UsersTrip (Company - trip) 4. Remove all requests for
		 * this trip 5. Delete the trip itself
		 */

		TripService tripService = new TripService();
		TicketService ticketService = new TicketService();
		UserService userService = new UserService();
		RequestService requestService = new RequestService();

		User company = userService.getUserByTripId(tripId).get();
		List<Ticket> tickets = ticketService.getByTrip(tripId);
		List<User> users = userService.getAll();

		try {
			users.forEach(u -> {
				List<Ticket> userTickets = u.getTickets();

				userTickets.forEach(t -> {
					if (t.getTrip().getTripId() == tripId) {
						userService.removeTicket(u, t);
					}
				});
			});

			tickets.forEach(t -> {
				if (t.getTrip().getTripId() == tripId) {
					ticketService.deleteById(t.getTicketId());
				}
			});

			tickets.forEach(t -> ticketService.deleteById(t.getTicketId()));
			userService.removeTrip(company, trip);
			requestService.deleteByTripId(tripId);

			logger.info("Trip deletion successfully cascaded");

			/* Inform distributor for the cancellation before it is deleted */
			NotificationUtils.generateCancelledTripNotification(trip);

			tripService.deleteById(tripId);
			logger.info("Trip deleted.");
		} catch (Exception e) {
			logger.error("Unable to delete trip. Most likely cascading failed at some point.");
			return;
		}

		informationLabel.setText("Trip has been successfully cancelled.");
	}

	public void showAttachments() {

	}
}
