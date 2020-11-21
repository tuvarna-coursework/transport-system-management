package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.UserType;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class UserTypeDAO implements GenericDAOInterface<UserType> {
	private EntityManager entityManager;

	public UserTypeDAO() {
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
	public Optional<UserType> getById(int id) {
		return Optional.ofNullable((UserType) entityManager.createQuery("FROM UserType WHERE usertype_id = :id").setParameter("id", id)
				.getSingleResult()); 
	}

	@Override
	public Optional<UserType> getByName(String name) {
		return Optional.ofNullable((UserType) entityManager.createQuery("FROM UserType WHERE usertype_name = :name")
				.setParameter("name", name).getSingleResult());
	}

	@Override
	public List<UserType> getAll() {
		return entityManager.createQuery("FROM UserType").getResultList();
	}

	@Override
	public void save(UserType type) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(type));
	}

	@Override
	public void updateName(UserType type, String newValue) {
		type.setUserTypeName(newValue);
		executeInsideTransaction(entityManager -> entityManager.merge(type));
	}

	@Override
	public void deleteById(int id) {
		if (!this.getById(id).isPresent()) {
			return;
		}
		
		UserType type = this.getById(id).get();
		executeInsideTransaction(entityManager -> entityManager.remove(type));
	}

	@Override
	public void deleteByName(String name) {
		if (!this.getByName(name).isPresent()) {
			return;
		}
		
		UserType type = this.getByName(name).get();
		executeInsideTransaction(entityManager -> entityManager.remove(type));
	}

	@Deprecated
	@Override
	public void update(UserType type, String[] newValues) {
	}
}
