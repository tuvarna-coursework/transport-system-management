package com.tuvarna.transportsystem.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Users") /* User is a reserved word in PostgreSQL and needs to be escaped */
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "user_fullname")
	private String userFullName;

	@Column(name = "user_loginname")
	private String userLoginName;

	@Column(name = "user_password")
	private String userPassword;

	@OneToOne
	@JoinColumn(name = "userprofile_id", referencedColumnName = "userprofile_id")
	private UserProfile userProfile;

	/*
	 * Even though one user has one type it makes sense that this is a OneToOne
	 * relation but that is not the case. The logic is that one usertype belongs to
	 * multiple users and so hibernate will throw a non-unique key exception since
	 * it does left outer joins and the way it queries doesn't allow it. Still one
	 * User has one UserType but it is mapped in hibernate like this.
	 */

	@ManyToOne
	@JoinColumn(name = "usertype_id")
	private UserType userType;

	@ManyToOne
	@JoinColumn(name = "user_location_id")
	private Location userLocation;

	@ManyToMany
	@JoinTable(name = "UserRole", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private List<Role> roles; // owner side of UserRole join table: user_id (PK) role_id (FK)

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "UserTrip", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "trip_id") })
	private Set<Trip> trips;

	/*
	 * This has been converted to a ManyToMany relation since the user will have
	 * multiple tickets and a ticket will belong to multiple people.
	 */
	@ManyToMany
	@JoinTable(name = "UserTicket", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "ticket_id") })
	private List<Ticket> tickets = new ArrayList<>();

	public User() {

	}

	public User(String userFullName, String userLoginName, String userPassword, UserProfile userProfile,
			UserType userType, Location userLocation) {
		this.setUserFullName(userFullName);
		this.setUserLoginName(userFullName);
		this.setUserPassword(userPassword);
		this.setUserProfile(userProfile);
		this.setUserType(userType);
		this.setUserLocation(userLocation);
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Location getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(Location userLocation) {
		this.userLocation = userLocation;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
