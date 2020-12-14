package com.tuvarna.transportsystem.controllers;

import javafx.beans.property.SimpleIntegerProperty;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.entities.UserType;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.UserProfileService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AdminController implements Initializable {
	ObservableList locationList = FXCollections.observableArrayList();
	ObservableList searchCriteriaList = FXCollections.observableArrayList();
	
	private static final Logger logger = LogManager.getLogger(AdminController.class.getName());

	@FXML
	private RadioButton distributor;

	@FXML
	private RadioButton company;

	@FXML
	private TextField nameCreationField;

	@FXML
	private Button addButton;

	@FXML
	ToggleGroup radioButtonAdmin;

	@FXML
	private ChoiceBox<String> companyLocationChoiceBox;

	@FXML
	private Label informationLabel;

	@FXML
	private TextField searchField;

	@FXML
	private Button searchButton;

	@FXML
	private Button deleteButton;

	@FXML
	private ChoiceBox<String> searchCriteriaChoiceBox;

	@FXML
	private TableView<User> usersFoundTable;

	@FXML
	private TableColumn<User, Integer> userId;

	@FXML
	private TableColumn<User, String> userFullName;

	@FXML
	private TableColumn<User, String> userNameFound;

	@FXML
	private TableColumn<User, String> userLocation;

	@FXML
	private TableColumn<User, String> userType;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");
		
		loadLocation();
		loadSearchCriteria();

		/* Integers must be ObservableItem<Integer> for JavaFX table */
		userId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getUserId()).asObject());

		userFullName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
						return new SimpleStringProperty(param.getValue().getUserFullName());
					}
				});

		userNameFound.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
						return new SimpleStringProperty(param.getValue().getUserLoginName());
					}
				});

		userLocation.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
						return new SimpleStringProperty(param.getValue().getUserLocation().getLocationName());
					}
				});

		userType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
						return new SimpleStringProperty(param.getValue().getUserType().getUserTypeName());
					}
				});
		
		logger.info("Loaded table, locations and search criteria.");
	}

	public void loadLocation() {
		locationList.removeAll(locationList);
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
		locationList.addAll(city_01, city_02, city_03, city_04, city_05, city_06, city_07, city_08, city_09, city_10, city_11,
				city_12, city_13, city_14, city_15, city_16, city_17, city_18, city_19, city_20,city_21);
		companyLocationChoiceBox.getItems().addAll(locationList);

	}

	public void loadSearchCriteria() {
		searchCriteriaList.removeAll(searchCriteriaList);
		String name = "Search by user name";
		String location = "Search by location";
		String type = "Search by user type";
		String fullName = "Search by full name";

		searchCriteriaList.addAll(name, fullName, location, type);
		searchCriteriaChoiceBox.getItems().addAll(searchCriteriaList);
		searchCriteriaChoiceBox.getSelectionModel().selectFirst();
	}
	
	public void addButtonOnAction(javafx.event.ActionEvent event) throws IOException {
		if (!nameCreationField.getText().isEmpty()) {
			UserService userService = new UserService();
			UserType userType;

			if (distributor.isSelected()) {
				userType = DatabaseUtils.USERTYPE_DISTRIBUTOR;
			} else if (company.isSelected()) {
				userType = DatabaseUtils.USERTYPE_COMPANY;
			} else {
				informationLabel.setText("Please specify the user type.");
				return;
			}

			/* Add null check even though it is 100% sure that it will be in the database */
			LocationService locationService = new LocationService();

			if (companyLocationChoiceBox.getSelectionModel().getSelectedIndex() < 0) {
				informationLabel.setText("Please select a location.");
				return;
			}

			String locationName = companyLocationChoiceBox.getSelectionModel().getSelectedItem().toString();

			if (!locationService.getByName(locationName).isPresent()) {
				logger.error("Location not present in database.");
				return;
			}

			Location location = (Location) new LocationService().getByName(locationName).get();

			/* Create a unique User Profile associated with this user */
			UserProfileService userProfileService = new UserProfileService();
			UserProfile profile = new UserProfile();
			userProfileService.save(profile);

			String username = DatabaseUtils.generateUserName(nameCreationField.getText());
			String password = DatabaseUtils.generatePassword();

			User user = new User(nameCreationField.getText(), username, password, profile, userType, location);

			userService.save(user);
			userService.addRole(user, DatabaseUtils.ROLE_USER);

			StringBuilder outputString = new StringBuilder();
			outputString.append(" Username: ").append(username).append("\n Password: ").append(password);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User succesfully created.");
			alert.setHeaderText("Please provide login credentials to the user!");
			alert.setContentText(outputString.toString());

			alert.showAndWait();
			logger.info("Successfully created user and persisted to database.");
		} else {
			informationLabel.setText("Please provide a fullname for the user you wish to create.");
		}
	}

	

	public void searchButtonOnAction(javafx.event.ActionEvent event) throws IOException {
		if (searchField.getText().isEmpty()) {
			informationLabel.setText("Please enter keyword to search.");
			return;
		}

		String keyword = searchField.getText().trim();
		UserService userService = new UserService();

		String selectedCriteria = searchCriteriaChoiceBox.getSelectionModel().getSelectedItem();

		if (selectedCriteria.equals("Search by user name")) {
			if (!userService.getByName(keyword).isPresent()) {
				informationLabel.setText("User not found in database.");
				return;
			}

			User user = userService.getByName(keyword).get();

			ObservableList<User> userList = FXCollections.observableArrayList();
			userList.add(user);

			usersFoundTable.setItems(userList);
		} else if (selectedCriteria.equals("Search by full name")) {
			if (userService.getByFullName(keyword).isEmpty()) {
				informationLabel.setText("No users found.");
				return;
			}

			List<User> usersFound = userService.getByFullName(keyword);

			ObservableList<User> userList = FXCollections.observableArrayList();
			userList.addAll(usersFound);

			usersFoundTable.setItems(userList);

		} else if (selectedCriteria.equals("Search by location")) {
			if (userService.getByUserLocation(keyword).isEmpty()) {
				informationLabel.setText("No users found.");
				return;
			}
			
			List<User> usersFound = userService.getByUserLocation(keyword);

			ObservableList<User> userList = FXCollections.observableArrayList();
			userList.addAll(usersFound);

			usersFoundTable.setItems(userList);
		} else if (selectedCriteria.equals("Search by user type")) {
			if (userService.getByUserType(keyword).isEmpty()) {
				informationLabel.setText("No users found.");
				return;
			}
			
			List<User> usersFound = userService.getByUserType(keyword);

			ObservableList<User> userList = FXCollections.observableArrayList();
			userList.addAll(usersFound);

			usersFoundTable.setItems(userList);
		}
		
		logger.info("Search results returned.");
	}

	public void deleteButtonOnAction(javafx.event.ActionEvent event) throws IOException {
		if (usersFoundTable.getSelectionModel().getSelectedIndex() < 0) {
			informationLabel.setText("No user selected to delete");
			return;
		}

		User user = usersFoundTable.getSelectionModel().getSelectedItem();

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm deletion");
		alert.setHeaderText("Are you sure you would like to delete this user?");
		alert.setResizable(false);
		alert.setContentText("Please beaware that this user will be permanently deleted.");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			UserService userService = new UserService();
			
			if (DatabaseUtils.cascadeUserDeletion(user)) {
				usersFoundTable.getItems().removeIf(u -> u.getUserId() == user.getUserId());
				informationLabel.setText("User successfully deleted.");
			} else {
				informationLabel.setText("An error has occured while deleting user.");
			}		
		}
	}

	public void backToLogIn(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
		
		DatabaseUtils.currentUser = null;
		logger.info("User logged off.");
	}

	public void goToHonorarium(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/AdminHonorarium.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

		logger.info("User profile (rating, honorarium) view loaded.");
	}
}
