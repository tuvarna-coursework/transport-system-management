package com.tuvarna.transportsystem.dao;


public interface GenericDAOInterface<T> {

	public void create(T role);

	public void read();

	public void update();

	public void delete();
}
