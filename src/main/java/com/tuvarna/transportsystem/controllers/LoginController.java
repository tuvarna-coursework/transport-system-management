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
	private UserService userService;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		userService = new UserService();
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

	public void loginButtonOnAction(javafx.event.ActionEvent event) throws IOException {	
			String viewToLoad = userService.handleLogin(usernameTextField.getText(), passwordTextField.getText());
			
			if (userService.userLoginInputIncorrect(viewToLoad)) {
				informationLabel.setText("Incorrect login credentials.");
				logger.info("Incorrect login credentials.");
				return;
			}
			
			// clear error messages
			informationLabel.setText("");

			Parent userPanel = FXMLLoader.load(getClass()
					.getResource(viewToLoad));
			Scene adminScene = new Scene(userPanel);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(adminScene);
			window.show();

			logger.info("Login success.");
	}
}
