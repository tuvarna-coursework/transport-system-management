package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class UserProfileDAO implements GenericDAOInterface<UserProfile> {
	private EntityManager entityManager;

	public UserProfileDAO() {
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
	public UserProfile getById(int id) {
		return (UserProfile) entityManager.createQuery("FROM UserProfile WHERE userprofile_id = :id")
				.setParameter("id", id).getSingleResult(); // check if the return type has to be Optional<Class> or it
															// is ok like this
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
		UserProfile profile = this.getById(id);
		executeInsideTransaction(entityManager -> entityManager.remove(profile));
	}

	@Deprecated
	@Override
	public List<UserProfile> getByName(String name) {
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