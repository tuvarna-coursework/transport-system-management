package com.tuvarna.transportsystem.entities;

import java.util.List;

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
@Table(name = "\"Location\"", schema = "\"TransportSystem\"")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_id")
	private int locationId;

	@Column(name = "location_name")
	private String locationName;

	@OneToMany(mappedBy = "userLocation")
	private List<User> users; // one location can belong to multiple users

	@OneToMany(mappedBy = "routeDepartureLocation")
	private List<Route> departureLocations;

	@OneToMany(mappedBy = "routeArrivalLocation")
	private List<Route> arrivalLocations;
	
	@OneToMany(mappedBy = "departureLocation")
	private List<Ticket> departureLocationsTicket;
	
	@OneToMany(mappedBy = "arrivalLocation")
	private List<Ticket> arrivalLocationsTicket;

	@ManyToMany(mappedBy = "attachmentLocations")
	private List<Route> routes;

	public Location() {

	}

	public Location(String locationName) {
		this.locationName = locationName;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}
