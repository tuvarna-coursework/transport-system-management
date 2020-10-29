package com.tuvarna.transportsystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ticket")
public class Ticket {

    private int ticketId;

    private int ticketUserId;

    private String ticketPurchaseDate;

    private int tripId;


    public void Ticket(){

    }


    public int getTripId() { return tripId; }
    public void setTripId(int tripId) { this.tripId = tripId; }

    public String getTicketPurchaseDate() { return ticketPurchaseDate; }
    public void setTicketPurchaseDate(String ticketPurchaseDate) { this.ticketPurchaseDate = ticketPurchaseDate; }

    public int getTicketUserId() { return ticketUserId; }
    public void setTicketUserId(int ticketUserId) { this.ticketUserId = ticketUserId; }

    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }
}
