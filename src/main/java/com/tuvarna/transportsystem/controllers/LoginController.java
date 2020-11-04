package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.sql.Connection;

public class LoginController implements Initializable {

	@FXML
	private Label informationLabel;
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordTextField;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

	}

	public void Change(javafx.event.ActionEvent event) throws IOException {
		Parent adminPanel = FXMLLoader.load(getClass().getResource("/views/CompanyAddTripPanel.fxml"));
		Scene adminScene = new Scene(adminPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

	}

	public void loginButtonOnAction(javafx.event.ActionEvent event) throws IOException {
		if (usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false) {
			validateLogin();
		} else {
			informationLabel.setText("Please enter username and password!");
		}
	}

	public void validateLogin() {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		UserService userService = new UserService();
		User user1 = new User();
		String fullname = user1.getUserLoginName();

		if (username == fullname) {
			informationLabel.setText("CORRECT");
		} else {
			informationLabel.setText("Invalid!");
		}

	}

}
