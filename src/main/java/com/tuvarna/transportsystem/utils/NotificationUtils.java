package com.tuvarna.transportsystem.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.tuvarna.transportsystem.entities.Notification;
import com.tuvarna.transportsystem.entities.Trip;
import com.tuvarna.transportsystem.services.NotificationService;
import com.tuvarna.transportsystem.services.TicketService;
import com.tuvarna.transportsystem.services.TripService;
import com.tuvarna.transportsystem.services.UserService;

public class NotificationUtils {
	public static Timer timer;

	private static int ONEDAY_IN_MILISECONDS = 86400000;

	public static void init() {
		timer = new Timer();

		TimerTask task = new TimerTask() {
			@Override
			public void run() {

				generateUnsoldTicketsForNearTrip();

				/*
				 * Logged in user is not a transport company and no report needs to be generated
				 */
				if (!NotificationUtils.generateTicketReport()) {
					return;
				}
				;
			};
		};

		/*
		 * long value is 1 day; every day (since the start of the program) a report for
		 * the sold tickets will be generated
		 */
		timer.schedule(task, new Date(), ONEDAY_IN_MILISECONDS);
	}

	public static void generateNewTripNotification(Trip trip) {
		Notification notification = new Notification();
		UserService userService = new UserService();

		notification.setSender(userService.getUserByTripId(trip.getTripId()).get());
		notification.setReceiver(userService.getByUserType("Distributor").get(0));

		StringBuilder sb = new StringBuilder();

		sb.append("New trip (More info in Schedule) ").append("Route: ")
				.append(trip.getRoute().getRouteDepartureLocation().getLocationName()).append(" - ")
				.append(trip.getRoute().getRouteArrivalLocation().getLocationName()).append(" Date: ")
				.append(trip.getTripDepartureDate().toString()).append(" Hour: ").append(trip.getTripDepartureHour());

		notification.setMessage(sb.toString());

		new NotificationService().save(notification);
	}

	public static boolean generateTicketReport() {
		/*
		 * Will return false if the user is not a company and TimerTask will not be
		 * executed
		 */
		if (!DatabaseUtils.currentUser.getUserType().getUserTypeName().equals("Transport Company")) {
			System.out.println("DEBUG: User not a company.");
			return false;
		}

		StringBuilder sb = new StringBuilder();
		TicketService ticketService = new TicketService();

		for (Trip trip : DatabaseUtils.currentUser.getTrips()) {
			sb = new StringBuilder();
			sb.append("Ticket report: For trip ").append(trip.getRoute().getRouteDepartureLocation().getLocationName())
					.append(" - ").append(trip.getRoute().getRouteArrivalLocation().getLocationName()).append(" ")
					.append(String.valueOf(ticketService.getByTrip(trip.getTripId()).size()))
					.append(" tickets were sold. ").append(" (").append(new Date()).append(" )");

			Notification notification = new Notification(DatabaseUtils.currentUser, DatabaseUtils.currentUser,
					sb.toString());
			new NotificationService().save(notification);
		}

		System.out.println("DEBUG: Provided ticket report.");

		return true;

	}

	public static void generateCancelledTripNotification(Trip trip) {
		StringBuilder sb = new StringBuilder();
		UserService userService = new UserService();

		sb.append("Trip cancelled: ").append(trip.getRoute().getRouteDepartureLocation().getLocationName())
				.append(" - ").append(trip.getRoute().getRouteArrivalLocation().getLocationName())
				.append(" ; Attachment locations: ");

		trip.getRoute().getAttachmentLocations().forEach(l -> sb.append(l.getLocation().getLocationName() + ", "));
		sb.deleteCharAt(sb.length() - 1); // delete last comma delimiter

		Notification notification = new Notification(DatabaseUtils.currentUser,
				userService.getByUserType("Distributor").get(0), sb.toString());

		new NotificationService().save(notification);
	}

	public static void generateUnsoldTicketsForNearTrip() {
		TripService tripService = new TripService();
		UserService userService = new UserService();

		List<Trip> trips = tripService.getAll();
		Date dateNow = new Date();

		for (Trip trip : trips) {
			StringBuilder sb = new StringBuilder();

			/*
			 * If there is less than one day until this trip departures and there are unsold
			 * tickets, generate notification for company and distributor
			 */
			if (trip.getTripDepartureDate().getTime() - dateNow.getTime() <= ONEDAY_IN_MILISECONDS) {
				if (trip.getTripTicketAvailability() > 0) {

					sb.append("There is a trip with unsold tickets coming up soon. Route: ")
							.append(trip.getRoute().getRouteDepartureLocation().getLocationName()).append(" - ")
							.append(trip.getRoute().getRouteArrivalLocation().getLocationName())
							.append("; Attachment locations: ");

					trip.getRoute().getAttachmentLocations()
							.forEach(l -> sb.append(l.getLocation().getLocationName() + ", "));
					sb.deleteCharAt(sb.length() - 1); // delete last comma delimiter

					sb.append(" Tickets left: ").append(trip.getTripTicketAvailability());

					Notification notificationForCompany = new Notification();

					/* Receiver is the company, the sender too (it doesn't matter in this case ) */
					notificationForCompany.setReceiver(userService.getUserByTripId(trip.getTripId()).get());
					notificationForCompany.setSender(userService.getUserByTripId(trip.getTripId()).get());
					notificationForCompany.setMessage(sb.toString());

					/*
					 * Receiver and sender is distributor. Hard coded to have only one distributor
					 * right now
					 */
					Notification notificationForDistributor = new Notification();
					notificationForDistributor.setReceiver(userService.getByUserType("Distributor").get(0));
					notificationForDistributor.setSender(userService.getByUserType("Distributor").get(0));
					notificationForDistributor.setMessage(sb.toString());

					NotificationService notificationService = new NotificationService();
					notificationService.save(notificationForCompany);
					notificationService.save(notificationForDistributor);

					System.out.println("DEBUG: Incoming trip with unsold tickets notification");
				}
			}
		}
	}
}
