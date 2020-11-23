package com.tuvarna.transportsystem.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "\"Trip\"", schema = "\"TransportSystem\"")
public class Trip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trip_id")
	private int tripId;

	@ManyToOne
	@JoinColumn(name = "trip_type_id")
	private TripType tripType;

	@ManyToOne /*
				 * No cascade; according to sql script you can't remove a location if it is
				 * associated with a trip/user
				 */
	@JoinColumn(name = "trip_departure_location_id") /*
														 * even though there is a mappedBy attribute, this is the owner
														 * side // mapped with One to Many since the location id can
														 * belong to multiple trips // regardless of the fact that one
														 * Trip can have only one departure location Hibernate throws
														 * exception that the PK is not unique otherwise.
														 */
	private Location tripDepartureLocation;

	@ManyToOne
	@JoinColumn(name = "trip_arrival_location_id")
	private Location tripArrivalLocation;

	/*
	 * Copied from User class:
	 * 
	 * Even though one user has one type it makes sense that this is a OneToOne
	 * relation but that is not the case. The logic is that one usertype belongs to
	 * multiple users and so hibernate will throw a non-unique key exception since
	 * it does left outer joins and the way it queries doesn't allow it. Still one
	 * User has one UserType but it is mapped in hibernate like this.
	 */

	@ManyToOne /*
				 * All below are non-modifiable if are related to a parent entity that's why no
				 * need to make things more difficult with cascading
				 */
	@JoinColumn(name = "trip_transporttype_id")
	private TransportType tripTransportType;

	@ManyToMany(mappedBy = "trips")
	private List<User> users = new ArrayList<>();

	@OneToMany(mappedBy = "trip")
	private List<Ticket> tickets;
	
	@OneToMany(mappedBy = "trip")
	private List<Request> requests;

	@Column(name = "trip_maxtickets_per_user")
	private int maxTicketsPerUser;

	@Column(name = "trip_ticket_availability")
	private int tripTicketAvailability;

	@Column(name = "trip_departure_date")
	private Date tripDepartureDate;

	@Column(name = "trip_arrival_date")
	private Date tripArrivalDate;

	@Column(name = "trip_capacity")
	private int tripCapacity;

	@Column(name = "trip_duration")
	private int tripDuration;

	@Column(name = "trip_hour_of_departure")
	private String tripDepartureHour;

	@Column(name = "trip_ticket_price")
	private double tripTicketPrice;

	public Trip() {

	}

	public Trip(TripType tripType, Location tripDepartureLocation, Location tripArrivalLocation, Date tripDepartureDate,
			Date tripArrivalDate, int tripCapacity, TransportType tripTransportType, int maxTicketsPerUser,
			int tripTicketAvailability,double tripTicketPrice,int tripDuration, String tripDepartureHour) {
		this.tripType = tripType;
		this.tripDepartureDate = tripDepartureDate;
		this.tripArrivalDate = tripArrivalDate;
		this.tripCapacity = tripCapacity;
		this.tripArrivalLocation = tripArrivalLocation;
		this.tripDepartureLocation = tripDepartureLocation;
		this.tripTransportType = tripTransportType;
		this.maxTicketsPerUser = maxTicketsPerUser;
		this.tripTicketAvailability = tripTicketAvailability;
		this.tripDuration = tripDuration;
		this.tripDepartureHour = tripDepartureHour;
		this.tripTicketPrice = tripTicketPrice;
	}

	public int getTripDuration() {
		return tripDuration;
	}

	public void setTripDuration(int tripDuration) {
		this.tripDuration = tripDuration;
	}

	public TripType getTripType() {
		return tripType;
	}

	public void setTripType(TripType tripType) {
		this.tripType = tripType;
	}

	public Location getTripDepartureLocation() {
		return tripDepartureLocation;
	}

	public void setTripDepartureLocation(Location tripDepartureLocation) {
		this.tripDepartureLocation = tripDepartureLocation;
	}

	public Location getTripArrivalLocation() {
		return tripArrivalLocation;
	}

	public void setTripArrivalLocation(Location tripArrivalLocation) {
		this.tripArrivalLocation = tripArrivalLocation;
	}

	public Date getTripDepartureDate() {
		return tripDepartureDate;
	}

	public void setTripDepartureDate(Date tripDepartureDate) {
		this.tripDepartureDate = tripDepartureDate;
	}

	public Date getTripArrivalDate() {
		return tripArrivalDate;
	}

	public void setTripArrivalDate(Date tripArrivalDate) {
		this.tripArrivalDate = tripArrivalDate;
	}

	public TransportType getTripTransportType() {
		return tripTransportType;
	}

	public void setTripTransportType(TransportType tripTransportType) {
		this.tripTransportType = tripTransportType;
	}

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public int getTripCapacity() {
		return tripCapacity;
	}

	public void setTripCapacity(int tripCapacity) {
		this.tripCapacity = tripCapacity;
	}

	public int getTripTicketAvailability() {
		return tripTicketAvailability;
	}

	public void setTripTicketAvailability(int tripTicketAvailability) {
		this.tripTicketAvailability = tripTicketAvailability;
	}

	public int getMaxTicketsPerUser() {
		return maxTicketsPerUser;
	}

	public void setMaxTicketsPerUser(int maxTicketsPerUser) {
		this.maxTicketsPerUser = maxTicketsPerUser;
	}

	public String getTripDepartureHour() {
		return tripDepartureHour;
	}

	public void setTripDepartureHour(String tripDepartureHour) {
		this.tripDepartureHour = tripDepartureHour;
	}

	public double getTripTicketPrice() {
		return tripTicketPrice;
	}

	public void setTripTicketPrice(double tripTicketPrice) {
		this.tripTicketPrice = tripTicketPrice;
	}
}