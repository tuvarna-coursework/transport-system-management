package com.tuvarna.transportsystem.dao;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class TicketDAO implements GenericDAOInterface<Ticket>{
	private EntityManager entityManager;

	public TicketDAO() {
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
	
	/* Functionality exclusive for Ticket class. Does HQL / Postgre work with Java Date??*/
	public List<Ticket> getByPurchaseDate(Date date){
		return entityManager.createQuery("FROM Ticket WHERE ticket_purchasedate = :date").setParameter("date", date)
				 .getResultList();
	}
	
	public List<Ticket> getByUser(int userId){
		return entityManager.createQuery("FROM Ticket WHERE user_id = :id").setParameter("id", userId)
				 .getResultList();
	}
	
	public List<Ticket> getByTrip(int tripId){
		return entityManager.createQuery("FROM Ticket WHERE trip_id = :id").setParameter("id", tripId)
				 .getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Ticket getById(int id) {
		return (Ticket) entityManager.createQuery("FROM Ticket WHERE ticket_id = :id").setParameter("id", id)
				.getSingleResult(); // check if the return type has to be Optional<Class> or it is ok like this
	}

	@Override
	public List<Ticket> getAll() {
		return entityManager.createQuery("FROM Ticket").getResultList();
	}

	@Override
	public void save(Ticket ticket) {
		/* Lambda functions unapplicable if JRE is below 1.8 (please update if so) */
		executeInsideTransaction(entityManager -> entityManager.persist(ticket));
	}

	@Override
	public void deleteById(int id) {
		Ticket ticket = this.getById(id);
		executeInsideTransaction(entityManager -> entityManager.remove(ticket));
	}
	
	@Deprecated
	@Override
	public Ticket getByName(String name) {
		/* No name column */
		return null;
	}
	
	@Deprecated
	@Override
	public void updateName(Ticket location, String newValue) {
		/* makes no sense for a ticket to be updateable */
	}

	@Deprecated
	@Override
	public void deleteByName(String name) {
	}

	@Deprecated
	@Override
	public void update(Ticket ticket, String[] newValues) {
	}
}
