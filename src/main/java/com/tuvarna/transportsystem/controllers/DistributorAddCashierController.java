package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.User;
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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


public class DistributorAddCashierController implements Initializable {

    @FXML
    private Label informationLabel;
    @FXML
    private ComboBox<String> companyComboBox;
    @FXML
    private ChoiceBox<String> locationChoiceBox;
    @FXML
    private TextField fullnameTextField;

    ObservableList list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLocation();
        companyComboBox.setItems(getCompanies());
    }

    public ObservableList<String> getCompanies(){
        ObservableList<String> userList=FXCollections.observableArrayList();
        UserService userService= new UserService();
        String type= "Transport Company";
        List<User> eList = userService.getByUserType(type);
        for (User ent : eList) {
            userList.add(ent.getUserFullName());
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

    private String generatePassword() {
        StringBuilder sb = new StringBuilder();
        String randomString = "abcABCdItRrGmnNoOzZeEqWw_-()%$#@!^*=+";

        Random random = new Random();

        for (int i = 0; i < 9; i++) {
            if (i % 2 == 0) {
                sb.append(random.nextInt(9));
            } else {
                sb.append(randomString.charAt(random.nextInt(randomString.length())));
            }
        }

        return sb.toString();
    }
    private String generateUserName(String input) {
        StringBuilder sb = new StringBuilder();

        /*
         * Simple algorithm to generate username based on fullname: Usernames will be in
         * the format: 4 letters _ 3 digits If it is 4 or less characters it will take
         * all letters and if not it will take every other character until there are 4
         * characters.
         *
         * Then append 3 random digits
         */
        if (input.trim().length() > 4) {
            for (int i = 0; i < input.trim().length(); i++) {
                if (i == 4) {
                    break;
                }

                if (i % 2 == 0 && input.charAt(i) != ' ') {
                    sb.append(input.toUpperCase().charAt(i));
                }
            }
        } else {
            sb.append(input.toUpperCase());
        }

        sb.append("_");

        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            sb.append(random.nextInt(9));
        }

        return sb.toString();
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
    public void createCashier(javafx.event.ActionEvent event) throws IOException{
        if(!fullnameTextField.getText().isEmpty()){
            informationLabel.setText(companyComboBox.getValue());
            String fullname= fullnameTextField.getText();
            UserService userService= new UserService();
            /*
            NOT FINISHED FUNCTIONALITY!
             */

        }else informationLabel.setText("Enter cashier full name!");


    }



}
