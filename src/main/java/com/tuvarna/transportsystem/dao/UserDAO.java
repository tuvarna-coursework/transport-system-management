package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

@SuppressWarnings("unchecked")
public class UserDAO implements GenericDAOInterface<User> {
	private EntityManager entityManager;

	public UserDAO() {
		entityManager = DatabaseUtils.createSession().getEntityManagerFactory().createEntityManager();
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

	public void updateLocation(User user, Location location) {
		user.setUserLocation(location);
		executeInsideTransaction(entityManager -> entityManager.merge(user));
	}

	public User getByLoginName(String name) {
		return (User) entityManager.createQuery("FROM User WHERE user_loginname = :name").setParameter("name", name)
				.getSingleResult();
	}

	/*
	 * Test if it works, AFAIK this does a cross join which can affected performance
	 * negatively. Change to inner join one day
	 */
	public List<User> getByUserType(String type) {
		return entityManager.createQuery("SELECT u FROM User u, UserType ut WHERE ut.usertype_name = :type")
				.setParameter("type", type).getResultList();
	}

	public List<User> getByUserProfileId(int profileId) {
		return entityManager.createQuery("FROM User WHERE userprofile_id = :id").setParameter("id", profileId)
				.getResultList();
	}

	/* Test */
	public List<User> getByUserLocation(String location) {
		return entityManager.createQuery("SELECT u FROM User u, Location l WHERE l.location_name = :location")
				.setParameter("location", location).getResultList();
	}

	@Override
	public User getById(int id) {
		return (User) entityManager.createQuery("FROM User WHERE user_id = :id").setParameter("id", id)
				.getSingleResult(); // check if the return type has to be Optional<Class> or it is ok like this
	}

	@Override
	public List<User> getByName(String name) {
		/* Name refers to full name, separate function for login name */
		return entityManager.createQuery("FROM User WHERE user_fullname = :name").setParameter("name", name)
				.getResultList();
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
		User user = this.getById(id);
		executeInsideTransaction(entityManager -> entityManager.remove(user));
	}

	@Override
	public void deleteByName(String name) {
		List<User> users = this.getByName(name);

		/*
		 * Have to iterate through the list, otherwise a single invocation of this
		 * method for a list doesn't work. Works both if it the query returned multiple
		 * records or a single one
		 */
		for (User user : users) {
			executeInsideTransaction(entityManager -> entityManager.remove(user));
		}
	}

	@Deprecated
	@Override
	public void update(User user, String[] newValues) {
	}
}
