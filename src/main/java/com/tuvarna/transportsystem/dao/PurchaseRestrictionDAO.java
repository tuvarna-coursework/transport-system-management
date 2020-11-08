package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.PurchaseRestriction;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class PurchaseRestrictionDAO implements GenericDAOInterface<PurchaseRestriction> {
	private EntityManager entityManager;

	public PurchaseRestrictionDAO() {
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
	public PurchaseRestriction getById(int id) {
		return (PurchaseRestriction) entityManager
				.createQuery("FROM PurchaseRestriction WHERE purchase_restriction_id = :id").setParameter("id", id)
				.getSingleResult(); // check if the return type has to be Optional<Class> or it is ok like this
	}

	@Override
	public PurchaseRestriction getByName(String name) {
		return (PurchaseRestriction) entityManager
				.createQuery("FROM PurchaseRestriction WHERE purchase_restriction_name = :name")
				.setParameter("name", name).getSingleResult();
	}

	@Override
	public List<PurchaseRestriction> getAll() {
		return entityManager.createQuery("FROM PurchaseRestriction").getResultList();
	}

	@Override
	public void save(PurchaseRestriction restriction) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(restriction));
	}

	@Override
	public void updateName(PurchaseRestriction restriction, String newValue) {
		restriction.setPurchaseRestriction_name(newValue);
		executeInsideTransaction(entityManager -> entityManager.merge(restriction));
	}

	@Override
	public void deleteById(int id) {
		PurchaseRestriction restriction = this.getById(id);
		executeInsideTransaction(entityManager -> entityManager.remove(restriction));
	}

	@Override
	public void deleteByName(String name) {
		PurchaseRestriction restriction = this.getByName(name);
		executeInsideTransaction(entityManager -> entityManager.remove(restriction));
	}

	@Deprecated
	@Override
	public void update(PurchaseRestriction location, String[] newValues) {
	}
}
