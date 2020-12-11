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
import com.tuvarna.transportsystem.utils.DatabaseUtils;

@SuppressWarnings("unchecked")
public class TransportTypeDAO implements GenericDAOInterface<TransportType> {
	private EntityManager entityManager;
	private static final Logger logger = LogManager.getLogger(TransportTypeDAO.class.getName());

	public TransportTypeDAO() {
		entityManager = DatabaseUtils.globalSession.getEntityManagerFactory().createEntityManager();
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured and TransportTypeDAO initialized.");
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
	public Optional<TransportType> getById(int id) {
		return Optional.ofNullable(
				(TransportType) entityManager.createQuery("FROM TransportType WHERE transport_type_id = :id")
						.setParameter("id", id).getResultList().stream().findFirst().orElse(null));
	}

	@Override
	public Optional<TransportType> getByName(String name) {
		return Optional.ofNullable(
				(TransportType) entityManager.createQuery("FROM TransportType WHERE transport_type_name = :name")
						.setParameter("name", name).getResultList().stream().findFirst().orElse(null));
	}

	@Override
	public List<TransportType> getAll() {
		return entityManager.createQuery("FROM TransportType").getResultList();
	}

	@Override
	public void save(TransportType type) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(type));
	}

	@Override
	public void updateName(TransportType type, String newValue) {
		type.setTransportTypeName(newValue);
		executeInsideTransaction(entityManager -> entityManager.merge(type));
	}

	@Override
	public void deleteById(int id) {
		if (!this.getById(id).isPresent()) {
			return;
		}

		TransportType type = this.getById(id).get();
		executeInsideTransaction(entityManager -> entityManager.remove(type));
	}

	@Override
	public void deleteByName(String name) {
		if (!this.getByName(name).isPresent()) {
			return;
		}

		TransportType type = this.getByName(name).get();
		executeInsideTransaction(entityManager -> entityManager.remove(type));
	}

	@Deprecated
	@Override
	public void update(TransportType type, String[] newValues) {
	}
}
