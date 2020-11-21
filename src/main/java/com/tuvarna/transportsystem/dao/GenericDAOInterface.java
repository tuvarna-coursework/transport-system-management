package com.tuvarna.transportsystem.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAOInterface<T> {

	public Optional<T> getById(int id);
	
	public Optional<T> getByName(String name);
	
	public List<T> getAll();
	
	public void save(T entity);
	
	public void updateName(T entity, String newValue);
	
	public void deleteById(int id);
	
	public void deleteByName(String name);
	
	@Deprecated
	/* Currently each class will have individual update functions for each of their fields */
	void update(T entity, String[] newValues); 
	
}