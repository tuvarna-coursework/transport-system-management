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
@Table(name = "Trip")
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

	@ManyToOne
	@JoinColumn(name = "trip_purchase_restriction_id")
	private PurchaseRestriction tripPurchaseRestriction;

	@OneToOne(mappedBy = "trip")
	private Ticket ticket; // does nothing but is mandatory. Non-owner side of relationship

	@ManyToMany(mappedBy = "trips")
	private Set<User> users;

	@OneToMany
	private List<Ticket> tickets;

	private int tripTicketAvailability;

	private Date tripDepartureDate;

	private Date tripArrivalDate;

	private int tripCapacity;

	public Trip() {

	}

	public Trip(TripType tripType, Location tripDepartureLocation, Location tripArrivalLocation, Date tripDepartureDate,
			Date tripArrivalDate, int tripCapacity, TransportType tripTransportType,
			PurchaseRestriction tripPurchaseRestriction, int tripTicketAvailability) {
		this.tripType = tripType;
		this.tripDepartureDate = tripDepartureDate;
		this.tripArrivalDate = tripArrivalDate;
		this.tripCapacity = tripCapacity;
		this.tripArrivalLocation = tripArrivalLocation;
		this.tripDepartureLocation = tripDepartureLocation;
		this.tripTransportType = tripTransportType;
		this.tripPurchaseRestriction = tripPurchaseRestriction;
		this.tripTicketAvailability = tripTicketAvailability;
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

	public PurchaseRestriction getTripPurchaseRestriction() {
		return tripPurchaseRestriction;
	}

	public void setTripPurchaseRestriction(PurchaseRestriction tripPurchaseRestriction) {
		this.tripPurchaseRestriction = tripPurchaseRestriction;
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
}