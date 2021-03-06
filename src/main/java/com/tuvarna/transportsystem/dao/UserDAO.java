package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Role;
import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.TripType;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.services.TripService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

@SuppressWarnings("unchecked")
public class UserDAO implements GenericDAOInterface<User> {
	private EntityManager entityManager;
	private static final Logger logger = LogManager.getLogger(UserDAO.class.getName());

	public UserDAO() {
		entityManager = DatabaseUtils.globalSession.getEntityManagerFactory().createEntityManager();
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured and UserDAO initialized.");
	}

	/*
	 * EntityTransaction controls transactions; what this function does is it
	 * accepts lambda functions like: entityManager -> entityManager.remove(user)
	 * but since a Transaction is needed to persist the info to the database; That's
	 * why this function has been written.
	 * 
	 * If it is successful it will commit to database and if not it will rollback to
	 * the last successful set of data in the database
	 */
	private void executeInsideTransaction(Consumer<EntityManager> action) {
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			action.accept(entityManager);
			tx.commit();
			logger.info("Transaction successfully executed.");
		} catch (RuntimeException e) {
			tx.rollback();
			logger.error("Transaction failed. Rollback occured.");
			throw e;
		}
	}

	/*
	 * Application: Get transport company's name and display it to the user
	 * schedule. Iterate through the UsersTrip joined table and look for a user
	 * binded with the trip id. One trip can belong to one user (the trip creator
	 * thus the transport company).
	 */
	public Optional<User> getUserByTripId(int tripId) {
		List<User> usersFound = this.getAll();

		TripService tripService = new TripService();

		if (!tripService.getById(tripId).isPresent()) {
			return null;
		}

		Trip trip = tripService.getById(tripId).get();

		/*
		 * If I call .contains of the list it doesn't work even if I override Trip's
		 * .equals method and hashcode..
		 */
		for (User user : usersFound) {
			for (Trip currentTrip : user.getTrips()) {
				if (currentTrip.getTripId() == trip.getTripId()) {
					return Optional.ofNullable(user);
				}
			}
		}
		return null;
	}

	/* Joined table functionality */
	public void addRole(User user, Role role) {
		user.getRoles().add(role);
		executeInsideTransaction(entityManager -> entityManager.merge(user));
	}

	public void addTrip(User user, Trip trip) {
		user.getTrips().add(trip);
		executeInsideTransaction(entityManager -> entityManager.merge(user));
	}

	public void addTicket(User user, Ticket ticket) {
		user.getTickets().add(ticket);
		executeInsideTransaction(entityManager -> entityManager.merge(user));
	}

	public void addCashierToTransportCompany(User company, User cashier) {
		company.getCashiers().add(cashier);
		executeInsideTransaction(entityManager -> entityManager.merge(company));
	}

	public void removeRole(User user, Role role) {
		if (user.getRoles().contains(role)) {
			user.getRoles().remove(role);
		}

		executeInsideTransaction(entityManager -> entityManager.merge(user));
	}

	public void removeTrip(User user, Trip trip) {
		if (user.getTrips().contains(trip)) {
			user.getTrips().remove(trip);
		}

		executeInsideTransaction(entityManager -> entityManager.merge(user));
	}

	public void removeTicket(User user, Ticket ticket) {
		if (user.getTickets().contains(ticket)) {
			user.getTickets().remove(ticket);
		}

		executeInsideTransaction(entityManager -> entityManager.merge(user));
	}

	public void removeCashierFromCompany(User company, User cashier) {
		if (company.getCashiers().contains(cashier)) {
			company.getCashiers().remove(cashier);
		}

		executeInsideTransaction(entityManager -> entityManager.merge(company));
	}

	public void updateLocation(User user, Location location) {
		user.setUserLocation(location);
		executeInsideTransaction(entityManager -> entityManager.merge(user));
	}

	public void updateUserProfile(User user, UserProfile profile) {
		user.setUserProfile(profile);
		executeInsideTransaction(entityManager -> entityManager.merge(user));
	}

	/*
	 * HQL looks for the entity name rather than the table name it was mapped for.
	 * So in this case the entity name User will be used as opposed to the table
	 * name of Users which is inituative.
	 */

	public List<User> getByFullName(String name) {
		return entityManager.createQuery("FROM User WHERE user_fullname = :name").setParameter("name", name)
				.getResultList();
	}

	/*
	 * Test if it works, AFAIK this does a cross join which can affected performance
	 * negatively. Change to inner join one day
	 */
	public List<User> getByUserType(String type) {
		return entityManager.createQuery(
				"SELECT u FROM User u, UserType ut WHERE u.userType = ut.userTypeId AND ut.userTypeName = :type")
				.setParameter("type", type).getResultList();
	}

	public List<User> getByUserProfileId(int profileId) {
		return entityManager.createQuery("FROM User WHERE userprofile_id = :id").setParameter("id", profileId)
				.getResultList();
	}

	/* Test */
	public List<User> getByUserLocation(String location) {
		return entityManager.createQuery(
				"SELECT u FROM User u, Location l WHERE u.userLocation = l.locationId AND l.locationName = :location")
				.setParameter("location", location).getResultList();
	}

	@Override
	public Optional<User> getById(int id) {
		/*
		 * .getSingleResult() throws exception if value is null, we want a returnable
		 * null value
		 */
		return Optional.ofNullable((User) entityManager.createQuery("FROM User WHERE user_id = :id")
				.setParameter("id", id).getResultList().stream().findFirst().orElse(null));
	}

	@Override
	public Optional<User> getByName(String name) {
		/* Name refers to login name, separate function for full name */
		return Optional.ofNullable((User) entityManager.createQuery("FROM User WHERE user_loginname = :name")
				.setParameter("name", name).getResultList().stream().findFirst().orElse(null));
	}

	@Override
	public List<User> getAll() {
		return entityManager.createQuery("FROM User").getResultList();
	}

	@Override
	public void save(User user) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(user));
	}

	@Override
	public void updateName(User user, String newValue) {
		user.setUserFullName(newValue);
		executeInsideTransaction(entityManager -> entityManager.merge(user));
	}

	@Override
	public void deleteById(int id) {
		if (!this.getById(id).isPresent()) {
			return;
		}

		User user = this.getById(id).get();
		executeInsideTransaction(entityManager -> entityManager.remove(user));
	}

	@Override
	public void deleteByName(String name) {
		if (!this.getByName(name).isPresent()) {
			return;
		}

		User user = this.getByName(name).get();
		executeInsideTransaction(entityManager -> entityManager.remove(user));
	}

	@Deprecated
	@Override
	public void update(User user, String[] newValues) {
	}
}
