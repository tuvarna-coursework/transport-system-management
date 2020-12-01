package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserType;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.services.UserTypeService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class DistributorAddCashierController implements Initializable {

    @FXML
    private Label informationLabel;
    @FXML
    private ComboBox<User> companyComboBox;
    @FXML
    private ChoiceBox<String> locationChoiceBox;
    @FXML
    private TextField fullnameTextField;

    ObservableList list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLocation();
        companyComboBox.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> userListView) {
                ListCell<User> cell = new ListCell<User>() {
                    @Override
                    protected void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                        } else {
                            setText(item.getUserFullName());
                        }
                    }
                };
                return cell;
            }
        });
        companyComboBox.getItems().addAll(getCompanies());
    }

    public ObservableList<User> getCompanies(){
        ObservableList<User> userList=FXCollections.observableArrayList();
        UserService userService= new UserService();
        String type= "Transport Company";
        List<User> eList = userService.getByUserType(type);
        for (User ent : eList) {
            userList.add(ent);
        }
        return userList;
    }
    public void loadLocation() {
        list.removeAll(list);
        String city_01 = "Varna";
        String city_02 = "Sofia";
        String city_03 = "Shumen";
        String city_04 = "Veliko Turnovo";
        String city_05 = "Razgrad";
        String city_06 = "Gabrovo";
        String city_07 = "Plovdiv";
        String city_08 = "Burgas";
        String city_09 = "Stara Zagora";
        String city_10 = "Blagoevgrad";
        String city_11 = "Sliven";
        String city_12 = "Pleven";
        String city_13 = "Omurtag";
        String city_14 = "Ruse";
        String city_15 = "Dobrich";
        String city_16 = "Montana";
        String city_17 = "Vraca";
        String city_18 = "Yambol";
        String city_19 = "Pernik";
        String city_20 = "Lovech";
        String city_21 = "Turgovishte";
        list.addAll(city_01, city_02, city_03, city_04, city_05, city_06, city_07, city_08, city_09, city_10, city_11,
                city_12, city_13, city_14, city_15, city_16, city_17, city_18, city_19, city_20,city_21);
        locationChoiceBox.getItems().addAll(list);
    }

    public void goToRequest(javafx.event.ActionEvent event) throws IOException {
        Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorRequestPanel.fxml"));
        Scene adminScene = new Scene(userPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.show();

    }
    public void goToLogIn(javafx.event.ActionEvent event) throws IOException {
        Parent userPanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
        Scene adminScene = new Scene(userPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.show();

    }
    public void goToSchedule(javafx.event.ActionEvent event) throws IOException {
        Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorPanelSchedule.fxml"));
        Scene adminScene = new Scene(userPanel);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.show();

    }



}
