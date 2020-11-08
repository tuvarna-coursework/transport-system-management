package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

@SuppressWarnings("unchecked")
public class TripDAO implements GenericDAOInterface<Trip> {

	private EntityManager entityManager;

	public TripDAO() {
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

	/* Test it */
	public List<Trip> getByTripType(String type) {
		return entityManager.createQuery("SELECT t FROM Trip t, TripType tt WHERE t.trip_type_id = tt.triptype_id "
				+ "AND tt.triptype_name = :type").setParameter("type", type).getResultList();
	}

	public List<Trip> getByDepartureLocation(String location) {
		return entityManager
				.createQuery("SELECT t FROM Trip t, Location l WHERE t.trip_departure_location_id = l.location_id "
						+ "AND l.location_name = :location")
				.setParameter("location", location).getResultList();
	}

	public List<Trip> getByArrivalLocation(String location) {
		return entityManager
				.createQuery("SELECT t FROM Trip t, Location l WHERE t.trip_arrival_location_id = l.location_id "
						+ "AND l.location_name = :location")
				.setParameter("location", location).getResultList();
	}

	/* TO DO: ADD MORE FUNCTIONALITY */

	@Override
	public Trip getById(int id) {
		return (Trip) entityManager.createQuery("FROM Trip WHERE trip_id = :id").setParameter("id", id)
				.getSingleResult(); // check if the return type has to be Optional<Class> or it is ok like this
	}

	@Override
	public List<Trip> getAll() {
		return entityManager.createQuery("FROM Trip").getResultList();
	}

	@Override
	public void save(Trip trip) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(trip));
	}

	@Override
	public void deleteById(int id) {
		Trip trip = this.getById(id);
		executeInsideTransaction(entityManager -> entityManager.remove(trip));
	}

	@Deprecated
	@Override
	public void updateName(Trip trip, String newValue) {
	}

	@Deprecated
	@Override
	public void deleteByName(String name) {
	}

	@Deprecated
	@Override
	public Trip getByName(String name) {
		return null;
	}

	@Deprecated
	@Override
	public void update(Trip trip, String[] newValues) {
	}
}
