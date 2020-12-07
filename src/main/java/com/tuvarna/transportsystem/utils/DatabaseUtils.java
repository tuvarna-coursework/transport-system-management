package com.tuvarna.transportsystem.utils;

import java.io.IOException;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Role;
import com.tuvarna.transportsystem.entities.Route;
import com.tuvarna.transportsystem.entities.TransportType;
import com.tuvarna.transportsystem.entities.TripType;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.entities.UserType;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.RoleService;
import com.tuvarna.transportsystem.services.RouteService;
import com.tuvarna.transportsystem.services.TransportTypeService;
import com.tuvarna.transportsystem.services.TripTypeService;
import com.tuvarna.transportsystem.services.UserProfileService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.services.UserTypeService;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DatabaseUtils {
	public static Session globalSession;

	public static User currentUser;

	public static Role ROLE_ADMIN;
	public static Role ROLE_USER;

	public static UserType USERTYPE_DISTRIBUTOR;
	public static UserType USERTYPE_CASHIER;
	public static UserType USERTYPE_USER;
	public static UserType USERTYPE_ADMIN;
	public static UserType USERTYPE_COMPANY;
	
	public static String REQUEST_STATUSPENDING = "PENDING";
	public static String REQUEST_STATUSREJECTED = "REJECTED";
	public static String REQUEST_STATUSACCEPTED = "ACCEPTED";

	public static void init() {
		globalSession = createSession();
		//populateAuxiliaryTables();
		initFields();
	}

	public static SessionFactory createSessionFactory() {
		SessionFactory factory;
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to initialize sessionFactory." + ex);
			throw new ExceptionInInitializerError(ex);
		}

		return factory;
	}

	public static Session createSession() {
		Session session;
		try {
			session = DatabaseUtils.createSessionFactory().openSession();
		} catch (Exception e) {
			System.err.println("Failed to initialize Session " + e);
			throw new ExceptionInInitializerError(e);
		}

		return session;
	}

	public static void populateAuxiliaryTables() {

		/*
		 * This function has the purpose to fill the tables which are hardcoded and do
		 * not depend on the user's actions. The tables affected are: Location),
		 * PurchaseRestriction, Role, TransportType, TripType, UserType
		 * 
		 * 
		 * 
		 * Locations can be changed only via admin. This ensures no mistakes happen
		 * since every company travels to specific destinations only and rarely add new
		 * locations. Ensures that tickets have a valid location as well.
		 */
		LocationService locationService = new LocationService();
		Location varna = new Location("Varna");
		Location sofia = new Location("Sofia");
		Location plovdiv = new Location("Plovdiv");
		Location turnovo = new Location("Veliko Turnovo");
		Location sliven = new Location("Sliven");
		Location gabrovo = new Location("Gabrovo");
		Location razgrad = new Location("Razgrad");
		Location shumen = new Location("Shumen");
		Location blagoevgrad = new Location("Blagoevgrad");
		Location burgas = new Location("Burgas");

		Location pleven = new Location("Pleven");
		Location omurtag = new Location("Omurtag");
		Location ruse = new Location("Ruse");
		Location dobrich = new Location("Dobrich");
		Location montana = new Location("Montana");
		Location vraca = new Location("Vraca");
		Location staraZagora= new Location("Stara Zagora");
		Location yambol= new Location("Yambol");
		Location pernik = new Location("Pernik");
		Location lovech = new Location("Lovech");
		Location turgovishte = new Location("Turgovishte");



		locationService.save(varna);
		locationService.save(sofia);
		locationService.save(plovdiv);
		locationService.save(turnovo);
		locationService.save(sliven);
		locationService.save(gabrovo);
		locationService.save(razgrad);
		locationService.save(shumen);
		locationService.save(blagoevgrad);
		locationService.save(burgas);
		//new locations
		locationService.save(pleven);
		locationService.save(omurtag);
		locationService.save(ruse);
		locationService.save(dobrich);
		locationService.save(montana);
		locationService.save(vraca);
		locationService.save(staraZagora);
		locationService.save(yambol);
		locationService.save(pernik);
		locationService.save(lovech);
		locationService.save(turgovishte);



		/*
		 * Authentication and authorisation is first and foremost distinguished between
		 * these two
		 */
		RoleService roleService = new RoleService();
		roleService.save(new Role("User"));
		roleService.save(new Role("Admin"));

		/* Vehicle type */
		TransportTypeService transportTypeService = new TransportTypeService();
		transportTypeService.save(new TransportType("Big bus"));
		transportTypeService.save(new TransportType("Van"));

		/* Express or normal trip */
		TripTypeService tripTypeService = new TripTypeService();
		tripTypeService.save(new TripType("Normal"));
		tripTypeService.save(new TripType("Express"));

		/* All the users that can use the program. Where user = retail customer */
		UserTypeService userTypeService = new UserTypeService();
		userTypeService.save(new UserType("Distributor"));
		userTypeService.save(new UserType("Cashier"));
		userTypeService.save(new UserType("User"));
		userTypeService.save(new UserType("Admin"));
		userTypeService.save(new UserType("Transport Company"));

		/*
		 * String userFullName, String userLoginName, String userPassword, UserProfile
		 * userProfile, UserType userType, Location userLocation
		 */

		UserProfileService userProfileService = new UserProfileService();
		UserProfile profile = new UserProfile();
		userProfileService.save(profile);
		UserService userService = new UserService();
		userService.save(new User("Gesha", "root", "root", profile, userTypeService.getByName("Admin").get(),
				locationService.getByName("Varna").get()));

		/*
		 * Add routes. Routes are inputted before the trip creation and during trip
		 * creation the company selects a route
		 */
		RouteService routeService = new RouteService();
		Route route1 = new Route(varna, sofia);
		Route route2 = new Route(sofia, varna);
		Route route3 = new Route(burgas, plovdiv);
		Route route4 = new Route(plovdiv, burgas);
		
		routeService.save(route1);
		routeService.save(route2);
		routeService.save(route3);
		routeService.save(route4);

		routeService.addAttachmentLocation(route1, turnovo, "03:15");
		routeService.addAttachmentLocation(route2, turnovo, "03:15");
		routeService.addAttachmentLocation(route3, sliven, "03:15");
		routeService.addAttachmentLocation(route4, sliven, "03:15");

		
	}

	/*
	 * Get from the database the roles and the usertypes since they will not be
	 * changed, but are used.
	 */
	public static void initFields() {
		/* Hard coded for debug */
		RoleService roleService = new RoleService();
		UserTypeService userTypeService = new UserTypeService();
		UserService userService = new UserService();
		//currentUser = userService.getById(7).get();

		try {
			ROLE_ADMIN = roleService.getByName("Admin").get();
			ROLE_USER = roleService.getByName("User").get();

			USERTYPE_DISTRIBUTOR = userTypeService.getByName("Distributor").get();
			USERTYPE_CASHIER = userTypeService.getByName("Cashier").get();
			USERTYPE_USER = userTypeService.getByName("User").get();
			USERTYPE_ADMIN = userTypeService.getByName("Admin").get();
			USERTYPE_COMPANY = userTypeService.getByName("Transport Company").get();
		} catch (Exception e) {
			System.out.println("Please run DatabaseUtils.populateAuxiliaryTables() before calling this function.");
		}
	}
	
	public static String generateUserName(String input) {
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

	
	
	public static String generatePassword() {
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
}
