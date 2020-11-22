package com.tuvarna.transportsystem.services;

import java.util.List;
import java.util.Optional;

import com.tuvarna.transportsystem.dao.RequestDAO;
import com.tuvarna.transportsystem.entities.Request;

public class RequestService implements CrudService<Request> {
	private RequestDAO requestDAO;

	public RequestService() {
		this.requestDAO = new RequestDAO();
	}

	@Override
	public Optional<Request> getById(int id) {
		return requestDAO.getById(id);
	}

	@Override
	public List<Request> getAll() {
		return requestDAO.getAll();
	}

	@Override
	public void save(Request request) {
		requestDAO.save(request);
	}

	@Override
	public void deleteById(int id) {
		requestDAO.deleteById(id);
	}

	@Deprecated
	@Override
	public Optional<Request> getByName(String name) {
		return null;
	}

	@Deprecated
	@Override
	public void updateName(Request request, String newValue) {
	}

	@Deprecated
	@Override
	public void deleteByName(String name) {
	}

	@Deprecated
	@Override
	public void update(Request request, String[] newValues) {
	}
}
