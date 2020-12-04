package com.tuvarna.transportsystem.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "\"Ticket\"", schema = "\"TransportSystem\"")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	private int ticketId;

	@Column(name = "ticket_purchasedate")
	private Date ticketPurchaseDate;

	@ManyToMany(mappedBy = "tickets")
	private List<User> users; 
	
	@ManyToOne
	@JoinColumn(name = "trip_id")
	private Trip trip; // One trip can occur on multiple tickets
	
	@ManyToOne
	@JoinColumn(name = "ticket_departurelocation_id")
	private Location departureLocation;
	
	@ManyToOne
	@JoinColumn(name = "ticket_arrivallocation_id")
	private Location arrivalLocation;
	
	public Ticket() {

	}

	/*
	 * NOTE that User is a foreign key and ONLY the ID will be persisted into the
	 * database
	 */
	public Ticket(Date ticketPurchaseDate, Trip trip, Location departure, Location arrival) {
		this.setTicketPurchaseDate(ticketPurchaseDate);
		this.setTrip(trip);
		this.setDepartureLocation(departure);
		this.setArrivalLocation(arrival);
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Date getTicketPurchaseDate() {
		return ticketPurchaseDate;
	}

	public void setTicketPurchaseDate(Date ticketPurchaseDate) {
		this.ticketPurchaseDate = ticketPurchaseDate;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public Location getDepartureLocation() {
		return departureLocation;
	}

	public void setDepartureLocation(Location departureLocation) {
		this.departureLocation = departureLocation;
	}

	public Location getArrivalLocation() {
		return arrivalLocation;
	}

	public void setArrivalLocation(Location arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ticketId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (ticketId != other.ticketId)
			return false;
		return true;
	}
}
