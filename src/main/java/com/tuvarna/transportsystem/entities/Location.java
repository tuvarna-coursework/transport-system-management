package com.tuvarna.transportsystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Location")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_id")
	private int locationId;

	@Column(name = "location_name")
	private String locationName;

	@OneToOne(mappedBy = "userLocation")
	private User user;

	@OneToOne(mappedBy = "tripDepartureLocation")
	private Trip tripDeparture;

	@OneToOne(mappedBy = "tripArrivalLocation")
	private Trip tripArrival;

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
