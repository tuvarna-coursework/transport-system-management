package com.tuvarna.transportsystem.services;

import java.util.List;
import java.util.Optional;

import com.tuvarna.transportsystem.dao.TripTypeDAO;
import com.tuvarna.transportsystem.entities.TripType;

public class TripTypeService implements CrudService<TripType> {
	private TripTypeDAO tripTypeDAO;

	public TripTypeService() {
		this.tripTypeDAO = new TripTypeDAO();
	}

	@Override
	public Optional<TripType> getById(int id) {
		return tripTypeDAO.getById(id);
	}

	@Override
	public Optional<TripType> getByName(String name) {
		return tripTypeDAO.getByName(name);
	}

	@Override
	public List<TripType> getAll() {
		return tripTypeDAO.getAll();
	}

	@Override
	public void save(TripType type) {
		tripTypeDAO.save(type);
	}

	@Override
	public void updateName(TripType type, String newValue) {
		tripTypeDAO.updateName(type, newValue);
	}

	@Override
	public void deleteById(int id) {
		tripTypeDAO.deleteById(id);
	}

	@Override
	public void deleteByName(String name) {
		tripTypeDAO.deleteByName(name);
	}

	@Deprecated
	@Override
	public void update(TripType type, String[] newValues) {
	}
}
