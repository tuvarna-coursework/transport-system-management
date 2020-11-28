package com.tuvarna.transportsystem.utils;

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

		routeService.addAttachmentLocation(route1, turnovo);
		routeService.addAttachmentLocation(route2, turnovo);
		routeService.addAttachmentLocation(route3, sliven);
		routeService.addAttachmentLocation(route4, sliven);

		
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
		currentUser = userService.getById(1).get();

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

	@Deprecated
	public static void testMappings() {

		UserProfile profile = new UserProfile(5.1, 5.2);
		new UserProfileService().save(profile);

		UserType type = new UserTypeService().getById(2).get(); // prepopulated already
		Location location = (Location) new LocationService().getByName("Varna").get();

		UserService userService = new UserService();
		String fullname = "bat gergi";
		String username = "username";
		String password = "parola";
		// User user = new User(fullname, username, password, profile, type, location);

		// userService.save(user);

		User user = userService.getById(1).get();
		// user.addTicket(new TicketService().getById(1));

		userService.save(user);

		/*
		 * TripType tripType, Location tripDepartureLocation, Location
		 * tripArrivalLocation, Date tripDepartureDate, Date tripArrivalDate, int
		 * tripCapacity, TransportType tripTransportType, PurchaseRestriction
		 * tripPurchaseRestriction, int tripTicketAvailability
		 */
		/*
		 * TripType type = new TripTypeService().getById(1); Location location = new
		 * LocationService().getById(1); Location location2 = new
		 * LocationService().getById(2); TransportType transportType = new
		 * TransportTypeService().getById(1); PurchaseRestriction restriction = new
		 * PurchaseRestrictionService().getById(1);
		 * 
		 * Trip trip = new Trip (type, location, location2, new Date(), new Date(), 50,
		 * transportType, restriction, 5);
		 * 
		 * TripService service = new TripService(); service.save(trip);
		 * 
		 * 
		 * Ticket ticket = new Ticket(new Date(), trip);
		 * 
		 * new TicketService().save(ticket);
		 */

	}
}
