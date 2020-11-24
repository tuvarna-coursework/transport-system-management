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
		locationList.addAll(city_01, city_02, city_03, city_04, city_05, city_06, city_07, city_08, city_09, city_10,
				city_11);
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
				System.out.println("ERROR: Location not present in database.");
				return;
			}

			Location location = (Location) new LocationService().getByName(locationName).get();

			/* Create a unique User Profile associated with this user */
			UserProfileService userProfileService = new UserProfileService();
			UserProfile profile = new UserProfile();
			userProfileService.save(profile);

			String username = this.generateUserName(nameCreationField.getText());
			String password = this.generatePassword();

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
			userService.deleteById(user.getUserId());

			usersFoundTable.getItems().removeIf(u -> u.getUserId() == user.getUserId());

			informationLabel.setText("User successfully deleted.");
		}
	}

	public void backToLogIn(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}

	public void goToHonorarium(javafx.event.ActionEvent event) throws IOException {
		Parent userPanel = FXMLLoader.load(getClass().getResource("/views/AdminHonorarium.fxml"));
		Scene adminScene = new Scene(userPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

	}

}
