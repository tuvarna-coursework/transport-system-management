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

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

	}

	public void Change(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/Register.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();


	}
	public void ChangeToSuccessLogin(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/UserPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();


	}


	public void loginButtonOnAction(javafx.event.ActionEvent event) throws IOException {
		/* IsBlank() available on Java 11+; written like this for backwards compatibility */
		if (usernameTextField.getText() != null && usernameTextField.getText().trim().length() != 0 && 
				passwordTextField.getText() != null && passwordTextField.getText().trim().length() != 0) {


			try{
				String username = usernameTextField.getText();
				String password = passwordTextField.getText();
				UserService userService = new UserService();
				User checkUserName= userService.getByName(username);
				User checkPassword= userService.getByName(username);

				String name = checkUserName.getUserLoginName();
				String pass = checkPassword.getUserPassword();


				if (username.equals(name) && BCrypt.checkpw(password, pass)) {
					informationLabel.setText("CORRECT");
					Parent userPanel = FXMLLoader.load(getClass().getResource("/views/UserPanel.fxml"));
					Scene adminScene = new Scene(userPanel);

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(adminScene);
					window.show();
				}
				else {
					informationLabel.setText("INVALID PASSWORD");
				}
				
			}catch (Exception e){
				informationLabel.setText("INVALID USERNAME");


			}

		} else {
			informationLabel.setText("Please enter username and password!");
		}
	}

	/*public void validateLogin() throws IOException {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		UserService userService = new UserService();
		User checkUserName= userService.getByLoginName(username);
		User checkPassword= userService.getByLoginName(username);



		String name=checkUserName.getUserLoginName();
		String pass=checkPassword.getUserPassword();

		if (username.equals(name)&&password.equals(pass)) {
			informationLabel.setText("CORRECT");
			ChangeToSuccessLogin();

		}
		else {
			informationLabel.setText("INVALID");
		}

	}*/

}
