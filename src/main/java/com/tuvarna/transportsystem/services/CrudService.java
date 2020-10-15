package com.tuvarna.transportsystem.services;


public interface CrudService<T> {
	
	public void create(T entity);
	
	public void read();
	
	public void update();
	
	public void delete();
}
