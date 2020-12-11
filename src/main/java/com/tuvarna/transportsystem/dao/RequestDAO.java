package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tuvarna.transportsystem.entities.Request;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

@SuppressWarnings("unchecked")
public class RequestDAO implements GenericDAOInterface<Request> {
	private EntityManager entityManager;
	private static final Logger logger = LogManager.getLogger(RequestDAO.class.getName());

	public RequestDAO() {
		entityManager = DatabaseUtils.globalSession.getEntityManagerFactory().createEntityManager();
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured and RequestDAO initialized.");
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
	
	public void updateStatus(Request request, String newStatus) {
		request.setStatus(newStatus);
		executeInsideTransaction(entityManager -> entityManager.merge(request));
	}
	
	public void deleteByTripId(int tripId) {
		this.getAll().forEach(r -> {
			if(r.getTrip().getTripId() == tripId) {
				executeInsideTransaction(entityManager -> entityManager.remove(r));
			};
		});
	}

	@Override
	public Optional<Request> getById(int id) {
		return Optional.ofNullable((Request) entityManager.createQuery("FROM Request WHERE request_id = :id")
				.setParameter("id", id)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null));
	}

	@Override
	public List<Request> getAll() {
		return entityManager.createQuery("FROM Request").getResultList();
	}

	@Override
	public void save(Request request) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(request));
	}

	@Override
	public void deleteById(int id) {
		if (!this.getById(id).isPresent()) {
			return;
		}

		Request request = this.getById(id).get();
		executeInsideTransaction(entityManager -> entityManager.remove(request));
	}

	@Deprecated
	@Override
	public Optional<Request> getByName(String name) {
		/* No name */
		return null;
	}

	@Deprecated
	@Override
	public void updateName(Request entity, String newValue) {
	}

	@Deprecated
	@Override
	public void deleteByName(String name) {
	}

	@Deprecated
	@Override
	public void update(Request entity, String[] newValues) {
	}
}
