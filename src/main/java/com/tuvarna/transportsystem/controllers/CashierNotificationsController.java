package com.tuvarna.transportsystem.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tuvarna.transportsystem.entities.Notification;
import com.tuvarna.transportsystem.services.NotificationService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class CashierNotificationsController implements Initializable {
	@FXML
	private Label informationLabel;

	@FXML
	private TableView<Notification> notificationTableView;
	@FXML
	private TableColumn<Notification, String> notificationId_col;
	@FXML
	private TableColumn<Notification, String> company_col;
	@FXML
	private TableColumn<Notification, String> message_col;
	
	private static final Logger logger = LogManager.getLogger(CashierNotificationsController.class.getName());

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");
		
		notificationId_col.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Notification, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Notification, String> param) {
						return new SimpleStringProperty(String.valueOf(param.getValue().getNotificationId()));
					}
				});

		company_col.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Notification, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Notification, String> param) {
						return new SimpleStringProperty(param.getValue().getSender().getUserFullName());
					}
				});

		message_col.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Notification, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Notification, String> param) {
						return new SimpleStringProperty(String.valueOf(param.getValue().getMessage()));
					}
				});

		notificationTableView.setItems(this.getNotifications());
		logger.info("Loaded and populated table.");
	}

	public ObservableList<Notification> getNotifications() {
		ObservableList<Notification> notificationList = FXCollections.observableArrayList();
		NotificationService notificationService = new NotificationService();

		List<Notification> notifications = notificationService
				.getNotificationsForReceiverId(DatabaseUtils.currentUser.getUserId());
		notificationList.addAll(notifications);
		return notificationList;
	}

	public void deleteNotification() {
		informationLabel.setText("Select notification!");
	}
}
