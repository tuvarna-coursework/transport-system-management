package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

@SuppressWarnings("unchecked")
public class UserProfileDAO implements GenericDAOInterface<UserProfile> {
	private EntityManager entityManager;
	private static final Logger logger = LogManager.getLogger(UserProfileDAO.class.getName());

	public UserProfileDAO() {
		entityManager = DatabaseUtils.globalSession.getEntityManagerFactory().createEntityManager();
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured and UserProfileDAO initialized.");
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
	
	public void increaseRating(UserProfile profile, double rating) {
		profile.setUserProfileRating(profile.getUserProfileRating() + rating);
		executeInsideTransaction(entityManager -> entityManager.merge(profile));
	}

	/* Below are additional queries for this entity only */
	public List<UserProfile> getByRating(double rating) {
		return entityManager.createQuery("FROM UserProfile WHERE userprofile_rating = :rating")
				.setParameter("rating", rating).getResultList();
	}

	public List<UserProfile> getByRatingRange(double lowerBoundary, double upperBoundary) {
		/* HQL BETWEEN and AND keywords are inclusive */
		return entityManager
				.createQuery("FROM UserProfile WHERE userprofile_rating BETWEEN :lowerBoundary AND :upperBoundary")
				.setParameter("lowerBoundary", lowerBoundary).setParameter("upperBoundary", upperBoundary)
				.getResultList();
	}

	public List<UserProfile> getByHonorarium(double honorarium) {
		return entityManager.createQuery("FROM UserProfile WHERE userprofile_honorarium = :honorarium")
				.setParameter("honorarium", honorarium).getResultList();
	}

	public List<UserProfile> getByHonorariumRange(double lowerBoundary, double upperBoundary) {
		/* HQL BETWEEN and AND keywords are inclusive */
		return entityManager
				.createQuery("FROM UserProfile WHERE userprofile_honorarium BETWEEN :lowerBoundary AND :upperBoundary")
				.setParameter("lowerBoundary", lowerBoundary).setParameter("upperBoundary", upperBoundary)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<UserProfile> getById(int id) {
		return Optional.ofNullable((UserProfile) entityManager
				.createQuery("FROM UserProfile WHERE userprofile_id = :id").setParameter("id", id)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null));
	}

	@Override
	public List<UserProfile> getAll() {
		return entityManager.createQuery("FROM UserProfile").getResultList();
	}

	@Override
	public void save(UserProfile profile) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(profile));
	}

	@Override
	public void deleteById(int id) {
		if (!this.getById(id).isPresent()) {
			return;
		}

		UserProfile profile = this.getById(id).get();
		executeInsideTransaction(entityManager -> entityManager.remove(profile));
	}

	@Deprecated
	@Override
	public Optional<UserProfile> getByName(String name) {
		/* Entity has no name column */
		return null;
	}

	@Deprecated
	@Override
	public void updateName(UserProfile profile, String newValue) {
		/* Entity has no name column */
	}

	@Deprecated
	@Override
	public void deleteByName(String name) {
		/* Entity has no name column */
	}

	@Deprecated
	@Override
	public void update(UserProfile profile, String[] newValues) {
	}
}
