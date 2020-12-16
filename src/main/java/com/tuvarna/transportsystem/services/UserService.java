package com.tuvarna.transportsystem.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;

import com.tuvarna.transportsystem.controllers.CompanyLoadStationsController;
import com.tuvarna.transportsystem.dao.UserDAO;
import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Request;
import com.tuvarna.transportsystem.entities.Role;
import com.tuvarna.transportsystem.entities.Route;
import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.TransportType;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.TripType;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.entities.UserType;
import com.tuvarna.transportsystem.utils.DatabaseUtils;
import com.tuvarna.transportsystem.utils.NotificationUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Toggle;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class UserService implements CrudService<User> {
	private UserDAO userDAO;

	public UserService() {
		this.userDAO = new UserDAO();
	}

	public String adminEditHonorariumProcessing(String validationOutput, String username, String honorarium,
			String rating) {
		if (!validationOutput.equals("Success")) {
			return validationOutput; // return error message
		}

		boolean isChanged = false;

		User user = this.getByName(username).get();
		UserProfile profile = user.getUserProfile();

		boolean ratingNull = rating == null || rating.equals(null) || rating.equals("");
		boolean honorariumNull = honorarium == null || honorarium.equals(null) || honorarium.equals("");

		if (ratingNull) {
			double honorariumValue = Double.parseDouble(honorarium.trim());

			/*
			 * Admin wants to change the horarium only but keep the rating the same. Check
			 * if the values differ
			 */
			if (profile.getUserProfileHonorarium() != honorariumValue) {
				profile.setUserProfileHonorarium(honorariumValue);
				this.updateUserProfile(user, profile);

				return "Successfully updated honorarium.";
			}

			return "New value matches old value for honorarium.";
		} else if (honorariumNull) {
			double ratingValue = Double.parseDouble(rating.trim());

			if (profile.getUserProfileRating() != ratingValue) {
				profile.setUserProfileRating(ratingValue);
				this.updateUserProfile(user, profile);

				return "Successfully updated rating";
			}

			return "New value matches old value for rating.";
		} else if (!(ratingNull) && !(honorariumNull)) {
			/* Both values should be changed */
			double honorariumValue = Double.parseDouble(honorarium.trim());
			double ratingValue = Double.parseDouble(rating.trim());

			boolean updatedHonorarium = false;
			boolean updatedRating = false;

			if (profile.getUserProfileHonorarium() != honorariumValue) {
				profile.setUserProfileHonorarium(honorariumValue);
				updatedHonorarium = true;
			}

			if (profile.getUserProfileRating() != ratingValue) {
				profile.setUserProfileRating(ratingValue);
				updatedRating = true;
			}

			if (updatedHonorarium || updatedRating) {
				this.updateUserProfile(user, profile);
			}

			if (updatedHonorarium && updatedRating) {
				return "Successfully updated both values.";
			}

			if (updatedHonorarium) {
				return "Updated honorarium. Rating is the same value as before.";
			}

			if (updatedRating) {
				return "Updated rating. Honorarium is the same value as before.";
			}
		}
		return "Edit failed.";
	}

	public String adminEditHonorariumValidation(String username, String honorarium, String rating) {

		if (username.equals(null) || username == null || username.equals("")) {
			return "No username entered.";
		}

		if (!this.getByName(username).isPresent()) {
			return "User not found.";
		}

		boolean honorariumNull = false;
		boolean ratingNull = false;

		if (honorarium.equals(null) || honorarium == null || honorarium.equals("")) {
			honorariumNull = true;

			if (rating.equals(null) || rating == null || rating.equals("")) {
				return "No changes to be made."; // both null
			}
		}

		if (rating.equals(null) || rating == null || rating.equals("")) {
			ratingNull = true;
		}

		if (!honorariumNull) {
			try {
				double tryParse = Double.parseDouble(honorarium);
			} catch (Exception e) {
				return "Invalid value for honorarium.";
			}
		}

		if (!ratingNull) {
			try {
				double tryParse = Double.parseDouble(rating);
			} catch (Exception e) {
				return "Invalid value for rating.";
			}
		}

		return "Success";
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
			return "/views/CashierSchedulePanel.fxml";
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

	public ObservableList<Trip> getScheduleForCashier(User cashier) {
		ObservableList<Trip> tripList = FXCollections.observableArrayList();
		TripService tripService = new TripService();

		/*
		 * Cashier accesses trips if they are the cashier assigned for it (exists in
		 * TripCashier table)
		 */
		List<Trip> eList = tripService.getAll();
		for (Trip ent : eList) {
			if (ent.getCashiers().contains(cashier)) {
				tripList.add(ent);
			}
		}
		return tripList;
	}

	public ObservableList<String> getDepLocationsForCashierBySelectedRow(User cashier, Trip trip) {
		ObservableList<String> departureLocations = FXCollections.observableArrayList();

		if (trip.equals(null)) {
			departureLocations.add("No route selected.");
			return departureLocations;
		}

		int routeId = trip.getRoute().getRouteId();

		RouteService routeService = new RouteService();

		/*
		 * A cashier can sell a ticket departuring from the location they work in.
		 * That's why we filter it. Most likely it will return 1 result
		 */

		/*
		 * DEBUG, correct code is below List<Location> departureLocation =
		 * routeService.getAttachmentLocationsInRouteById(routeId);
		 */
		List<Location> departureLocation = routeService.getAttachmentLocationsInRouteById(routeId).stream()
				.filter(l -> l.getLocationName().equals(cashier.getUserLocation().getLocationName()))
				.collect(Collectors.toList());

		ObservableList<String> locationList = FXCollections.observableArrayList();
		departureLocation.forEach(l -> locationList.add(l.getLocationName()));
		locationList.add(trip.getRoute().getRouteDepartureLocation().getLocationName());

		return locationList;
	}

	public ObservableList<String> getArrLocationsForCashierBySelectedRow(User cashier, Trip trip) {
		ObservableList<String> locationList = FXCollections.observableArrayList();

		if (trip.equals(null)) {
			locationList.add("No route selected.");
			return locationList;
		}

		int routeId = trip.getRoute().getRouteId();

		RouteService routeService = new RouteService();

		/*
		 * The arrival location cannot be the same as the current location of the
		 * cashier, so fetch everything else
		 */
		List<Location> departureLocation = routeService.getAttachmentLocationsInRouteById(routeId).stream()
				.filter(l -> !(l.getLocationName().equals(cashier.getUserLocation().getLocationName())))
				.collect(Collectors.toList());

		departureLocation.add(trip.getRoute().getRouteArrivalLocation());
		departureLocation.forEach(l -> locationList.add(l.getLocationName()));

		return locationList;
	}

	public String cashierSellTicketValidation(Trip trip, String departureLocation, String arrivalLocation,
			String quantity, boolean customerIsGuest, boolean customerIsRegistered, String usernameSelected,
			String guestUserfullName) {

		if (trip == null) {
			return "Please select a trip.";
		}

		if (departureLocation == null || departureLocation.equals(null) || departureLocation.equals("")) {
			return "Please select a departure location.";
		}

		if (arrivalLocation == null || arrivalLocation.equals(null) || arrivalLocation.equals("")) {
			return "Please select an arrival location.";
		}

		if (quantity == null || quantity.equals(null) || quantity.equals("")) {
			return "Please select ticket quantity.";
		}

		if (!(customerIsGuest) && !(customerIsRegistered)) {
			return "Please select if the customer is a guest or is registered.";
		}

		if (customerIsRegistered) {
			if (usernameSelected == null || usernameSelected.equals(null) || usernameSelected.equals("")) {
				return "Please select a customer from the dropdown menu.";
			}

			if (!this.getByName(usernameSelected).isPresent()) {
				return "User not found in database.";
			}
		} else if (customerIsGuest) {
			if (guestUserfullName == null || guestUserfullName.equals(null) || guestUserfullName.equals("")) {
				return "Please enter the name of the customer.";
			}
		}

		return "Success";
	}

	public String cashierSellTicketProcessing(Trip trip, String departureLocation, String arrivalLocation,
			String quantity, boolean customerIsGuest, boolean customerIsRegistered, String usernameSelected,
			String guestUserFullName) {

		String constraintValidationResult = this.cashierSellTicketValidation(trip, departureLocation, arrivalLocation,
				quantity, customerIsGuest, customerIsRegistered, usernameSelected, guestUserFullName);
		if (!constraintValidationResult.equals("Success")) {
			return constraintValidationResult; // constraint failure
		}

		User customer = null;

		if (customerIsRegistered) {
			customer = this.getByName(usernameSelected).get();
		} else if (customerIsGuest) {

			UserProfileService userProfileService = new UserProfileService();
			UserProfile userProfile = new UserProfile();
			userProfileService.save(userProfile);

			Location userLocation = new LocationService().getByName(departureLocation).get();

			customer = new User(guestUserFullName, DatabaseUtils.generateUserName(guestUserFullName),
					DatabaseUtils.generatePassword(), userProfile, DatabaseUtils.USERTYPE_USER, userLocation);
		}

		int ticketsToPurchase = Integer.parseInt(quantity);

		LocationService locationService = new LocationService();
		Location departureLocationInstance = locationService.getByName(departureLocation).get();
		Location arrivalLocationInstance = locationService.getByName(arrivalLocation).get();

		TripService tripService = new TripService();
		tripService.updateTripTicketAvailability(trip, trip.getTripTicketAvailability() - ticketsToPurchase);
		// logger.info("Updated tickets availability for trip.");

		TicketService ticketService = new TicketService();
		Ticket ticket = new Ticket(new Date(System.currentTimeMillis()), trip, departureLocationInstance,
				arrivalLocationInstance);
		ticketService.save(ticket);
		// logger.info("Ticket succesfully created.");

		this.addTicket(customer, ticket);
		// logger.info("Inserted into UsersTicket table.");

		// For every 5 purchased tickets, the user gains a rating of 0.2
		if (DatabaseUtils.currentUser.getTickets().size() % 5 == 0) {
			new UserProfileService().increaseRating(DatabaseUtils.currentUser.getUserProfile(), 0.2);
			// logger.info("Cashier rating increased by 0.2");
		}

		User company = this.getUserByTripId(trip.getTripId()).get();

		/*
		 * Iterate through the tickets, check if the trip they belong to matches the
		 * user id of this company's id. Basically, get all tickets sold by this
		 * company.
		 */
		List<Ticket> ticketsSoldByCompany = ticketService.getAll().stream()
				.filter(t -> this.getUserByTripId(t.getTrip().getTripId()).get().getUserId() == company.getUserId())
				.collect(Collectors.toList());

		if (ticketsSoldByCompany.size() % 5 == 0) {
			new UserProfileService().increaseRating(company.getUserProfile(), 0.1);
			// logger.info("Company rating increased by 0.1");
		}

		// informationLabel.setText("You sold a ticket.");

		return "Success";
	}

	public ObservableList<Ticket> cashierGetTicketsSold(User cashier) {
		ObservableList<Ticket> ticketList = FXCollections.observableArrayList();

		TicketService ticketService = new TicketService();

		List<Ticket> allTickets = ticketService.getAll();

		for (Ticket ticket : allTickets) {
			if (ticket.getTrip().getCashiers().contains(cashier)) {
				ticketList.add(ticket);
			}
		}

		return ticketList;
	}

	public String companyAddTripValidation(String ticketsAvailability, String seatsCapacity, String duration,
			String price, TextField departureDate, TextField arrivalDate, Object maxTicketsPerUser,
			String hourOfDeparture, String departureLocation, String arrivalLocation, Toggle selectedTripType,
			Toggle selectedBusType) throws ParseException {

		Pattern pattern = Pattern.compile("^\\d+$");

		/*
		 * Validate the text fields. In this case the pattern should be only a full
		 * number between 0-int.maxvalue
		 */
		if (!pattern.matcher(ticketsAvailability.trim()).matches()) {
			return "Invalid quantity.";
		}

		if (maxTicketsPerUser == null) {
			return "Invalid quantity.";
		}

		if (!pattern.matcher(maxTicketsPerUser.toString().trim()).matches()) {
			return "Invalid quantity.";
		}

		if (!pattern.matcher(seatsCapacity.trim()).matches()) {
			return "Invalid seats capacity.";
		}

		if (!pattern.matcher(duration.trim()).matches()) {
			return "Invalid duration!";
		}

		try {
			double tryParse = Double.parseDouble(price);
		} catch (Exception e) {
			return "Invalid price.";
		}

		if (hourOfDeparture == null || hourOfDeparture.equals(null) || hourOfDeparture.equals("")) {
			return "Invalid hour of departure.";
		}

		String departure = departureDate.getText();

		if (departure.equals(null) || departure.equals("") || departure == null) {
			return "Invalid departure date.";
		}

		// DateFormat formatDepartureDate = new SimpleDateFormat("MM/dd/yyyy");

		String dateFormatPattern = new SimpleDateFormat().toLocalizedPattern().split(" ")[0];
		DateFormat formatDepartureDate = new SimpleDateFormat(dateFormatPattern);
		Date dateDeparture = formatDepartureDate.parse(departure);

		// arrival date
		String arrival = arrivalDate.getText();

		if (arrival.equals(null) || arrival.equals("") || arrival == null) {
			return "Invalid arrival date.";
		}
		// DateFormat formatArrivalDate = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat formatArrivalDate = new SimpleDateFormat(dateFormatPattern);
		Date dateArrival = formatArrivalDate.parse(arrival);
		// SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH).parse(arrival);

		/* Date validation */
		if (dateDeparture.after(dateArrival) || dateDeparture.before(new Date(System.currentTimeMillis()))
				|| dateArrival.before(new Date(System.currentTimeMillis()))) {
			return "Invalid interval!";
		}

		if (departureLocation == null || departureLocation.equals(null) || departureLocation.equals("")) {
			return "Invalid departure location!";
		}

		if (arrivalLocation == null || arrivalLocation.equals(null) || arrivalLocation.equals("")) {
			return "Invalid arrival location!";
		}

		if (departureLocation.equals(arrivalLocation)) {
			return "Departure and arrival locations cannot match.";
		}

		if (selectedBusType == null) {
			return "Please select bus type (Regular or Big bus)!";
		}

		if (selectedTripType == null) {
			return "Please select trip type (Normal or Express)!";
		}

		return "Success";
	}

	public String companyAddTripProcessing(Route routeCreated, String ticketsAvailability, String seatsCapacity,
			String duration, String price, TextField departureDate, TextField arrivalDate, Object maxTicketsPerUser,
			String hourOfDeparture, String departureLocation, String arrivalLocation, Toggle selectedTripType,
			Toggle selectedBusType) throws ParseException {

		String constraintCheck = this.companyAddTripValidation(ticketsAvailability, seatsCapacity, duration, price,
				departureDate, arrivalDate, maxTicketsPerUser, hourOfDeparture, departureLocation, arrivalLocation,
				selectedTripType, selectedBusType);

		if (!constraintCheck.equals("Success")) {
			return constraintCheck; // constraint failure, inform view
		}

		String transportType = selectedBusType.toString().substring(selectedBusType.toString().indexOf('\'') + 1,
				selectedBusType.toString().lastIndexOf('\''));
		String tripType = selectedTripType.toString().substring(selectedTripType.toString().indexOf('\'') + 1,
				selectedTripType.toString().lastIndexOf('\''));

		TransportType transportTypeInstance = new TransportTypeService().getByName(transportType).get();
		TripType tripTypeInstance = new TripTypeService().getByName(tripType).get();
		int tripDuration = Integer.parseInt(duration.trim());
		double tripPrice = Double.parseDouble(price.trim());
		int tripTicketAvailability = Integer.parseInt(ticketsAvailability.trim());
		int tripCapacity = Integer.parseInt(seatsCapacity.trim());
		int tripMaxTicketsPerUser = Integer.parseInt(maxTicketsPerUser.toString().trim());

		if (routeCreated == null) {
			return "Please add stations first.";
		}

		String departure = departureDate.getText();
		// DateFormat formatDepartureDate = new SimpleDateFormat("MM/dd/yyyy");

		String dateFormatPattern = new SimpleDateFormat().toLocalizedPattern().split(" ")[0];
		DateFormat formatDepartureDate = new SimpleDateFormat(dateFormatPattern);
		Date dateDeparture = formatDepartureDate.parse(departure);

		// arrival date
		String arrival = arrivalDate.getText();
		// DateFormat formatArrivalDate = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat formatArrivalDate = new SimpleDateFormat(dateFormatPattern);
		Date dateArrival = formatArrivalDate.parse(arrival);
		// SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH).parse(arrival);

		Trip newTrip = new Trip(tripTypeInstance, routeCreated, dateDeparture, dateArrival, tripCapacity,
				transportTypeInstance, tripMaxTicketsPerUser, tripTicketAvailability, tripPrice, tripDuration,
				hourOfDeparture);
		TripService tripService = new TripService();
		tripService.save(newTrip);
		// logger.info("Constraint validation passed, persisting new trip to
		// database.");

		/*
		 * In the UserTrip table a new entry will be added with the logged in user
		 * (owner in this case) and the newly created trip
		 */
		new UserService().addTrip(DatabaseUtils.currentUser, newTrip);
		// logger.info("Inserting to UsersTrip table (Associating transport company to
		// this trip.");

		/* Distributor gets a notification */
		NotificationUtils.generateNewTripNotification(newTrip);

		return "Success";
	}

	public Route createGlobalTrip(String departureLocation, String arrivalLocation) {
		String constraintCheck = this.companyAddAttachmentLocationsEndPointValidation(departureLocation,
				arrivalLocation);

		if (!constraintCheck.equals("Success")) {
			return null;
		}

		// creating the route
		LocationService locationService = new LocationService();
		Location locationDeparture = locationService.getByName(departureLocation).get();
		Location locationArrival = locationService.getByName(arrivalLocation).get();
		RouteService routeService = new RouteService();
		Route route = new Route(locationDeparture, locationArrival);

		routeService.save(route);

		return route;
	}

	public String companyAddAttachmentLocationsEndPointValidation(String departureLocation, String arrivalLocation) {
		if (departureLocation == null || departureLocation.equals(null) || departureLocation.equals("")) {
			return "Please select departure station!";
		}

		if (arrivalLocation == null || arrivalLocation.equals(null) || arrivalLocation.equals("")) {
			return "Please select arrival station!";
		}

		return "Success";
	}

	public void companyAddTripHandleAttachmentLocations(String departureLocation, String arrivalLocation, Route route) {
		try {
			Stage stage = new Stage();
			FXMLLoader userPanel = new FXMLLoader(getClass().getResource("/views/CompanyLoadStations.fxml"));
			DialogPane root = (DialogPane) userPanel.load();

			// sending the route and stations to other controller
			CompanyLoadStationsController controller = (CompanyLoadStationsController) userPanel.getController();
			controller.getRouteLocations(arrivalLocation, departureLocation, route);

			Scene adminScene = new Scene(root);
			stage.setScene(adminScene);
			stage.setTitle("Transport Company");
			stage.showAndWait();

		} catch (Exception e) {
			System.out.println("Problem");

		}
	}

	public String companyAddAttachmentLocationsProcessing(List<String> locations, List<String> hours, Route route) {
		String constraintCheck = this.companyAddAttachmentLocationsValidation(locations, hours, route);
		if (!constraintCheck.equals("Success")) {
			return constraintCheck;
		}

		RouteService routeService = new RouteService();
		LocationService locationService = new LocationService();

		List<String> nonNullLocations = locations.stream().filter(l -> l != null).collect(Collectors.toList());
		List<String> nonNullHours = hours.stream().filter(h -> h != null).collect(Collectors.toList());

		for (int i = 0; i < nonNullLocations.size(); i++) {
			Location location = locationService.getByName(nonNullLocations.get(i)).get();
			routeService.addAttachmentLocation(route, location, nonNullHours.get(i));
		}

		return "Success";
	}

	public String companyAddAttachmentLocationsValidation(List<String> locations, List<String> hours, Route route) {
		RouteService routeService = new RouteService();

		// JavaFX sends all choice boxes values (they can be null) and we have to filter
		// and check if they are nulls
		if (locations.stream().filter(l -> l != null).collect(Collectors.toList()).size() == 0) {
			return "No locations were selected.";
		}

		if (hours.stream().filter(l -> l != null).collect(Collectors.toList()).size() == 0) {
			return "No times were selected.";
		}
		
		List<String> nonNullLocations = locations.stream().filter(l -> l != null).collect(Collectors.toList());
		List<String> nonNullHours = hours.stream().filter(h -> h != null).collect(Collectors.toList());

		if (locations.size() != hours.size()) {
			return "Constraint mismatch.";
		}

		if (locations.size() == 0) {
			return "No locations have been added. Cannot proceed.";
		}

		int previousHour = 0;
		for (int i = 0; i < nonNullLocations.size(); i++) {
			String currentLocation = nonNullLocations.get(i);
			LocationService locationService = new LocationService();
			Location locationStation = locationService.getByName(currentLocation).get();
			String time = nonNullHours.get(i);

			if (i == 0) {
				previousHour = Integer.parseInt(time.split(":")[0]);
			}

			if (locationStation.getLocationName().equals(route.getRouteDepartureLocation().getLocationName())
					|| locationStation.getLocationName().equals(route.getRouteArrivalLocation().getLocationName())) {
				return "Station " + i + " matches with arrival or departure location.";
			}

			// following stations' times must be bigger than the first
			if (i != 0) {
				int currentHour = Integer.parseInt(hours.get(i).split(":")[0]);
				
				if (currentHour <= previousHour) {
					return "Following arrival time must be larger than the previous one.";
				}
				previousHour = currentHour;
				
			}

			if (routeService.getAttachmentLocationsInRouteById(route.getRouteId()).contains(locationStation)) {
				return "Station already exists as an attachment location.";
			}
		}

		return "Success";
	}

	
	public String companyAcceptRequestValidation(Request request) {
		if (request == null) {
			return "Please select request from table!";
		}
		
		if (request.getStatus().equals(DatabaseUtils.REQUEST_STATUSREJECTED)) {
			return "Cannot accept a previously rejected request.";
		}
		
		if (request.getStatus().equals(DatabaseUtils.REQUEST_STATUSACCEPTED)) {
			return "Request already accepted.";
		}

		return "Success";
	}
	
	public String companyAcceptRequestProcessing(Request request) {
		String constraintCheck = this.companyAcceptRequestValidation(request);
		if (!constraintCheck.equals("Success")) {
			return constraintCheck;
		}
		
		int tickets = request.getTrip().getTripTicketAvailability();
		int requestedTickets = request.getTicketsQuantity();
		int newTickets = tickets + requestedTickets;

		Trip trip = request.getTrip();
		TripService tripService = new TripService();
		tripService.updateTripTicketAvailability(trip, newTickets);
		//logger.info("Request accepted: updating tickets availability for the trip.");

		RequestService requestService = new RequestService();
		requestService.updateStatus(request, DatabaseUtils.REQUEST_STATUSACCEPTED);
		//logger.info("Request was accepted.");

		// refresh table
		//requestCompanyTable.setItems(getRequests());
		//informationLabel.setText("Request was accepted!");
		
		return "Success";
	}
	
	public String companyRejectRequestValidation(Request request) {
		if (request == null) {
			return "Please select request from table!";
		}
		
		if (request.getStatus().equals(DatabaseUtils.REQUEST_STATUSACCEPTED)) {
			return "Cannot accept a previously accepted request.";
		}
		
		if (request.getStatus().equals(DatabaseUtils.REQUEST_STATUSREJECTED)) {
			return "Request already rejected.";
		}

		return "Success";
	}
	
	public String companyRejectRequestProcessing(Request request) {
		String constraintCheck = this.companyRejectRequestValidation(request);
		
		if (!constraintCheck.equals("Success")) {
			return constraintCheck;
		}
		
		RequestService requestService = new RequestService();
		requestService.updateStatus(request, DatabaseUtils.REQUEST_STATUSREJECTED);
		//logger.info("Request status updated: REJECTED");
		
		return "Success";
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
