package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Route;
import com.tuvarna.transportsystem.entities.RouteAttachment;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.services.RouteService;
import com.tuvarna.transportsystem.services.TripService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CompanyShowRouteAttachmentsController implements Initializable {
    @FXML
    private Label informationLabel;
    @FXML
    private TableView<RouteAttachment> tripTableView;
    @FXML
    private TableColumn<RouteAttachment,String> station_col;
    @FXML
    private TableColumn<RouteAttachment,String> hour_col;
    @FXML
    private Button closeButton;
    Trip globalTrip;
    int routeID=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        station_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RouteAttachment, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<RouteAttachment, String> param) {
                        return new SimpleStringProperty(param.getValue().getLocation().getLocationName());
                    }
                });
        hour_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RouteAttachment, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RouteAttachment, String> param) {
                return new SimpleStringProperty(param.getValue().getHourOfArrival());
            }
        });
    }
    public void getTrip(Trip trip){
        globalTrip=trip;

    }
    public void view(){
        tripTableView.setItems(showAttachments());
        informationLabel.setText("Stations between " + globalTrip.getRoute().getRouteDepartureLocation().getLocationName() + " and " + globalTrip.getRoute().getRouteArrivalLocation().getLocationName() + "!");

    }
    public void close(){
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
