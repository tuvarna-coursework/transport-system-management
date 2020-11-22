package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

@SuppressWarnings("unchecked")
public class LocationDAO implements GenericDAOInterface<Location> {
	private EntityManager entityManager;

	public LocationDAO() {
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

	@Override
	public Optional<Location> getById(int id) {
		/* .getSingleResult throws exception if value is null, we need it returnable */
		return Optional.ofNullable((Location) entityManager.createQuery("FROM Location WHERE location_id = :id")
				.setParameter("id", id)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null));
	}

	@Override
	public Optional<Location> getByName(String name) {
		return Optional.ofNullable((Location) entityManager.createQuery("FROM Location WHERE location_name = :name")
				.setParameter("name", name)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null));
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
		if (!this.getById(id).isPresent()) {
			return;
		}
		
		Location location = this.getById(id).get();
		executeInsideTransaction(entityManager -> entityManager.remove(location));
	}

	@Override
	public void deleteByName(String name) {
		if (!this.getByName(name).isPresent()) {
			return;
		}
		
		Location location = this.getByName(name).get();
		executeInsideTransaction(entityManager -> entityManager.remove(location));
	}

	@Deprecated
	@Override
	public void update(Location location, String[] newValues) {
	}
}
