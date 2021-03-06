package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tuvarna.transportsystem.entities.TransportType;
import com.tuvarna.transportsystem.entities.TripType;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

@SuppressWarnings("unchecked")
public class TripTypeDAO implements GenericDAOInterface<TripType> {
	private EntityManager entityManager;
	private static final Logger logger = LogManager.getLogger(TripTypeDAO.class.getName());

	public TripTypeDAO() {
		entityManager = DatabaseUtils.globalSession.getEntityManagerFactory().createEntityManager();
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured and TripTypeDAO initialized.");
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

	@SuppressWarnings("unchecked")
	@Override
	public Optional<TripType> getById(int id) {
		return Optional.ofNullable((TripType) entityManager.createQuery("FROM TripType WHERE triptype_id = :id")
				.setParameter("id", id).getResultList().stream().findFirst().orElse(null));
	}

	@Override
	public Optional<TripType> getByName(String name) {
		return Optional.ofNullable((TripType) entityManager.createQuery("FROM TripType WHERE triptype_name = :name")
				.setParameter("name", name).getResultList().stream().findFirst().orElse(null));
	}

	@Override
	public List<TripType> getAll() {
		return entityManager.createQuery("FROM TripType").getResultList();
	}

	@Override
	public void save(TripType type) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(type));
	}

	@Override
	public void updateName(TripType type, String newValue) {
		type.setTripTypeName(newValue);
		executeInsideTransaction(entityManager -> entityManager.merge(type));
	}

	@Override
	public void deleteById(int id) {
		if (!this.getById(id).isPresent()) {
			return;
		}

		TripType type = this.getById(id).get();
		executeInsideTransaction(entityManager -> entityManager.remove(type));
	}

	@Override
	public void deleteByName(String name) {
		if (!this.getByName(name).isPresent()) {
			return;
		}

		TripType type = this.getByName(name).get();
		executeInsideTransaction(entityManager -> entityManager.remove(type));
	}

	@Deprecated
	@Override
	public void update(TripType type, String[] newValues) {
	}
}
