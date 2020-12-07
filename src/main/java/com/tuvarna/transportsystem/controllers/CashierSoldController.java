package com.tuvarna.transportsystem.controllers;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.services.TicketService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class CashierSoldController implements Initializable {
	@FXML
	private TableView<Ticket> ticketsSoldTable;
	@FXML
	private TableColumn<Ticket, String> ticketId;

	@FXML
	private TableColumn<Ticket, String> ticketDeparture;

	@FXML
	private TableColumn<Ticket, String> ticketArrival;

	@FXML
	private TableColumn<Ticket, String> tripType;

	@FXML
	private TableColumn<Ticket, String> transportType;

	@FXML
	private TableColumn<Ticket, String> customer;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ticketId.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(String.valueOf(param.getValue().getTicketId()));
					}
				});

		ticketDeparture.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(
								String.valueOf(param.getValue().getDepartureLocation().getLocationName()));
					}
				});

		ticketArrival.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(
								String.valueOf(param.getValue().getArrivalLocation().getLocationName()));
					}
				});

		tripType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(
								String.valueOf(param.getValue().getTrip().getTripType().getTripTypeName()));
					}
				});

		transportType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						return new SimpleStringProperty(String
								.valueOf(param.getValue().getTrip().getTripTransportType().getTransportTypeName()));
					}
				});

		customer.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> param) {
						UserService userService = new UserService();
						
						List<User> users = userService.getAll();
						
						for (User user : users) {
							if (user.getTickets().contains(param.getValue())) {
								return new SimpleStringProperty(String.valueOf(user.getUserFullName()));
							}
						}
						
						return new SimpleStringProperty(String.valueOf("-"));
					}
				});

		ticketsSoldTable.setItems(getTicketsSold());
	}

	private ObservableList<Ticket> getTicketsSold() {
		ObservableList<Ticket> ticketList = FXCollections.observableArrayList();

		TicketService ticketService = new TicketService();

		List<Ticket> allTickets = ticketService.getAll();

		for (Ticket ticket : allTickets) {
			if (ticket.getTrip().getCashiers().contains(DatabaseUtils.currentUser)) {
				ticketList.add(ticket);
			}
		}

		return ticketList;

	}

	public void goToLogIn(javafx.event.ActionEvent event) throws IOException {
		Parent schedulePanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
		Scene scheduleScene = new Scene(schedulePanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scheduleScene);
		window.show();
		
		DatabaseUtils.currentUser = null;
	}

	public void goToSchedule(javafx.event.ActionEvent event) throws IOException {
		Parent schedulePanel = FXMLLoader.load(getClass().getResource("/views/CashierSchedulePanel.fxml"));
		Scene scheduleScene = new Scene(schedulePanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scheduleScene);
		window.show();

	}

	public void goToNotifications(javafx.event.ActionEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/CashierNotificationsPanel.fxml"));
		AnchorPane root = (AnchorPane) userPanel.load();
		Scene adminScene = new Scene(root);
		stage.setScene(adminScene);
		stage.setTitle("Transport Company");
		stage.showAndWait();
	}

}
