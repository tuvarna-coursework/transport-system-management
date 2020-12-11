package com.tuvarna.transportsystem.tests;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.tuvarna.transportsystem.utils.NotificationUtils;

public class JUnitNotificationsTest {

	@Test
	public void testInit() {
		NotificationUtils.init();
	}
	
	@Test(expected = NullPointerException.class)
	public void testGenerateNewTripNotificationNull() {
		NotificationUtils.generateNewTripNotification(null);
	}
	
	@Test
	public void testGenerateTicketReport() {
		assertFalse(NotificationUtils.generateTicketReport()); // current user is null
	}
	
	@Test(expected = NullPointerException.class)
	public void testGenerateUnsoldTicketsForNearTrip() {
		NotificationUtils.generateUnsoldTicketsForNearTrip(); // no session  to create dao
	}
}
