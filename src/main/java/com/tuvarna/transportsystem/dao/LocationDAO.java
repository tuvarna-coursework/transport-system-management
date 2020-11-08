package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class LocationDAO implements GenericDAOInterface<Location> {
	private EntityManager entityManager;

	public LocationDAO() {
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

	@SuppressWarnings("unchecked")
	@Override
	public Location getById(int id) {
		return (Location) entityManager.createQuery("FROM Location WHERE location_id = :id").setParameter("id", id)
				.getSingleResult(); // check if the return type has to be Optional<Class> or it is ok like this
	}

	@Override
	public Location getByName(String name) {
		return (Location) entityManager.createQuery("FROM Location WHERE location_name = :name")
				.setParameter("name", name).getSingleResult();
	}

	@Override
	public List<Location> getAll() {
		return entityManager.createQuery("FROM Location").getResultList();
	}

	@Override
	public void save(Location location) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(location));
	}

	@Override
	public void updateName(Location location, String newValue) {
		location.setLocationName(newValue);
		executeInsideTransaction(entityManager -> entityManager.merge(location));
	}

	@Override
	public void deleteById(int id) {
		Location location = this.getById(id);
		executeInsideTransaction(entityManager -> entityManager.remove(location));
	}

	@Override
	public void deleteByName(String name) {
		Location location = this.getByName(name);
		executeInsideTransaction(entityManager -> entityManager.remove(location));
	}

	@Deprecated
	@Override
	public void update(Location location, String[] newValues) {
	}
}
