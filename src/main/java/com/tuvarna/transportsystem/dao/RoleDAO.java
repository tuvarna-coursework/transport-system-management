package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.tuvarna.transportsystem.entities.Role;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

@SuppressWarnings("unchecked")
public class RoleDAO implements GenericDAOInterface<Role> {
	private EntityManager entityManager;

	public RoleDAO() {
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
	public Optional<Role> getById(int id) {
		return Optional.ofNullable((Role) entityManager.createQuery("FROM Role WHERE role_id = :id")
				.setParameter("id", id)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null));
	}

	@Override
	public Optional<Role> getByName(String name) {
		return Optional.ofNullable((Role) entityManager.createQuery("FROM Role WHERE role_name = :name")
				.setParameter("name", name)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null));
	}

	@Override
	public List<Role> getAll() {
		return entityManager.createQuery("FROM Role").getResultList();
	}

	@Override
	public void save(Role role) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(role));
	}

	@Override
	public void updateName(Role role, String newValue) {
		role.setRoleName(newValue);
		executeInsideTransaction(entityManager -> entityManager.merge(role));
	}

	@Override
	public void deleteById(int id) {
		if (!this.getById(id).isPresent()) {
			return;
		}

		Role role = this.getById(id).get();
		executeInsideTransaction(entityManager -> entityManager.remove(role));
	}

	@Override
	public void deleteByName(String name) {
		if (!this.getByName(name).isPresent()) {
			return;
		}

		Role role = this.getByName(name).get();
		executeInsideTransaction(entityManager -> entityManager.remove(role));
	}

	@Deprecated
	@Override
	public void update(Role role, String[] newValues) {
	}
}