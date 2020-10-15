package com.tuvarna.transportsystem.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tuvarna.transportsystem.entities.Role;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class GenericDAO<T> implements GenericDAOInterface<T> {

	public GenericDAO() {

	}

	public void create(T entity) {
		Session session;
		try {
			session = DatabaseUtils.createSession();
			Transaction transaction = session.beginTransaction();

			session.save(entity);
			transaction.commit();

			session.close();
		} catch (Throwable ex) {
			System.err.println("Failed to persist to database: " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public void read() {
		// TODO Auto-generated method stub

	}

	public void update() {
		// TODO Auto-generated method stub

	}

	public void delete() {
		// TODO Auto-generated method stub

	}
}
