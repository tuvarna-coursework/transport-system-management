package com.tuvarna.transportsystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class AdminHonorariumController implements Initializable {

	@FXML
	private TextField userNameTextField;

	@FXML
	private TextField userHonorariumTextField;

	@FXML
	private TextField userRatingTextField;
	
	@FXML
	private Label informationLabel;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

	}

	public void goBack(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/AdminPanel.fxml"));
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
		
		DatabaseUtils.currentUser = null;
	}

	public void editOnClick(javafx.event.ActionEvent event) throws IOException {
		String userName = userNameTextField.getText().trim();
		UserService userService = new UserService();

		boolean isChanged = false;

		if (!userService.getByName(userName).isPresent()) {
			informationLabel.setText("User not found.");
			return;
		}

		User user = userService.getByName(userName).get();
		UserProfile profile = user.getUserProfile();

		/* Nothing to update; terminate */
		if (userHonorariumTextField.getText().isEmpty() && userRatingTextField.getText().isEmpty()) {
			informationLabel.setText("No changes to be made.");
			return;
		} else {
			if (userRatingTextField.getText().isEmpty()) {
				double honorarium = Double.parseDouble(userHonorariumTextField.getText());

				/*
				 * Admin wants to change the horarium only but keep the rating the same. Check
				 * if the values differ
				 */
				if (profile.getUserProfileHonorarium() != honorarium) {
					profile.setUserProfileHonorarium(honorarium);
					isChanged = true;
				}
			} else if (userHonorariumTextField.getText().isEmpty()) {
				double rating = Double.parseDouble(userRatingTextField.getText());

				if (profile.getUserProfileRating() != rating) {
					profile.setUserProfileRating(rating);
					isChanged = true;
				}
			} else {
				/* Both values should be changed */
				double honorarium = Double.parseDouble(userHonorariumTextField.getText());
				double rating = Double.parseDouble(userRatingTextField.getText());

				if (profile.getUserProfileHonorarium() != honorarium) {
					profile.setUserProfileHonorarium(honorarium);
					isChanged = true;
				}

				if (profile.getUserProfileRating() != rating) {
					profile.setUserProfileRating(rating);
					isChanged = true;
				}
			}
		}

		if (isChanged) {
			userService.updateUserProfile(user, profile);
			informationLabel.setText("Changes applied successfully.");
		}
	}
}
