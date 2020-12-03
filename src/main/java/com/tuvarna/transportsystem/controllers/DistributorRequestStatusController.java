package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Request;
import com.tuvarna.transportsystem.services.RequestService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DistributorRequestStatusController implements Initializable {
    @FXML
    private TableView<Request> requestStatusTable;
    @FXML
    private TableColumn<Request,Integer> requestId_col;
    @FXML
    private TableColumn<Request,String> tripId_col;
    @FXML
    private TableColumn<Request,Integer> requestedTickets_col;
    @FXML
    private TableColumn<Request,String> seats_col;
    @FXML
    private TableColumn<Request,String> company_col;
    @FXML
    private TableColumn<Request,String> status_col;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tripId_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Request,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Request, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getTrip().getTripId()));
            }
        });
        seats_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Request,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Request, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getTrip().getTripCapacity()));
            }
        });

        requestedTickets_col.setCellValueFactory(new PropertyValueFactory<Request,Integer>("ticketsQuantity"));
        requestId_col.setCellValueFactory(new PropertyValueFactory<Request,Integer>("requestId"));


        requestStatusTable.setItems(getRequests());

    }
    public ObservableList<Request> getRequests(){
        ObservableList<Request> requestsList= FXCollections.observableArrayList();
        RequestService requestService = new RequestService();

        List<Request> eList = requestService.getAll();
        for (Request ent : eList) {
            requestsList.add(ent);
        }
        return requestsList;

    }
}
