package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.tuvarna.transportsystem.entities.Role;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class RoleDAO implements GenericDAOInterface<Role> {
	private EntityManager entityManager;

	public RoleDAO() {
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
	public Role getById(int id) {
		return (Role) entityManager.createQuery("FROM Role WHERE role_id = :id").setParameter("id", id)
				.getSingleResult(); // check if the return type has to be Optional<Class> or it is ok like this
	}

	@Override
	public List<Role> getByName(String name) {
		return entityManager.createQuery("FROM Role WHERE role_name = :name").setParameter("name", name)
				.getResultList();
	}

	@Override
	public List<Role> getAll() {
		return entityManager.createQuery("FROM Role").getResultList();
	}

	@Override
	public void save(Role entity) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(entity));
	}

	public void updateName(Role role, String newValue) {
		role.setRoleName(newValue);
		executeInsideTransaction(entityManager -> entityManager.merge(role));
	}

	@Override
	public void deleteById(int id) {
		Role role = this.getById(id);
		executeInsideTransaction(entityManager -> entityManager.remove(role));
	}
	
	@Override
	public void deleteByName(String name) {
		List<Role> roles = this.getByName(name);
		
		/* Have to iterate through the list, otherwise a single invocation of this method for a list doesn't work. 
		 * Works both if it the query returned multiple records or a single one */
		for (Role role : roles) {
			executeInsideTransaction(entityManager -> entityManager.remove(role));
		}
	}

	@Deprecated
	@Override
	public void update(Role entity, String[] newValues) {
	}
}