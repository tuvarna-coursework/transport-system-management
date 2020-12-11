package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.RouteAttachment;
import com.tuvarna.transportsystem.entities.Trip;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DistributorShowRouteAttachmentsController implements Initializable {
	@FXML
	private TableView<RouteAttachment> tripTableView;
	@FXML
	private TableColumn<RouteAttachment, String> station_col;
	@FXML
	private TableColumn<RouteAttachment, String> hour_col;
	@FXML
	private Button closeButton;
	@FXML
	private Label informationLabel;

	Trip globalTrip;

	private static final Logger logger = LogManager
			.getLogger(DistributorShowRouteAttachmentsController.class.getName());

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");

		station_col.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<RouteAttachment, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<RouteAttachment, String> param) {
						return new SimpleStringProperty(param.getValue().getLocation().getLocationName());
					}
				});
		hour_col.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<RouteAttachment, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<RouteAttachment, String> param) {
						return new SimpleStringProperty(param.getValue().getHourOfArrival());
					}
				});

	}

	public void getTrip(Trip trip) {
		globalTrip = trip;
	}

	public void view() {
		tripTableView.setItems(showAttachments());
		if (showAttachments().isEmpty()) {
			informationLabel.setText("This trip doesn't have attached station. IT'S EXPRESS!");
			return;
		}
		informationLabel
				.setText("Stations between " + globalTrip.getRoute().getRouteDepartureLocation().getLocationName()
						+ " and " + globalTrip.getRoute().getRouteArrivalLocation().getLocationName() + "!");

		logger.info("Showing attachment locations for this trip.");
	}

	public void close() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();

	}

	public ObservableList<RouteAttachment> showAttachments() {
		ObservableList<RouteAttachment> tripList = FXCollections.observableArrayList();
		List<RouteAttachment> eList = globalTrip.getRoute().getAttachmentLocations();
		for (RouteAttachment ent : eList) {
			tripList.add(ent);
		}
		return tripList;
	}
}
