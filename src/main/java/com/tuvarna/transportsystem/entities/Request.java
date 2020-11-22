package com.tuvarna.transportsystem.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "\"Request\"", schema = "\"TransportSystem\"")
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment ID sequence
	@Column(name = "request_id")
	private int requestId;

	@Column(name = "ticket_quantity")
	private int ticketsQuantity;

	@ManyToOne
	@JoinColumn(name = "request_trip_id")
	private Trip trip;
	
	public Request() {}

	public Request(int ticketsQuantity, Trip trip) {
		this.ticketsQuantity = ticketsQuantity;
		this.trip = trip;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getTicketsQuantity() {
		return ticketsQuantity;
	}

	public void setTicketsQuantity(int ticketsQuantity) {
		this.ticketsQuantity = ticketsQuantity;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	} 
}
