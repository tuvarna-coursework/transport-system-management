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

import com.tuvarna.transportsystem.entities.Notification;
import com.tuvarna.transportsystem.services.NotificationService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class CompanyNotificationsController implements Initializable {
    @FXML
    private Label informationLabel;


    @FXML
    private TableView<Notification> notificationTableView;
   @FXML
   private TableColumn<Notification, String> notificationId_col;
   @FXML
   private TableColumn<Notification, String> sendFullName_col;
   @FXML
   private TableColumn<Notification, String> message_col;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	notificationId_col.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Notification, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Notification, String> param) {
						return new SimpleStringProperty(String.valueOf(param.getValue().getNotificationId()));
					}
				});
		
    	sendFullName_col.setCellValueFactory(
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
    }
    
    public ObservableList<Notification> getNotifications() {
		ObservableList<Notification> notificationList = FXCollections.observableArrayList();
		NotificationService notificationService = new NotificationService();

		List<Notification> notifications = notificationService.getNotificationsForReceiverId(DatabaseUtils.currentUser.getUserId());
		notificationList.addAll(notifications);
		return notificationList;

	}
    
    public void deleteNotification(){
        informationLabel.setText("Select notification!");
    }

}
