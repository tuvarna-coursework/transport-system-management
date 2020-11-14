package com.tuvarna.transportsystem.entities;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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

import org.mindrot.jbcrypt.BCrypt;


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

	@OneToOne(orphanRemoval = true, cascade = CascadeType.REMOVE) /*
																	 * Any profile that is no longer associated with
																	 * user will be deleted (since a userprofile is
																	 * unique for 1 user only
																	 */
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

	@ManyToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }) /*
									 * Can't use ALL for a bidirectional many-to-many table since the REMOVE will
									 * always remove more records than it should
									 */
	@JoinTable(name = "UserRole", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private List<Role> roles = new ArrayList<>(); // owner side of UserRole join table: user_id (PK) role_id (FK)

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "UserTrip", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "trip_id") })
	private List<Trip> trips = new ArrayList<>();

	/*
	 * This has been converted to a ManyToMany relation since the user will have
	 * multiple tickets and a ticket will belong to multiple people.
	 */
	@ManyToMany
	@JoinTable(name = "UserTicket", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "ticket_id") })
	private List<Ticket> tickets = new ArrayList<>(); // this is like an instance of the UserTicket table

	public User() {

	}

	public User(String userFullName, String userLoginName, String userPassword, UserProfile userProfile,
			UserType userType, Location userLocation) {
		this.setUserFullName(userFullName);
		this.setUserLoginName(userLoginName);
		this.setUserPassword(userPassword);
		this.setUserProfile(userProfile);
		this.setUserType(userType);
		this.setUserLocation(userLocation);
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Trip> getTrips() {
		return trips;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
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
		/* The salt used contains 4096 iterations (2^12) */
		this.userPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt(12));
	}
}
