package com.tuvarna.transportsystem.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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

	@OneToOne
	@JoinColumn(name = "trip_type_id", referencedColumnName = "triptype_id")
	private TripType tripType;

	@OneToOne
	@JoinColumn(name = "trip_departure_location_id", referencedColumnName = "location_id")
	private Location tripDepartureLocation;

	@OneToOne
	@JoinColumn(name = "trip_arrival_location_id", referencedColumnName = "location_id")
	private Location tripArrivalLocation;

	@OneToOne
	@JoinColumn(name = "trip_transporttype_id", referencedColumnName = "transport_type_id")
	private TransportType tripTransportType;

	@OneToOne
	@JoinColumn(name = "trip_purchase_restriction_id", referencedColumnName = "purchase_restriction_id")
	private PurchaseRestriction tripPurchaseRestriction;

	@OneToOne(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
	private Ticket ticket; // does nothing but is mandatory. Non-owner side of relationship

	@ManyToMany(mappedBy = "trips")
	private List<User> users;
	
	@OneToMany(mappedBy = "trip")
	private List<Ticket> tickets = new ArrayList<>();

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
		this.tripDepartureLocation = tripDepartureLocation;
		this.tripArrivalLocation = tripArrivalLocation;
		this.tripDepartureDate = tripDepartureDate;
		this.tripArrivalDate = tripArrivalDate;
		this.tripCapacity = tripCapacity;
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
