package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Role;
import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.TripType;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

@SuppressWarnings("unchecked")
public class UserDAO implements GenericDAOInterface<User> {
	private EntityManager entityManager;

	public UserDAO() {
		entityManager = DatabaseUtils.globalSession.getEntityManagerFactory().createEntityManager();
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
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		}
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

	public void removeRole(User user, Role role) {
		if (user.getRoles().contains(role)) {
			user.getRoles().remove(role);
		}

		executeInsideTransaction(entityManager -> entityManager.merge(user));
	}

	public void removeTrip(User user, Trip trip) {
		if (user.getRoles().contains(trip)) {
			user.getRoles().remove(trip);
		}

		executeInsideTransaction(entityManager -> entityManager.merge(user));
	}

	public void removeTicket(User user, Ticket ticket) {
		if (user.getRoles().contains(ticket)) {
			user.getRoles().remove(ticket);
		}

		executeInsideTransaction(entityManager -> entityManager.merge(user));
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
		return entityManager.createQuery("SELECT u FROM User u, UserType ut WHERE ut.userTypeName = :type")
				.setParameter("type", type).getResultList();
	}

	public List<User> getByUserProfileId(int profileId) {
		return entityManager.createQuery("FROM User WHERE userprofile_id = :id").setParameter("id", profileId)
				.getResultList();
	}

	/* Test */
	public List<User> getByUserLocation(String location) {
		return entityManager.createQuery("SELECT u FROM User u, Location l WHERE l.locationName = :location")
				.setParameter("location", location).getResultList();
	}

	@Override
	public Optional<User> getById(int id) {
		/* .getSingleResult() throws exception if value is null, we want a returnable null value */
		return Optional.ofNullable((User) entityManager.createQuery("FROM User WHERE user_id = :id").setParameter("id", id)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null));
	}

	@Override
	public Optional<User> getByName(String name) {
		/* Name refers to login name, separate function for full name */
		return Optional.ofNullable((User) entityManager.createQuery("FROM User WHERE user_loginname = :name")
				.setParameter("name", name)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null));
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
