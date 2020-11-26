package com.tuvarna.transportsystem.controllers;
import com.tuvarna.transportsystem.utils.DatabaseUtils;


import com.tuvarna.transportsystem.dao.TripDAO;
import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.services.TripService;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.util.Callback;



import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CompanyScheduleController implements Initializable {


    @FXML
    private TableView<Trip> companyScheduleTable;
    @FXML
    private TableColumn<Trip,String> col_departure;
    @FXML
    private TableColumn<Trip,Integer> col_capacity;
    @FXML
    private TableColumn<Trip,String> col_departureDate;
    @FXML
    private TableColumn<Trip,String> col_arrival;
    @FXML
    private TableColumn<Trip,Integer> col_availableTickets;
    @FXML
    private TableColumn<Trip,String> col_tripType;
    @FXML
    private TableColumn<Trip,String>col_transportType;
    @FXML
    private TableColumn<Trip,Double>col_price;
    @FXML
    private TableColumn<Trip,Integer>col_duration;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_departure.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trip,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
                return new SimpleStringProperty(param.getValue().getRoute().getRouteDepartureLocation().getLocationName());
            }
        });
        col_departureDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trip,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
                return new SimpleStringProperty(param.getValue().getTripDepartureDate().toString());
            }
        });
        col_arrival.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trip,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
                return new SimpleStringProperty(param.getValue().getRoute().getRouteArrivalLocation().getLocationName());
            }
        });

        col_tripType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trip,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
                return new SimpleStringProperty(param.getValue().getTripType().getTripTypeName());
            }
        });
        col_transportType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trip,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
                return new SimpleStringProperty(param.getValue().getTripTransportType().getTransportTypeName());
            }
        });
        col_duration.setCellValueFactory(new PropertyValueFactory<Trip,Integer>("tripDuration"));
        col_price.setCellValueFactory(new PropertyValueFactory<Trip,Double>("tripTicketPrice"));
        col_availableTickets.setCellValueFactory(new PropertyValueFactory<Trip,Integer>("tripTicketAvailability"));
        col_capacity.setCellValueFactory(new PropertyValueFactory<Trip,Integer>("tripCapacity"));
        companyScheduleTable.setItems(getTripSchedule());

    }


    public ObservableList<Trip> getTripSchedule(){
        ObservableList<Trip> tripList=FXCollections.observableArrayList();
        TripService tripService= new TripService();

        List<Trip> eList = tripService.getAll();
        for (Trip ent : eList) {
            tripList.add(ent);
        }
        return tripList;

    }

    public void goToAddTrip(javafx.event.ActionEvent event) throws IOException {
        Parent userPanel = FXMLLoader.load(getClass().getResource("/views/CompanyAddTripPanel.fxml"));
        Scene adminScene = new Scene(userPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.show();
    }

    public void goToRequest(javafx.event.ActionEvent event) throws IOException {
        Parent userPanel = FXMLLoader.load(getClass().getResource("/views/CompanyRequestPanel.fxml"));
        Scene adminScene = new Scene(userPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.show();

    }
    public void backToLogIn(javafx.event.ActionEvent event) throws IOException {
        Parent userPanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
        Scene adminScene = new Scene(userPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.show();


    }



}
