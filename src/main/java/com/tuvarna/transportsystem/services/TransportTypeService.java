package com.tuvarna.transportsystem.services;

import java.util.List;

import com.tuvarna.transportsystem.dao.TransportTypeDAO;
import com.tuvarna.transportsystem.entities.TransportType;

public class TransportTypeService implements CrudService<TransportType>{
	private TransportTypeDAO transportTypeDAO;

	public TransportTypeService() {
		this.transportTypeDAO = new TransportTypeDAO();
	}

	@Override
	public TransportType getById(int id) {
		return transportTypeDAO.getById(id);
	}

	@Override
	public List<TransportType> getByName(String name) {
		return transportTypeDAO.getByName(name);
	}

	@Override
	public List<TransportType> getAll() {
		return transportTypeDAO.getAll();
	}

	@Override
	public void save(TransportType type) {
		transportTypeDAO.save(type);
	}

	@Override
	public void updateName(TransportType type, String newValue) {
		transportTypeDAO.updateName(type, newValue);
	}

	@Override
	public void deleteById(int id) {
		transportTypeDAO.deleteById(id);
	}

	@Override
	public void deleteByName(String name) {
		transportTypeDAO.deleteByName(name);
	}

	@Deprecated
	@Override
	public void update(TransportType type, String[] newValues) {
	}
}
