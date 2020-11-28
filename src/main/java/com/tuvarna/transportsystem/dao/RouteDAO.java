package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Role;
import com.tuvarna.transportsystem.entities.Route;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class RouteDAO implements GenericDAOInterface<Route> {
	private EntityManager entityManager;

	public RouteDAO() {
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

	public void addAttachmentLocation(Route route, Location location) {
		route.getAttachmentLocations().add(location);
		executeInsideTransaction(entityManager -> entityManager.merge(route));
	}

	public void removeAttachmentLocation(Route route, Location location) {
		if (route.getAttachmentLocations().contains(location)) {
			route.getAttachmentLocations().remove(location);
		}

		executeInsideTransaction(entityManager -> entityManager.merge(route));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<Route> getById(int id) {
		return Optional.ofNullable((Route) entityManager.createQuery("FROM Route WHERE route_id = :id")
				.setParameter("id", id).getResultList().stream().findFirst().orElse(null));
	}

	@Override
	public List<Route> getAll() {
		return entityManager.createQuery("FROM Route").getResultList();
	}

	@Override
	public void save(Route route) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(route));
	}

	@Override
	public void deleteById(int id) {
		if (!this.getById(id).isPresent()) {
			return;
		}

		Route route = this.getById(id).get();
		executeInsideTransaction(entityManager -> entityManager.remove(route));
	}

	@Deprecated
	@Override
	public Optional<Route> getByName(String name) {
		return null;
	}

	@Deprecated
	@Override
	public void updateName(Route route, String newValue) {
	}

	@Deprecated
	@Override
	public void deleteByName(String name) {
	}

	@Deprecated
	@Override
	public void update(Route route, String[] newValues) {
	}
}