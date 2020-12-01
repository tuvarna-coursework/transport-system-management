package com.tuvarna.transportsystem.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "\"Route\"", schema = "\"TransportSystem\"")
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "route_id")
	private int routeId;

	@ManyToOne
	@JoinColumn(name = "route_departurelocation_id")
	private Location routeDepartureLocation; /* A location will appear in multiple routes */

	@ManyToOne
	@JoinColumn(name = "route_arrivallocation_id")
	private Location routeArrivalLocation;

	@OneToMany(mappedBy="route")
	private List<RouteAttachment> attachmentLocations = new ArrayList<>(); // owner side of many to many RouteAttachment joined table

	@OneToMany(mappedBy = "route")
	private List<Trip> trips;

	public Route() {
	}

	public Route(Location routeDepartureLocation, Location routeArrivalLocation) {
		this.routeDepartureLocation = routeDepartureLocation;
		this.routeArrivalLocation = routeArrivalLocation;
	}

	public List<RouteAttachment> getAttachmentLocations() {
		return attachmentLocations;
	}

	public void setAttachmentLocations(List<RouteAttachment> attachmentLocations) {
		this.attachmentLocations = attachmentLocations;
	}

	public List<Trip> getTrips() {
		return trips;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public Location getRouteDepartureLocation() {
		return routeDepartureLocation;
	}

	public void setRouteDepartureLocation(Location routeDepartureLocations) {
		this.routeDepartureLocation = routeDepartureLocations;
	}

	public Location getRouteArrivalLocation() {
		return routeArrivalLocation;
	}

	public void setRouteArrivalLocation(Location routeArrivalLocations) {
		this.routeArrivalLocation = routeArrivalLocations;
	}

}
