package com.tuvarna.transportsystem.services;

import java.util.List;
import java.util.Optional;

import com.tuvarna.transportsystem.dao.UserDAO;
import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.Role;
import com.tuvarna.transportsystem.entities.Ticket;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;

public class UserService implements CrudService<User> {
	private UserDAO userDAO;

	public UserService() {
		this.userDAO = new UserDAO();
	}

	public Optional<User> getUserByTripId(int tripId) {
		return userDAO.getUserByTripId(tripId);
	}

	/* Functionality for joined tables */
	public void addRole(User user, Role role) {
		userDAO.addRole(user, role);
	}

	public void addTrip(User user, Trip trip) {
		userDAO.addTrip(user, trip);
	}

	public void addTicket(User user, Ticket ticket) {
		userDAO.addTicket(user, ticket);
	}
	
	public void addCashierToTransportCompany(User company, User cashier) {
		userDAO.addCashierToTransportCompany(company, cashier);
	}

	public void removeRole(User user, Role role) {
		userDAO.removeRole(user, role);
	}

	public void removeTrip(User user, Trip trip) {
		userDAO.removeTrip(user, trip);
	}
	
	public void removeTicket(User user, Ticket ticket) {
		userDAO.removeTicket(user, ticket);
	}
	
	public void removeCashierFromTransportCompany(User company, User cashier) {
		userDAO.removeCashierFromCompany(company, cashier);
	}

	public void updateLocation(User user, Location location) {
		userDAO.updateLocation(user, location);
	}

	public void updateUserProfile(User user, UserProfile profile) {
		userDAO.updateUserProfile(user, profile);
	}

	public List<User> getByFullName(String name) {
		return userDAO.getByFullName(name);
	}

	public List<User> getByUserType(String type) {
		return userDAO.getByUserType(type);
	}

	public List<User> getByUserProfileId(int profileId) {
		return userDAO.getByUserProfileId(profileId);
	}

	public List<User> getByUserLocation(String location) {
		return userDAO.getByUserLocation(location);
	}

	@Override
	public Optional<User> getById(int id) {
		return userDAO.getById(id);
	}

	@Override
	public Optional<User> getByName(String name) {
		return userDAO.getByName(name);
	}

	@Override
	public List<User> getAll() {
		return userDAO.getAll();
	}

	@Override
	public void save(User user) {
		userDAO.save(user);
	}

	@Override
	public void updateName(User user, String newValue) {
		userDAO.updateName(user, newValue);
	}

	@Override
	public void deleteById(int id) {
		userDAO.deleteById(id);
	}

	@Override
	public void deleteByName(String name) {
		userDAO.deleteByName(name);
	}

	@Deprecated
	@Override
	public void update(User user, String[] newValues) {
	}
}
