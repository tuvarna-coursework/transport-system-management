package com.tuvarna.transportsystem.services;

import java.util.List;
import java.util.Optional;

import com.tuvarna.transportsystem.dao.RouteDAO;
import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Route;

public class RouteService implements CrudService<Route> {
	private RouteDAO routeDAO;

	public RouteService() {
		this.routeDAO = new RouteDAO();
	}
	
	public List<Location> getAttachmentLocationsInRouteById(int routeId){
		return routeDAO.getAttachmentLocationsInRouteById(routeId);
	}
	
	
	public String getArrivalHourAtAttachmentLocation(int routeId, int locationId) {
		return routeDAO.getArrivalHourAtAttachmentLocation(routeId, locationId);
	}

	public void addAttachmentLocation(Route route, Location location, String hourOfDeparture) {
		routeDAO.addAttachmentLocation(route, location, hourOfDeparture);
	}

	/*
	public void removeAttachmentLocation(Route route, Location location) {
		routeDAO.removeAttachmentLocation(route, location);
	}
	*/

	@Override
	public Optional<Route> getById(int id) {
		return routeDAO.getById(id);
	}

	@Override
	public List<Route> getAll() {
		return routeDAO.getAll();
	}

	@Override
	public void save(Route route) {
		routeDAO.save(route);
	}

	@Override
	public void deleteById(int id) {
		routeDAO.deleteById(id);
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
