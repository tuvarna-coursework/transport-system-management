package com.tuvarna.transportsystem.services;

import java.util.List;

public interface CrudService<T> {

	public T getById(int id);

	public List<T> getByName(String name);

	public List<T> getAll();

	public void save(T entity);
	
	public void updateName(T entity, String newValue);
	
	public void deleteById(int id);
	
	public void deleteByName(String name);
	
	@Deprecated
	/* Currently each class will have individual update functions for each of their fields */
	void update(T entity, String[] newValues); 
}
