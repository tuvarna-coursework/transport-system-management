package com.tuvarna.transportsystem.tests;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.tuvarna.transportsystem.entities.User;

public class JUnitEntityTest {

	@Test
	public void testUserPasswordHashingSuccess() {
		User user = new User();

		user.setUserPassword("a");
		assertTrue(user.getUserPassword().length() > 1); // successfully hashed password
	}

	@Test
	public void testEntityFieldsNull() {
		User user = new User();
		assertNull(user.getUserFullName());
	}
}
