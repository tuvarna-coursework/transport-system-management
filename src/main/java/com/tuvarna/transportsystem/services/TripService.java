package com.tuvarna.transportsystem.services;

import java.util.Date;
import java.util.List;

import com.tuvarna.transportsystem.dao.TransportTypeDAO;
import com.tuvarna.transportsystem.dao.TripDAO;
import com.tuvarna.transportsystem.entities.TransportType;
import com.tuvarna.transportsystem.entities.Trip;

public class TripService implements CrudService<Trip> {
	private TripDAO tripDAO;

	public TripService() {
		this.tripDAO = new TripDAO();
	}

	public List<Trip> getByTripType(String type) {
		return tripDAO.getByTripType(type);
	}

	public List<Trip> getByDepartureLocation(String location) {
		return tripDAO.getByDepartureLocation(location);
	}

	public List<Trip> getByArrivalLocation(String location) {
		return tripDAO.getByArrivalLocation(location);
	}

	public List<Trip> getByDepartureDate(Date date) {
		return tripDAO.getByDepartureDate(date);
	}

	public List<Trip> getByArrivalDate(Date date) {
		return tripDAO.getByDepartureDate(date);
	}
	
	public List<Trip> getByDepartureHour(String hour){
		return tripDAO.getByDepartureHour(hour);
	}

	@Override
	public Trip getById(int id) {
		return tripDAO.getById(id);
	}

	@Override
	public List<Trip> getAll() {
		return tripDAO.getAll();
	}

	@Override
	public void save(Trip trip) {
		tripDAO.save(trip);
	}

	@Override
	public void deleteById(int id) {
		tripDAO.deleteById(id);
	}

	@Deprecated
	@Override
	public Trip getByName(String name) {
		return tripDAO.getByName(name);
	}

	@Deprecated
	@Override
	public void updateName(Trip trip, String newValue) {
		tripDAO.updateName(trip, newValue);
	}

	@Deprecated
	@Override
	public void deleteByName(String name) {
		tripDAO.deleteByName(name);
	}

	@Deprecated
	@Override
	public void update(Trip trip, String[] newValues) {
	}

}
