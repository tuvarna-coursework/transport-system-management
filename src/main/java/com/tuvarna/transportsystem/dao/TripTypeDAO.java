package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.TransportType;
import com.tuvarna.transportsystem.entities.TripType;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class TripTypeDAO implements GenericDAOInterface<TripType> {
	private EntityManager entityManager;

	public TripTypeDAO() {
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

	@SuppressWarnings("unchecked")
	@Override
	public TripType getById(int id) {
		return (TripType) entityManager.createQuery("FROM TripType WHERE triptype_id = :id").setParameter("id", id)
				.getSingleResult(); // check if the return type has to be Optional<Class> or it
									// is ok like this
	}

	@Override
	public TripType getByName(String name) {
		return (TripType) entityManager.createQuery("FROM TripType WHERE triptype_name = :name")
				.setParameter("name", name).getSingleResult();
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
		TripType type = this.getById(id);
		executeInsideTransaction(entityManager -> entityManager.remove(type));
	}

	@Override
	public void deleteByName(String name) {
		TripType type = this.getByName(name);
		executeInsideTransaction(entityManager -> entityManager.remove(type));
	}

	@Deprecated
	@Override
	public void update(TripType type, String[] newValues) {
	}
}
