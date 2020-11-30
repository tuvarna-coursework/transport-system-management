package com.tuvarna.transportsystem.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.tuvarna.transportsystem.dao.TransportTypeDAO;
import com.tuvarna.transportsystem.dao.TripDAO;
import com.tuvarna.transportsystem.entities.TransportType;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.User;

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
	
	public List<Trip> getByLocations(String departure, String arrival){
		return tripDAO.getByLocations(departure, arrival);
	}
	
	public List<Trip> getByAttachmentLocations(String location){
		return tripDAO.getByAttachmentLocation(location);
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
	
	public void addCashierForTrip(Trip trip, User cashier) {
		tripDAO.addCashierForTrip(trip, cashier);
	}
	
	public void removeCashierFromTrip(Trip trip, User cashier) {
		tripDAO.removeCashierFromTrip(trip, cashier);
	}
	
	public void updateTripTicketAvailability(Trip trip, int newValue) {
		tripDAO.updateTripTicketAvailability(trip, newValue);
	}

	@Override
	public Optional<Trip> getById(int id) {
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
	public Optional<Trip> getByName(String name) {
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
