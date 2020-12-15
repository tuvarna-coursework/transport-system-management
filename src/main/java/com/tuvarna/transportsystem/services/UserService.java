package com.tuvarna.transportsystem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

import com.tuvarna.transportsystem.dao.UserDAO;
import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Role;
import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.entities.UserType;
import com.tuvarna.transportsystem.utils.DatabaseUtils;
import com.tuvarna.transportsystem.utils.NotificationUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class UserService implements CrudService<User> {
	private UserDAO userDAO;

	public UserService() {
		this.userDAO = new UserDAO();
	}

	public String adminDeleteProcessing(User user) {
		if (user == null) {
			return "No user selected for deletion.";
		}

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm deletion");
		alert.setHeaderText("Are you sure you would like to delete this user?");
		alert.setResizable(false);
		alert.setContentText("Please beaware that this user will be permanently deleted.");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			UserService userService = new UserService();

			if (DatabaseUtils.cascadeUserDeletion(user)) {
				return "User successfully deleted.";
			} else {
				return "An error has occured while deleting user.";
			}
		}

		return "No actions taken."; // user didnt select ok
	}

	public List<User> adminSearchProcessing(String validationOutput, String keyword, String searchCriteria) {
		List<User> returnList = new ArrayList<>();

		if (searchCriteria.equals("Search by user name")) {
			returnList.add(this.getByName(keyword).get());
		} else if (searchCriteria.equals("Search by full name")) {
			returnList.addAll(this.getByFullName(keyword));
		} else if (searchCriteria.equals("Search by location")) {
			returnList.addAll(this.getByUserLocation(keyword));
		} else if (searchCriteria.equals("Search by user type")) {
			returnList.addAll(this.getByUserType(keyword));
		}

		return returnList;
	}

	public String adminSearchValidation(String keyword, String searchCriteria) {
		if (keyword == null || keyword.equals(null) || keyword.equals("")) {
			return "Please enter keyword to search.";
		}

		if (searchCriteria == null || searchCriteria.equals(null) || searchCriteria.equals("")) {
			return "Please select search criteria.";
		}

		if (searchCriteria.equals("Search by user name")) {
			if (!this.getByName(keyword).isPresent()) {
				return "User not found in database.";
			}
		} else if (searchCriteria.equals("Search by full name")) {
			if (this.getByFullName(keyword).isEmpty()) {
				return "No users found.";
			}
		} else if (searchCriteria.equals("Search by location")) {
			if (this.getByUserLocation(keyword).isEmpty()) {
				return "No users found.";
			}
		} else if (searchCriteria.equals("Search by user type")) {
			if (this.getByUserType(keyword).isEmpty()) {
				return "No users found.";
			}
		}

		return "Success";
	}

	public String adminAddUserProcessing(String validationOutput, String fullname, boolean companySelected,
			boolean distributorSelected, String userLocation) {

		// error occured with the constraint validation, show in FXML
		if (!this.adminAddUserValidation(fullname, companySelected, distributorSelected, userLocation)
				.equals("Success")) {
			return validationOutput;
		}

		// it is success, continue with the user creation

		LocationService locationService = new LocationService();

		if (!locationService.getByName(userLocation).isPresent()) {
			// logger.error("Location not present in database.");
			return "Location not present in database.";
		}

		Location location = (Location) new LocationService().getByName(userLocation).get();

		/* Create a unique User Profile associated with this user */
		UserProfileService userProfileService = new UserProfileService();
		UserProfile profile = new UserProfile();
		userProfileService.save(profile);

		String username = DatabaseUtils.generateUserName(fullname);
		String password = DatabaseUtils.generatePassword();

		UserType userType = null;

		if (companySelected) {
			userType = DatabaseUtils.USERTYPE_COMPANY;
		} else if (distributorSelected) {
			userType = DatabaseUtils.USERTYPE_DISTRIBUTOR;
		}

		User user = new User(fullname, username, password, profile, userType, location);

		this.save(user);
		this.addRole(user, DatabaseUtils.ROLE_USER);

		StringBuilder outputString = new StringBuilder();
		outputString.append(" Username: ").append(username).append("\n Password: ").append(password);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User succesfully created.");
		alert.setHeaderText("Please provide login credentials to the user!");
		alert.setContentText(outputString.toString());

		alert.showAndWait();
		// logger.info("Successfully created user and persisted to database.");

		return "Success";
	}

	public String adminAddUserValidation(String fullname, boolean companySelected, boolean distributorSelected,
			String location) {
		if (fullname.equals(null) || fullname == null || fullname.equals("")) {
			return "Please provide a fullname for the user you wish to create.";
		}

		UserType userType = null;

		if ((!companySelected) && (!distributorSelected)) {
			return "Please specify the user type.";
		}

		if (location == null) {
			return "Please select a location.";
		}

		return "Success";
	}

	public String processRegistration(String validationOutput, String fullname, String username, String password,
			String userLocation) {
		if (validationOutput.equals("Success")) {

			LocationService locationService = new LocationService();
			Location location = locationService.getByName(userLocation).get();

			UserProfile profile = new UserProfile(0.0, 0.0);
			new UserProfileService().save(profile);
			UserType type = DatabaseUtils.USERTYPE_USER;
			UserService userService = new UserService();
			User user = new User(fullname, username, password, profile, type, location);
			userService.save(user);
			// logger.info("Successfully persisted user to database.");

			userService.addRole(user, DatabaseUtils.ROLE_USER);
			// logger.info("Assigned role user.");

			// registration success and return logged in view
			DatabaseUtils.currentUser = user;
			return "/views/UserPanel.fxml";
		}

		return validationOutput; // returns error message to registration view and set information label
	}

	public String validateRegistration(String fullname, String username, String password, String location) {
		if (fullname.trim().length() > 40 || fullname.trim().length() < 5) {
			// informationLabel.setText("Invalid fullname. Name must be between 5 and 40
			// characters.");
			return "Invalid fullname. Name must be between 5 and 40 characters.";
		}

		if ((!Pattern.matches("^\\w+$", username)) || username.length() < 4 || username.length() > 20) {
			return "Invalid username. No spaces, special characters. Length: 4 - 20 characters.";
		}

		if (password.length() < 5 || password.length() > 20) {
			return "Invalid password. Length: 5 - 20 characters.";
		}

		if (location == null) {
			return "No location selected.";
		}

		LocationService locationService = new LocationService();

		if (!locationService.getByName(location).isPresent()) {
			// logger.error("Location not present in database.");
			return "Location not present in database.";
		}

		return "Success";
	}

	// if it redirects to login then there is a login error
	public boolean userLoginInputIncorrect(String output) {
		if (output.equals("/views/Login.fxml")) {
			return true;
		}
		return false;
	}

	// if login is successful return corresponding view to controller
	public String handleLogin(String username, String password) {
		if (validateLogin(username, password)) {
			return redirectLoginView(DatabaseUtils.currentUser);
		}

		return "/views/Login.fxml";
	}

	// return view to FXML controller
	public String redirectLoginView(User user) {
		String userType = user.getUserType().getUserTypeName();

		if (userType.equals("Admin")) {
			return "/views/AdminPanel.fxml";
		} else if (userType.equals("User")) {
			return "/views/UserPanel.fxml";
		} else if (userType.equals("Transport Company")) {
			return "/views/CompanyAddTripPanel.fxml";
		} else if (userType.equals("Distributor")) {
			return "/views/DistributorAddPanel.fxml";
		} else if (userType.equals("Cashier")) {
			return "/views/CashierSchedule.fxml";
		}

		// unknown type somehow
		return "/views/Login.fxml";
	}

	public boolean validateLogin(String username, String password) {
		if (username != null && username.trim().length() != 0 && password != null && password.length() != 0) {

			if (!this.getByName(username).isPresent()) {
				return false;
			}

			User userFound = this.getByName(username).get();

			// login success
			if (userFound.getUserLoginName().equals(username)
					&& BCrypt.checkpw(password, userFound.getUserPassword())) {
				DatabaseUtils.currentUser = userFound;
				return true;
			}
			return false;
		}

		return false;
	}

	public Optional<User> getUserByTripId(int tripId) {
		return userDAO.getUserByTripId(tripId);
	}

	/* Functionality for joined tables */
	public void addRole(User user, Role role) {
		userDAO.addRole(user, role);
	}

	public void addTrip(User user, Trip trip) {
		userDAO.addTrip(user, trip);
	}

	public void addTicket(User user, Ticket ticket) {
		userDAO.addTicket(user, ticket);
	}

	public void addCashierToTransportCompany(User company, User cashier) {
		userDAO.addCashierToTransportCompany(company, cashier);
	}

	public void removeRole(User user, Role role) {
		userDAO.removeRole(user, role);
	}

	public void removeTrip(User user, Trip trip) {
		userDAO.removeTrip(user, trip);
	}

	public void removeTicket(User user, Ticket ticket) {
		userDAO.removeTicket(user, ticket);
	}

	public void removeCashierFromTransportCompany(User company, User cashier) {
		userDAO.removeCashierFromCompany(company, cashier);
	}

	public void updateLocation(User user, Location location) {
		userDAO.updateLocation(user, location);
	}

	public void updateUserProfile(User user, UserProfile profile) {
		userDAO.updateUserProfile(user, profile);
	}

	public List<User> getByFullName(String name) {
		return userDAO.getByFullName(name);
	}

	public List<User> getByUserType(String type) {
		return userDAO.getByUserType(type);
	}

	public List<User> getByUserProfileId(int profileId) {
		return userDAO.getByUserProfileId(profileId);
	}

	public List<User> getByUserLocation(String location) {
		return userDAO.getByUserLocation(location);
	}

	@Override
	public Optional<User> getById(int id) {
		return userDAO.getById(id);
	}

	@Override
	public Optional<User> getByName(String name) {
		return userDAO.getByName(name);
	}

	@Override
	public List<User> getAll() {
		return userDAO.getAll();
	}

	@Override
	public void save(User user) {
		userDAO.save(user);
	}

	@Override
	public void updateName(User user, String newValue) {
		userDAO.updateName(user, newValue);
	}

	@Override
	public void deleteById(int id) {
		userDAO.deleteById(id);
	}

	@Override
	public void deleteByName(String name) {
		userDAO.deleteByName(name);
	}

	@Deprecated
	@Override
	public void update(User user, String[] newValues) {
	}
}
