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
@Table(name = "Ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	private int ticketId;

	private Date ticketPurchaseDate;

	@ManyToMany(mappedBy = "tickets")
	private List<User> users; 
	
	@ManyToOne
	@JoinColumn(name = "trip_id")
	private Trip trip; // One trip can occur on multiple tickets

	public Ticket() {

	}

	/*
	 * NOTE that User is a foreign key and ONLY the ID will be persisted into the
	 * database
	 */
	public Ticket(Date ticketPurchaseDate, Trip trip) {
		this.setTicketPurchaseDate(ticketPurchaseDate);
		this.setTrip(trip);
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
}
