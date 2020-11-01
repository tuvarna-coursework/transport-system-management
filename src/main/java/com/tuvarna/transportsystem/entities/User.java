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

	@OneToOne
	@JoinColumn(name = "usertype_id", referencedColumnName = "usertype_id")
	private UserType userType;

	@OneToOne
	@JoinColumn(name = "user_location_id", referencedColumnName = "location_id")
	private Location userLocation;

	@ManyToMany
	@JoinTable(name = "UserRole", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private List<Role> roles; // owner side of UserRole join table: user_id (PK) role_id (FK)

	@ManyToMany
	@JoinTable(name = "UserTrip", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "trip_id") })
	private List<Trip> trips = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Ticket> tickets = new ArrayList<>();

	public User() {

	}

	/* Test constructor */
	public User(String userFullName, String userLoginName, String userPassword, UserProfile userProfile,
			UserType userType, Location userLocation) {
		this.setUserFullName(userFullName);
		this.setUserLoginName(userFullName);
		this.setUserPassword(userPassword);
		this.setUserProfile(userProfile);
		this.setUserType(userType);
		this.userLocation = userLocation;
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

	@Column(name = "user_location_id")
	public Location getUserLocation() {
		return userLocation;
	}

	@Column(name = "user_location_id")
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
