package com.tuvarna.transportsystem.tests;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

public class JUnitDatabaseTest {
	
	@Test
	public void testSessionCreationSuccess() {
		assertNotNull(DatabaseUtils.createSession()); // successful creation
	}
	
	@Test(timeout = 25000)
	public void testSessionCreationSpeed() {
		assertNotNull(DatabaseUtils.createSession()); // 25k ms to initialize session
	}
	
	@Test
	public void testFieldsInitializationSuccess() {
		DatabaseUtils.initFields();
	}
	
	@Test
	public void testEntireSequenceSuccess() {
		DatabaseUtils.init();
	}
	
	@Test
	public void userNameGenerationSuccess() {
		assertNotNull(DatabaseUtils.generateUserName("Pesho Peshev")); // correct
	}
}
