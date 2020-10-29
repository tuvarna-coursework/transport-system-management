package com.tuvarna.transportsystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Trip")
public class Trip {

    private int tripId;
    private int tripTypeId;
    private int tripDepartureLocationId;
    private int tripArrivalLocationId;
    private String tripDepartureDate;
    private String tripArrivalDate;
    private int tripCapacity;
    private int tripTransportTypeId;
    private int tripPurchaseRestrictionId;
    private int tripTicketAvailability;

    public void Trip(){

    }




    public int getTripId() { return tripId; }
    public void setTripId(int tripId) { this.tripId = tripId; }

    public int getTripTypeId() { return tripTypeId; }
    public void setTripTypeId(int tripTypeId) { this.tripTypeId = tripTypeId; }

    public int getTripDepartureLocationId() { return tripDepartureLocationId; }
    public void setTripDepartureLocationId(int tripDepartureLocationId) { this.tripDepartureLocationId = tripDepartureLocationId; }

    public int getTripArrivalLocationId() { return tripArrivalLocationId; }
    public void setTripArrivalLocationId(int tripArrivalLocationId) { this.tripArrivalLocationId = tripArrivalLocationId; }

    public String getTripDepartureDate() { return tripDepartureDate; }
    public void setTripDepartureDate(String tripDepartureDate) { this.tripDepartureDate = tripDepartureDate; }

    public String getTripArrivalDate() { return tripArrivalDate; }
    public void setTripArrivalDate(String tripArrivalDate) { this.tripArrivalDate = tripArrivalDate; }

    public int getTripCapacity() { return tripCapacity; }
    public void setTripCapacity(int tripCapacity) { this.tripCapacity = tripCapacity; }

    public int getTripTransportTypeId() { return tripTransportTypeId; }
    public void setTripTransportTypeId(int tripTransportTypeId) { this.tripTransportTypeId = tripTransportTypeId; }

    public int getTripPurchaseRestrictionId() { return tripPurchaseRestrictionId; }
    public void setTripPurchaseRestrictionId(int tripPurchaseRestrictionId) { this.tripPurchaseRestrictionId = tripPurchaseRestrictionId; }

    public int getTripTicketAvailability() { return tripTicketAvailability; }
    public void setTripTicketAvailability(int tripTicketAvailability) { this.tripTicketAvailability = tripTicketAvailability; }
}
