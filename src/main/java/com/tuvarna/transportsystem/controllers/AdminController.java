package com.tuvarna.transportsystem.controllers;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
	ObservableList locationList = FXCollections.observableArrayList();
	ObservableList searchCriteriaList = FXCollections.observableArrayList();

	private static final Logger logger = LogManager.getLogger(AdminController.class.getName());
	private UserService userService;

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
		userService = new UserService();

		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured.");

		//loadLocation();
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

		companyLocationChoiceBox.setItems(getLocation());
		logger.info("Loaded table, locations and search criteria.");
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
		String constraintCheck = userService.adminAddUserValidation(nameCreationField.getText(), company.isSelected(),
				distributor.isSelected(), companyLocationChoiceBox.getSelectionModel().getSelectedItem());

		String addUserProcessed = userService.adminAddUserProcessing(constraintCheck, nameCreationField.getText(),
				company.isSelected(), distributor.isSelected(),
				companyLocationChoiceBox.getSelectionModel().getSelectedItem());

		if (!addUserProcessed.equals("Success")) {
			informationLabel.setText(addUserProcessed);
		}
	}

	public void searchButtonOnAction(javafx.event.ActionEvent event) throws IOException {
		String constraintCheck = userService.adminSearchValidation(searchField.getText(),
				searchCriteriaChoiceBox.getSelectionModel().getSelectedItem());

		if (!constraintCheck.equals("Success")) {
			informationLabel.setText(constraintCheck);
			return;
		}
		
		// clear error message
		informationLabel.setText("");

		ObservableList<User> userList = FXCollections.observableArrayList();
		userList.addAll(userService.adminSearchProcessing(constraintCheck, searchField.getText(),
				searchCriteriaChoiceBox.getSelectionModel().getSelectedItem()));

		usersFoundTable.setItems(userList);
		logger.info("Search results returned.");

	}

	public void deleteButtonOnAction(javafx.event.ActionEvent event) throws IOException {		
		User userForDeletion = usersFoundTable.getSelectionModel().getSelectedItem();
		String result = userService.adminDeleteProcessing(usersFoundTable.getSelectionModel().getSelectedItem());
		
		informationLabel.setText(result); // success or error could be the outcome, both printed
		
		if (result.equals("User successfully deleted.")) {
			usersFoundTable.getItems().removeIf(u -> u.getUserId() == userForDeletion.getUserId()); // remove from table (refresh)
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
