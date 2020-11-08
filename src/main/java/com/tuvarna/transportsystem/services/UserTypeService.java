package com.tuvarna.transportsystem.services;

import java.util.List;

import com.tuvarna.transportsystem.dao.UserTypeDAO;
import com.tuvarna.transportsystem.entities.UserType;

public class UserTypeService implements CrudService<UserType> {
	private UserTypeDAO userTypeDAO;

	public UserTypeService() {
		this.userTypeDAO = new UserTypeDAO();
	}

	@Override
	public UserType getById(int id) {
		return userTypeDAO.getById(id);
	}

	@Override
	public UserType getByName(String name) {
		return userTypeDAO.getByName(name);
	}

	@Override
	public List<UserType> getAll() {
		return userTypeDAO.getAll();
	}

	@Override
	public void save(UserType type) {
		userTypeDAO.save(type);
	}

	@Override
	public void updateName(UserType type, String newValue) {
		userTypeDAO.updateName(type, newValue);
	}

	@Override
	public void deleteById(int id) {
		userTypeDAO.deleteById(id);
	}

	@Override
	public void deleteByName(String name) {
		userTypeDAO.deleteByName(name);
	}

	@Deprecated
	@Override
	public void update(UserType type, String[] newValues) {
	}
}
