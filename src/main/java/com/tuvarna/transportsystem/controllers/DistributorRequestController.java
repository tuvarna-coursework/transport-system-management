package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Request;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.services.RequestService;
import com.tuvarna.transportsystem.services.TripService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
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
import java.util.List;
import java.util.ResourceBundle;

public class DistributorRequestController implements Initializable {

    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private TableView<Trip> requestTable;
    @FXML
    private TableColumn<Trip,String> departure_col;
    @FXML
    private TableColumn<Trip,String> arrival_col;
    @FXML
    private TableColumn<Trip,String> hour_col;
    @FXML
    private TableColumn<Trip,Integer> capacity_col;
    @FXML
    private TableColumn<Trip,Integer> tickets_col;
    @FXML
    private TableColumn<Trip,String> company_col;
    @FXML
    private TextField requiredTicketsTextField;
    @FXML
    private Label informationLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departure_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trip,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
                return new SimpleStringProperty(param.getValue().getRoute().getRouteDepartureLocation().getLocationName());
            }
        });
        arrival_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trip,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
                return new SimpleStringProperty(param.getValue().getRoute().getRouteArrivalLocation().getLocationName());
            }
        });
        
        company_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trip,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Trip, String> param) {
            	UserService userService = new UserService();
            	int tripId = param.getValue().getTripId();
            	
            	User user = userService.getUserByTripId(tripId).get();
            	
                return new SimpleStringProperty(user.getUserFullName());
            }
        });
        hour_col.setCellValueFactory(new PropertyValueFactory<Trip,String>("tripDepartureHour"));
        capacity_col.setCellValueFactory(new PropertyValueFactory<Trip,Integer>("tripCapacity"));
        tickets_col.setCellValueFactory(new PropertyValueFactory<Trip,Integer>("tripTicketAvailability"));
        requestTable.setItems(getTrips());

    }
    public ObservableList<Trip> getTrips(){
        ObservableList<Trip> tripList=FXCollections.observableArrayList();
        TripService tripService= new TripService();

        List<Trip> eList = tripService.getAll();
        for (Trip ent : eList) {
            if (DatabaseUtils.currentUser.getTrips().contains(ent)) {
                tripList.add(ent);
            }
        }
        return tripList;
    }


    public void goToAddCashier(javafx.event.ActionEvent event) throws IOException {
        Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorAddPanel.fxml"));
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
    public void goToScheduleDistributor(javafx.event.ActionEvent event) throws IOException {
        Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorPanelSchedule.fxml"));
        Scene adminScene = new Scene(userPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.show();

    }


    public void makeRequest(){

        Trip trip = requestTable.getSelectionModel().getSelectedItem();
        int required;
        if(trip== null){
            informationLabel.setText("Please select trip!");
            return;
        }
        String requiredTickets= requiredTicketsTextField.getText().trim().toString();
        if(requiredTickets.isEmpty()){
            informationLabel.setText("Please enter required tickets!");
            return;
        }
        try {
            required= Integer.parseInt(requiredTickets);
        }catch (Exception e){
            informationLabel.setText("Enter number!");
            return;
        }
        int calCapacity=trip.getTripCapacity();
        int calTickets=trip.getTripTicketAvailability();
        int filCal=calCapacity-calTickets;
        if(required>filCal){
            informationLabel.setText("Not ENOUGH seats in the bus! Enter less tickets quantity!");
            return;
        }

        RequestService requestService = new RequestService();
        Request request= new Request(required, trip, DatabaseUtils.REQUEST_STATUSPENDING);
        requestService.save(request);
        informationLabel.setText("You send a request for " + requiredTicketsTextField.getText() + " tickets for trip #"+trip.getTripId());
    }
    public void showRequest(javafx.event.ActionEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/DistributorRequestStatusPanel.fxml"));
        DialogPane root = (DialogPane) userPanel.load();
        Scene adminScene = new Scene(root);
        stage.setScene(adminScene);
        stage.setTitle("Transport Company");
        stage.showAndWait();

    }
    public void goToNotifications(javafx.event.ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/DistributorNotificationsPanel.fxml"));
        AnchorPane root = (AnchorPane) userPanel.load();
        Scene adminScene = new Scene(root);
        stage.setScene(adminScene);
        stage.setTitle("Transport Company");
        stage.showAndWait();
    }
}
