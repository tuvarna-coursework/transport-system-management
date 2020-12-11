package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tuvarna.transportsystem.entities.Notification;
import com.tuvarna.transportsystem.entities.Request;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

@SuppressWarnings("unchecked")
public class NotificationDAO implements GenericDAOInterface<Notification> {
	private EntityManager entityManager;
	private static final Logger logger = LogManager.getLogger(NotificationDAO.class.getName());

	public NotificationDAO() {
		entityManager = DatabaseUtils.globalSession.getEntityManagerFactory().createEntityManager();
		PropertyConfigurator.configure("log4j.properties"); // configure log4j
		logger.info("Log4J successfully configured and NotificationDAO initialized.");
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

	public List<Notification> getNotificationsBySenderId(int userId) {
		return entityManager.createQuery("FROM Notification WHERE sender_id = :id").setParameter("id", userId)
				.getResultList();

	}

	public List<Notification> getNotificationsForReceiverId(int userId) {
		return entityManager.createQuery("FROM Notification WHERE receiver_id = :id").setParameter("id", userId)
				.getResultList();
	}

	@Override
	public Optional<Notification> getById(int id) {
		return Optional
				.ofNullable((Notification) entityManager.createQuery("FROM Notification WHERE notification_id = :id")
						.setParameter("id", id).getResultList().stream().findFirst().orElse(null));
	}

	@Override
	public List<Notification> getAll() {
		return entityManager.createQuery("FROM Notification").getResultList();
	}

	@Override
	public void save(Notification notification) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(notification));
	}

	@Override
	public void deleteById(int id) {
		if (!this.getById(id).isPresent()) {
			return;
		}

		Notification notification = this.getById(id).get();
		executeInsideTransaction(entityManager -> entityManager.remove(notification));
	}

	@Deprecated
	@Override
	public Optional<Notification> getByName(String name) {
		/* No name */
		return null;
	}

	@Deprecated
	@Override
	public void updateName(Notification entity, String newValue) {
	}

	@Deprecated
	@Override
	public void deleteByName(String name) {
	}

	@Deprecated
	@Override
	public void update(Notification entity, String[] newValues) {
	}
}