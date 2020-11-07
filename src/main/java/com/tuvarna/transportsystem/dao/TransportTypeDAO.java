package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.TransportType;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class TransportTypeDAO implements GenericDAOInterface<TransportType> {
	private EntityManager entityManager;

	public TransportTypeDAO() {
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
	public TransportType getById(int id) {
		return (TransportType) entityManager.createQuery("FROM TransportType WHERE transport_type_id = :id")
				.setParameter("id", id).getSingleResult(); // check if the return type has to be Optional<Class> or it
															// is ok like this
	}

	@Override
	public List<TransportType> getByName(String name) {
		return entityManager.createQuery("FROM TransportType WHERE transport_type_name = :name")
				.setParameter("name", name).getResultList();
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
		TransportType type = this.getById(id);
		executeInsideTransaction(entityManager -> entityManager.remove(type));
	}

	@Override
	public void deleteByName(String name) {
		List<TransportType> types = this.getByName(name);

		/*
		 * Have to iterate through the list, otherwise a single invocation of this
		 * method for a list doesn't work. Works both if it the query returned multiple
		 * records or a single one
		 */
		for (TransportType type : types) {
			executeInsideTransaction(entityManager -> entityManager.remove(type));
		}
	}

	@Deprecated
	@Override
	public void update(TransportType type, String[] newValues) {
	}
}
