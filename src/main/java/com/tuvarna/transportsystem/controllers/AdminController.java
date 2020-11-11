package com.tuvarna.transportsystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.entities.UserType;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.UserProfileService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class AdminController implements Initializable {

	@FXML
	private RadioButton distributor;

	@FXML
	private RadioButton company;

	@FXML
	private TextField nameField;

	@FXML
	private Button addButton;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

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

	public void addButtonOnAction(javafx.event.ActionEvent event) throws IOException {
		if (nameField.getText() != null) {
			UserService userService = new UserService();
			UserType userType;

			if (distributor.isSelected()) {
				userType = DatabaseUtils.USERTYPE_DISTRIBUTOR;
			} else if (company.isSelected()) {
				userType = DatabaseUtils.USERTYPE_COMPANY;
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Constraint mismatch");
				alert.setHeaderText("Please specify the type of the user you wish to create");

				alert.showAndWait();
				return;
			}

			/*
			 * Full name and location is currently hard coded since I am awaiting front end
			 * changes
			 */
			Location location = (Location) new LocationService().getById(1);

			/* Create a unique User Profile associated with this user */
			UserProfileService userProfileService = new UserProfileService();
			UserProfile profile = new UserProfile();
			userProfileService.save(profile);

			String username = this.generateUserName(nameField.getText());
			String password = this.generatePassword();

			User user = new User("TEST", username, password, profile, userType, location);
			userService.addRole(user, DatabaseUtils.ROLE_USER);
			userService.save(user);

			StringBuilder outputString = new StringBuilder();
			outputString.append(" Username: ").append(username).append("\n Password: ").append(password);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User succesfully created.");
			alert.setHeaderText("Please provide login credentials to the user!");
			alert.setContentText(outputString.toString());

			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Constraint mismatch");
			alert.setHeaderText("Please provide a name for the user you wish to create");

			alert.showAndWait();
		}
	}

	@FXML
	ToggleGroup radioButtonAdmin;

}
