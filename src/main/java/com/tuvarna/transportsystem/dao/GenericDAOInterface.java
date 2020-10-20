package com.tuvarna.transportsystem.dao;

import java.util.List;

public interface GenericDAOInterface<T> {

	public T getById(int id);
	
	public List<T> getByName(String name);
	
	public List<T> getAll();
	
	public void save(T entity);
	
	@Deprecated
	/* Currently each class will have individual update functions for each of their fields */
	void update(T entity, String[] newValues); 
	
	public void deleteById(int id);
	
	public void deleteByName(String name);
	
}