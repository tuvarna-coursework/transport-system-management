package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.entities.UserType;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.UserProfileService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.services.UserTypeService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

	ObservableList list = FXCollections.observableArrayList();
	@FXML
	private ChoiceBox<String> locationChoiceBox;
	@FXML
	private TextField fullnameTextField;
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordTextField;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadLocation();

	}

	public void backToLogIn(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

	}

	public void registerButton(javafx.event.ActionEvent event) throws IOException {

		String fullname = fullnameTextField.getText();
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		String userlocation = locationChoiceBox.getValue();

		LocationService locationService = new LocationService();
		Location location = (Location) locationService.getByName(userlocation);

		UserProfile profile = new UserProfile(0.0, 0.0);
		new UserProfileService().save(profile);
		UserType type = DatabaseUtils.USERTYPE_USER;
		UserService userService = new UserService();
		User user = new User(fullname, username, password, profile, type, location);
		userService.save(user);
		
		userService.addRole(user, DatabaseUtils.ROLE_USER);

		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/UserPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

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
		list.addAll(city_01, city_02, city_03, city_04, city_05, city_06, city_07, city_08, city_09, city_10, city_11);
		locationChoiceBox.getItems().addAll(list);
	}

}
