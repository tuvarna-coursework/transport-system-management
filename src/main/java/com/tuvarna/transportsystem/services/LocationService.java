package com.tuvarna.transportsystem.services;

import java.util.List;
import java.util.Optional;

import com.tuvarna.transportsystem.dao.LocationDAO;
import com.tuvarna.transportsystem.entities.Location;

public class LocationService implements CrudService<Location> {
	private LocationDAO locationDAO;

	public LocationService() {
		this.locationDAO = new LocationDAO();
	}

	@Override
	public Optional<Location> getById(int id) {
		return locationDAO.getById(id);
	}

	@Override
	public Optional<Location> getByName(String name) {
		return locationDAO.getByName(name);
	}

	@Override
	public List<Location> getAll() {
		return locationDAO.getAll();
	}

	@Override
	public void save(Location location) {
		locationDAO.save(location);
	}

	@Override
	public void updateName(Location location, String newValue) {
		locationDAO.updateName(location, newValue);
	}

	@Override
	public void deleteById(int id) {
		locationDAO.deleteById(id);
	}

	@Override
	public void deleteByName(String name) {
		locationDAO.deleteByName(name);
	}

	@Deprecated
	@Override
	public void update(Location location, String[] newValues) {
	}
}
