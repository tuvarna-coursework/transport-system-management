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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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

	@FXML
	private Label informationLabel;

	private static final Logger logger = LogManager.getLogger(RegisterController.class.getName());

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");

		loadLocation();
	}

	public void backToLogIn(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		logger.info("User returned to login screen.");
	}

	public void registerButton(javafx.event.ActionEvent event) throws IOException {
		String fullname = fullnameTextField.getText();
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		String userlocation = locationChoiceBox.getValue();

		if (fullname.trim().length() > 40 || fullname.trim().length() < 5) {
			informationLabel.setText("Invalid fullname. Name must be between 5 and 40 characters.");
			return;
		}

		if ((!Pattern.matches("^\\w+$", username)) || username.length() < 4 || username.length() > 20) {
			informationLabel.setText("Invalid username. No spaces, special characters. Length: 4 - 20 characters.");
			return;
		}

		if (password.length() < 5 || password.length() > 20) {
			informationLabel.setText("Invalid password. Length: 5 - 20 characters.");
			return;
		}

		LocationService locationService = new LocationService();

		if (!locationService.getByName(userlocation).isPresent()) {
			logger.error("Location not present in database.");
			return;
		}

		Location location = locationService.getByName(userlocation).get();

		UserProfile profile = new UserProfile(0.0, 0.0);
		new UserProfileService().save(profile);
		UserType type = DatabaseUtils.USERTYPE_USER;
		UserService userService = new UserService();
		User user = new User(fullname, username, password, profile, type, location);
		userService.save(user);
		logger.info("Successfully persisted user to database.");

		userService.addRole(user, DatabaseUtils.ROLE_USER);
		logger.info("Assigned role user.");

		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/UserPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		logger.info("Registration successful, login successful.");
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
				city_12, city_13, city_14, city_15, city_16, city_17, city_18, city_19, city_20, city_21);
		locationChoiceBox.getItems().addAll(list);
	}
}
