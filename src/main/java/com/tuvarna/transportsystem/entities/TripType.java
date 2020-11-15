package com.tuvarna.transportsystem.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "\"TripType\"", schema = "\"TransportSystem\"")
public class TripType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment ID sequence
	@Column(name = "triptype_id")
	private int tripTypeId;

	@Column(name = "triptype_name")
	private String tripTypeName;

	@OneToMany(mappedBy = "tripType")
	private List<Trip> trips;

	public TripType() {

	}

	public TripType(String tripTypeName) {
		this.tripTypeName = tripTypeName;
	}

	public int getTripTypeId() {
		return tripTypeId;
	}

	public void setTripTypeId(int tripTypeId) {
		this.tripTypeId = tripTypeId;
	}

	public String getTripTypeName() {
		return tripTypeName;
	}

	public void setTripTypeName(String tripTypeName) {
		this.tripTypeName = tripTypeName;
	}
}
