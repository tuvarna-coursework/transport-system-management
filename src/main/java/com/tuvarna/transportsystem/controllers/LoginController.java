package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserType;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.services.UserTypeService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;
import com.tuvarna.transportsystem.utils.NotificationUtils;

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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.plaf.nimbus.AbstractRegionPainter;
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

	private static final Logger logger = LogManager.getLogger(LoginController.class.getName());

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");
	}

	public void Change(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/Register.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		logger.info("Register panel loaded.");
	}

	public void ChangeToSuccessLogin(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/UserPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		logger.info("User successfully logged in.");
	}

	public void loginButtonOnAction(javafx.event.ActionEvent event) throws IOException {
		/*
		 * IsBlank() available on Java 11+; written like this for backwards
		 * compatibility
		 */
		if (usernameTextField.getText() != null && usernameTextField.getText().trim().length() != 0
				&& passwordTextField.getText() != null && passwordTextField.getText().trim().length() != 0) {

			try {
				String username = usernameTextField.getText();
				String password = passwordTextField.getText();

				UserService userService = new UserService();
				UserTypeService userTypeService = new UserTypeService();

				/* Case handled by try-catch block */
				User checkUser = userService.getByName(username).get();

				String name = checkUser.getUserLoginName();
				String pass = checkUser.getUserPassword();
				UserType type = checkUser.getUserType();
				String usertype = type.getUserTypeName();

				if (username.equals(name) && BCrypt.checkpw(password, pass)) {
					informationLabel.setText("CORRECT");

					/* Login successful, remember the logged in user */
					DatabaseUtils.currentUser = checkUser;

					if (usertype.equals("Admin")) {
						Parent userPanel = FXMLLoader.load(getClass().getResource("/views/AdminPanel.fxml"));
						Scene adminScene = new Scene(userPanel);

						Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
						window.setScene(adminScene);
						window.show();
					}

					if (usertype.equals("User")) {
						Parent userPanel = FXMLLoader.load(getClass().getResource("/views/UserPanel.fxml"));
						Scene adminScene = new Scene(userPanel);

						Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
						window.setScene(adminScene);
						window.show();
					}

					if (usertype.equals("Transport Company")) {
						Parent userPanel = FXMLLoader.load(getClass().getResource("/views/CompanyAddTripPanel.fxml"));
						Scene adminScene = new Scene(userPanel);

						Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
						window.setScene(adminScene);
						window.show();
					}

					if (usertype.equals("Distributor")) {
						Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorAddPanel.fxml"));
						Scene adminScene = new Scene(userPanel);

						Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
						window.setScene(adminScene);
						window.show();
					}

					if (usertype.equals("Cashier")) {
						Parent userPanel = FXMLLoader.load(getClass().getResource("/views/CashierSchedulePanel.fxml"));
						Scene adminScene = new Scene(userPanel);

						Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
						window.setScene(adminScene);
						window.show();
					}

					logger.info("User succesfully logged in.");
					NotificationUtils.init();
				} else {
					informationLabel.setText("INVALID USERNAME OR PASSWORD");
				}

			} catch (Exception e) {
				logger.error("Login failed.");
			}

		} else {
			informationLabel.setText("Please enter username and password!");
		}
	}
}
