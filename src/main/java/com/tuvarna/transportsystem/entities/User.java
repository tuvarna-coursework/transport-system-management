package com.tuvarna.transportsystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User {

    private int userId;
    private String userFullName;
    private String userLoginName;
    private String userPassword;
    private int userProfileId;
    private int userTypeId;
    private int userLocationId;

    public void User(){

    }


    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUserFullName() { return userFullName; }
    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }

    public String getUserLoginName() { return userLoginName; }
    public void setUserLoginName(String userLoginName) { this.userLoginName = userLoginName; }

    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    public int getUserProfileId() { return userProfileId; }
    public void setUserProfileId(int userProfileId) { this.userProfileId = userProfileId; }

    public int getUserTypeId() { return userTypeId; }
    public void setUserTypeId(int userTypeId) { this.userTypeId = userTypeId; }

    public int getUserLocationId() { return userLocationId; }
    public void setUserLocationId(int userLocationId) { this.userLocationId = userLocationId; }
}
