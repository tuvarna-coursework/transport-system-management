package com.tuvarna.transportsystem.dao;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

@SuppressWarnings("unchecked")
public class TripDAO implements GenericDAOInterface<Trip> {
	private EntityManager entityManager;

	public TripDAO() {
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

	/* Test it */
	public List<Trip> getByTripType(String type) {
		return entityManager.createQuery("SELECT t FROM Trip t, TripType tt WHERE t.tripType = tt.tripTypeId "
				+ "AND tt.tripTypeName = :type").setParameter("type", type).getResultList();
	}

	public List<Trip> getByDepartureLocation(String location) {
		return entityManager
				.createQuery("SELECT t FROM Trip t, Location l WHERE t.tripDepartureLocation = l.locationId"
						+ "AND l.locationName = :location")
				.setParameter("location", location).getResultList();
	}

	public List<Trip> getByArrivalLocation(String location) {
		return entityManager
				.createQuery("SELECT t FROM Trip t, Location l WHERE t.tripArrivalLocation = l.locationId"
						+ "AND l.locationName = :location")
				.setParameter("location", location).getResultList();
	}
	
	public List<Trip> getByLocations(String departure, String arrival){
		return entityManager
				.createQuery("SELECT t FROM Trip t, Location l, Location l2 WHERE (t.tripDepartureLocation = l.locationId"
						+ " AND l.locationName = :departure) AND"
						+ "(t.tripArrivalLocation = l2.locationId AND l2.locationName = :arrival)")
				.setParameter("departure", departure)
				.setParameter("arrival", arrival)
				.getResultList();
	}

	public List<Trip> getByDepartureDate(Date date) {
		return  entityManager.createQuery("FROM Trip WHERE trip_departure_date = :date")
				.setParameter("date", date).getResultList();
	}

	public List<Trip> getByArrivalDate(Date date) {
		return  entityManager.createQuery("FROM Trip WHERE trip_arrival_date = :date")
				.setParameter("date", date).getResultList();
	}
	
	public List<Trip> getByDepartureHour(String hour){
		return  entityManager.createQuery("FROM Trip WHERE trip_hour_of_departure = :hour")
				.setParameter("hour", hour).getResultList();
	}
	
	public void updateTripTicketAvailability(Trip trip, int newValue) {
			trip.setTripTicketAvailability(newValue);
			executeInsideTransaction(entityManager -> entityManager.merge(trip));	
	}

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
