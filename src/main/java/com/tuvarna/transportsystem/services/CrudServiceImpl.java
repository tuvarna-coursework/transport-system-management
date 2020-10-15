package com.tuvarna.transportsystem.services;

import com.tuvarna.transportsystem.dao.GenericDAOInterface;
import com.tuvarna.transportsystem.dao.GenericDAO;

@SuppressWarnings("rawtypes")
public class CrudServiceImpl<T> implements CrudService<T> {
	private GenericDAOInterface dao;

	public CrudServiceImpl() {
		this.dao = new GenericDAO();
	}

	public void create(T entity) {
		dao.create(entity);
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
