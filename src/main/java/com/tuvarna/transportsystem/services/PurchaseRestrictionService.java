package com.tuvarna.transportsystem.services;

import java.util.List;

import com.tuvarna.transportsystem.dao.PurchaseRestrictionDAO;
import com.tuvarna.transportsystem.entities.PurchaseRestriction;

public class PurchaseRestrictionService implements CrudService<PurchaseRestriction> {
	private PurchaseRestrictionDAO restrictionDAO;

	public PurchaseRestrictionService() {
		this.restrictionDAO = new PurchaseRestrictionDAO();
	}

	@Override
	public PurchaseRestriction getById(int id) {
		return restrictionDAO.getById(id);
	}

	@Override
	public List<PurchaseRestriction> getByName(String name) {
		return restrictionDAO.getByName(name);
	}

	@Override
	public List<PurchaseRestriction> getAll() {
		return restrictionDAO.getAll();
	}

	@Override
	public void save(PurchaseRestriction restriction) {
		restrictionDAO.save(restriction);
	}

	@Override
	public void updateName(PurchaseRestriction restriction, String newValue) {
		restrictionDAO.updateName(restriction, newValue);
	}

	@Override
	public void deleteById(int id) {
		restrictionDAO.deleteById(id);
	}

	@Override
	public void deleteByName(String name) {
		restrictionDAO.deleteByName(name);
	}

	@Deprecated
	@Override
	public void update(PurchaseRestriction restriction, String[] newValues) {
	}
}
