package com.tuvarna.transportsystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserTrip")
public class UserTrip {

    private int userId;
    private int tripId;
    private String isOrganizer;
    private String isDistributor;

    public void UserTrip(){

    }


    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getTripId() { return tripId; }
    public void setTripId(int tripId) { this.tripId = tripId; }

    public String getIsOrganizer() { return isOrganizer; }
    public void setIsOrganizer(String isOrganizer) { this.isOrganizer = isOrganizer; }

    public String getIsDistributor() { return isDistributor; }
    public void setIsDistributor(String isDistributor) { this.isDistributor = isDistributor; }
}
