package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.UserProfileService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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

	@FXML
	private TableView<User> cashierTable;

	@FXML
	private TableColumn<User, Integer> cashierId;

	@FXML
	private TableColumn<User, String> cashierFullName;

	@FXML
	private TableColumn<User, String> cashierCompany;

	@FXML
	private TableColumn<User, String> cashierLocation;

	ObservableList list = FXCollections.observableArrayList();

	private static final Logger logger = LogManager.getLogger(DistributorAddCashierController.class.getName());

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");

		locationChoiceBox.setItems(getLocation());
		companyComboBox.setItems(getCompanies());
		logger.info("Available companies loaded.");

		cashierFullName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
						return new SimpleStringProperty(param.getValue().getUserFullName());
					}
				});

		cashierCompany.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
						if (param.getValue().getCompanies().isEmpty()) {
							return new SimpleStringProperty("TEST: None");
						}
						return new SimpleStringProperty(param.getValue().getCompanies().get(0).getUserFullName());
					}
				});

		cashierLocation.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
						return new SimpleStringProperty(param.getValue().getUserLocation().getLocationName());
					}
				});

		cashierId.setCellValueFactory(new PropertyValueFactory<User, Integer>("userId"));

		cashierTable.setItems(this.getCashiers());
		logger.info("Loaded cashiers and created table.");
	}

	public ObservableList<User> getCashiers() {
		ObservableList<User> userList = FXCollections.observableArrayList();
		UserService userService = new UserService();

		userService.getByUserType("Cashier").forEach(c -> userList.add(c));
		return userList;
	}

	public ObservableList<String> getCompanies() {
		ObservableList<String> userList = FXCollections.observableArrayList();
		UserService userService = new UserService();
		String type = "Transport Company";
		List<User> eList = userService.getByUserType(type);
		for (User ent : eList) {
			userList.add(ent.getUserFullName());
		}
		return userList;
	}

	private ObservableList<String> getLocation() {
		ObservableList<String> locationList = FXCollections.observableArrayList();
		LocationService locationService = new LocationService();
		/*
		 * Loading locations from data base.
		 */
		List<Location> eList = locationService.getAll();
		for (Location ent : eList) {
			locationList.add(ent.getLocationName());

		}
		return locationList;
	}

	public void goToNotifications(javafx.event.ActionEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/DistributorNotificationsPanel.fxml"));
		AnchorPane root = (AnchorPane) userPanel.load();
		Scene adminScene = new Scene(root);
		stage.setScene(adminScene);
		stage.setTitle("Transport Company");
		stage.showAndWait();
		
		logger.info("Notifications tab opened.");
	}

	public void goToRequest(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorRequestPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		logger.info("Requests tab opened.");
	}

	public void goToLogIn(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		DatabaseUtils.currentUser = null;
		logger.info("User logged out.");
	}

	public void goToSchedule(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorPanelSchedule.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
		
		logger.info("Schedule tab selected.");
	}

	public void createCashier(javafx.event.ActionEvent event) throws IOException {
		if (!fullnameTextField.getText().isEmpty()) {
			String fullname = fullnameTextField.getText().trim();
			UserService userService = new UserService();

			if (fullname.length() < 4 || fullname.length() > 30) {
				informationLabel.setText("Invalid fullname. Length: 4 - 20");
				return;
			}

			if (locationChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select a location!");
				return;
			}

			if (companyComboBox.getSelectionModel().getSelectedItem().isEmpty()) {
				informationLabel.setText("Please select a company!");
				return;
			}

			Location userLocation = new LocationService()
					.getByName(locationChoiceBox.getSelectionModel().getSelectedItem()).get();
			User company = userService.getByFullName(companyComboBox.getSelectionModel().getSelectedItem()).get(0); // only

			/* Create a unique User Profile associated with this user */
			UserProfileService userProfileService = new UserProfileService();
			UserProfile profile = new UserProfile();
			userProfileService.save(profile);

			String username = DatabaseUtils.generateUserName(fullname);
			String password = DatabaseUtils.generatePassword();

			User cashier = new User(fullname, username, password, profile, DatabaseUtils.USERTYPE_CASHIER,
					userLocation);

			userService.save(cashier);
			userService.addRole(cashier, DatabaseUtils.ROLE_USER);
			userService.addCashierToTransportCompany(company, cashier);
			logger.info("User successfully created. Assigned role 'user' and inserted into CompanyCashier table.");

			StringBuilder outputString = new StringBuilder();
			outputString.append(" Username: ").append(username).append("\n Password: ").append(password);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User succesfully created.");
			alert.setHeaderText("Please provide login credentials to the user!");
			alert.setContentText(outputString.toString());

			alert.showAndWait();

		} else {
			informationLabel.setText("Enter cashier full name!");
		}
	}

	public void refresh(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/DistributorAddPanel.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
		
		logger.info("Page refreshed.");
	}
}
