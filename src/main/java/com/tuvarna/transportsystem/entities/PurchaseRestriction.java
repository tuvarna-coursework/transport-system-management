package com.tuvarna.transportsystem.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="PurchaseRestriction")
public class PurchaseRestriction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_restriction_id")
    private int purchaseRestriction_id;

    @Column(name = "purchase_restriction_name")
    private String purchaseRestriction_name;


    public PurchaseRestriction(){

    }

    public PurchaseRestriction(String purchaseRestriction_name) {
        this.purchaseRestriction_name = purchaseRestriction_name;
    }


    public int getPurchaseRestriction_id() { return purchaseRestriction_id; }
    public void setPurchaseRestriction_id(int purchaseRestriction_id) { this.purchaseRestriction_id = purchaseRestriction_id; }

    public String getPurchaseRestriction_name() { return purchaseRestriction_name; }
    public void setPurchaseRestriction_name(String purchaseRestriction_name) { this.purchaseRestriction_name = purchaseRestriction_name; }
}
