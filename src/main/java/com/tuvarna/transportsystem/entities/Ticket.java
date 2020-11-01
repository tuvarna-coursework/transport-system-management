package com.tuvarna.transportsystem.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user; // owner side: name = foreign key in ticket; referencedColumnName = primary key
						// in Users
	@ManyToOne
	@JoinColumn(name = "trip_id")
	private Trip trip; // owner side: name = foreign key in Ticket; referencedColumnName = primary key
						// in Trip

	public Ticket() {

	}

	/*
	 * NOTE that User is a foreign key and ONLY the ID will be persisted into the
	 * database
	 */
	public Ticket(Date ticketPurchaseDate, User user, Trip trip) {
		this.setTicketPurchaseDate(ticketPurchaseDate);
		this.setUser(user);
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
}
