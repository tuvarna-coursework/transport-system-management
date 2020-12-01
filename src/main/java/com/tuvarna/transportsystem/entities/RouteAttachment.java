package com.tuvarna.transportsystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "\"RouteAttachment\"", schema = "\"TransportSystem\"")
public class RouteAttachment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "route_attachment_id")
	private int id; // mandatory for hibernate even though our table is just two foreign keys

	/*
	 * Many to many joined table but we need the extra hour of arrival for every
	 * attachment station that's why this had to be extracted as a separate entity,
	 * otherwise, a extra column cannot be added.
	 */

	@ManyToOne
	@JoinColumn(name = "route_id")
	private Route route;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	@Column(name = "hour_of_arrival")
	private String hourOfArrival;

	public RouteAttachment() {
	}

	public RouteAttachment(Route route, Location location, String hourOfArrival) {
		this.route = route;
		this.location = location;
		this.hourOfArrival = hourOfArrival;
	}
	
	public RouteAttachment(int id, Route route, Location location, String hourOfArrival) {
		this.id = id;
		this.route = route;
		this.location = location;
		this.hourOfArrival = hourOfArrival;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getHourOfArrival() {
		return hourOfArrival;
	}

	public void setHourOfArrival(String hourOfArrival) {
		this.hourOfArrival = hourOfArrival;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
