package com.tuvarna.transportsystem.services;

import java.util.List;

import com.tuvarna.transportsystem.dao.RoleDAO;
import com.tuvarna.transportsystem.entities.Role;

public class RoleService implements CrudService<Role> {
	private RoleDAO roleDAO;

	public RoleService() {
		this.roleDAO = new RoleDAO();
	}

	@Override
	public Role getById(int id) {
		return roleDAO.getById(id);
	}

	@Override
	public Role getByName(String name) {
		return roleDAO.getByName(name);
	}

	@Override
	public List<Role> getAll() {
		return roleDAO.getAll();
	}

	@Override
	public void save(Role role) {
		roleDAO.save(role);
	}

	@Override
	public void updateName(Role role, String newValue) {
		roleDAO.updateName(role, newValue);
	}

	@Override
	public void deleteById(int id) {
		roleDAO.deleteById(id);
	}

	@Override
	public void deleteByName(String name) {
		roleDAO.deleteByName(name);
	}

	@Deprecated
	@Override
	public void update(Role entity, String[] newValues) {
	}
}
