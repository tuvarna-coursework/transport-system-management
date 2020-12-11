package com.tuvarna.transportsystem.tests;

import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;

import com.tuvarna.transportsystem.dao.LocationDAO;
import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class JUnitDAOTest {
	
	@Test
	public void createNewDAO() {
		LocationDAO locationDAO = new LocationDAO();
	}
	
	@Test(expected = PersistenceException.class)
	public void persistNullEntityTest() {
		DatabaseUtils.init();
		
		LocationDAO locationDAO = new LocationDAO();
		locationDAO.save(new Location()); // location_name is null and throws exception
	}
	
	@Test
	public void persistEntitySuccess() {
		DatabaseUtils.init();
		LocationDAO locationDAO = new LocationDAO();
		locationDAO.save(new Location("Varna2"));
	}
}
