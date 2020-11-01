package com.tuvarna.transportsystem.utils;

import java.util.Date;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.tuvarna.transportsystem.dao.TicketDAO;
import com.tuvarna.transportsystem.dao.UserDAO;
import com.tuvarna.transportsystem.dao.UserProfileDAO;
import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.PurchaseRestriction;
import com.tuvarna.transportsystem.entities.Role;
import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.TransportType;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.TripType;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.entities.UserType;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.PurchaseRestrictionService;
import com.tuvarna.transportsystem.services.RoleService;
import com.tuvarna.transportsystem.services.TicketService;
import com.tuvarna.transportsystem.services.TransportTypeService;
import com.tuvarna.transportsystem.services.TripService;
import com.tuvarna.transportsystem.services.TripTypeService;
import com.tuvarna.transportsystem.services.UserProfileService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.services.UserTypeService;

public class DatabaseUtils {

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
		locationService.save(new Location("Varna"));
		locationService.save(new Location("Sofia"));
		locationService.save(new Location("Plovdiv"));
		locationService.save(new Location("Veliko Tarnovo"));
		locationService.save(new Location("Sliven"));
		locationService.save(new Location("Gabrovo"));
		locationService.save(new Location("Burgas"));

		/*
		 * This table may not be needed but it was added so it can be easily expanded
		 * later on. For now there is only one user restriction which is buying more
		 * than a certain set of tickets. (they can't buy all available quantity for
		 * example)
		 */
		new PurchaseRestrictionService().save(new PurchaseRestriction("Overbuy"));

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
	}
	
	public static void testMappings() {
		
		UserProfile profile = new UserProfile(5.1, 5.2);
		new UserProfileService().save(profile);
		
		UserType type = new UserTypeService().getById(2); // prepopulated already
		Location location = (Location) new LocationService().getByName("Varna").get(0);
		
		UserService userService = new UserService();
		//String fullname = "bat gergi";
		//String username = "username";
		//String password = "parola";
		//User user = new User(fullname, username, password, profile, type, location);
		//System.out.println(user.getuserLocation().getLocationName());
		//userService.save(user);
		User user = userService.getById(1);
		

		/* Test trip creation */
		TripType tripType = new TripTypeService().getById(1);
		TransportType transportType = new TransportTypeService().getById(1);
		PurchaseRestriction restriction = new PurchaseRestrictionService().getById(1);
		
		/* works */
		//Trip trip = new Trip(tripType, location, location, new Date(), new Date(), 5, transportType, restriction, 5);
		//new TripService().save(trip);
		Trip trip = new TripService().getById(1);
		
		Ticket ticket = new Ticket(new Date(), user, trip);
		new TicketService().save(ticket);
	}
}
