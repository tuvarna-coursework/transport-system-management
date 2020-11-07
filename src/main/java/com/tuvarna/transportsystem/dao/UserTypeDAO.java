package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.UserType;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class UserTypeDAO implements GenericDAOInterface<UserType> {
	private EntityManager entityManager;

	public UserTypeDAO() {
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
	public UserType getById(int id) {
		return (UserType) entityManager.createQuery("FROM UserType WHERE usertype_id = :id").setParameter("id", id)
				.getSingleResult(); // check if the return type has to be Optional<Class> or it
									// is ok like this
	}

	@Override
	public List<UserType> getByName(String name) {
		return entityManager.createQuery("FROM UserType WHERE usertype_name = :name").setParameter("name", name)
				.getResultList();
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
		UserType type = this.getById(id);
		executeInsideTransaction(entityManager -> entityManager.remove(type));
	}

	@Override
	public void deleteByName(String name) {
		List<UserType> types = this.getByName(name);

		/*
		 * Have to iterate through the list, otherwise a single invocation of this
		 * method for a list doesn't work. Works both if it the query returned multiple
		 * records or a single one
		 */
		for (UserType type : types) {
			executeInsideTransaction(entityManager -> entityManager.remove(type));
		}
	}

	@Deprecated
	@Override
	public void update(UserType type, String[] newValues) {
	}
}
