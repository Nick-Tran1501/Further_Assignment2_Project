package com.example.Assigment_2_Project.model;


import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String startLocation;

    @Column
    private String endLocation;

    @Column
    private ZonedDateTime pickupTime;

    @Column
    private ZonedDateTime dropTime;

    @Column
    private int tripDistance;

    @OneToOne
    private Invoice invoice;

    @OneToOne
    private Customer customer;

    @OneToOne
    private Driver driver;

    @Column
    private int totalPayment;

    public Booking() {
    }

    public Booking(long id, String startLocation, String endLocation, ZonedDateTime pickupTime, ZonedDateTime dropTime, int tripDistance, Invoice invoice, Customer customer, Driver driver, int totalPayment) {
        this.id = id;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.pickupTime = pickupTime;
        this.dropTime = dropTime;
        this.tripDistance = tripDistance;
        this.invoice = invoice;
        this.customer = customer;
        this.driver = driver;
        this.totalPayment = totalPayment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public ZonedDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(ZonedDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public ZonedDateTime getDropTime() {
        return dropTime;
    }

    public void setDropTime(ZonedDateTime dropTime) {
        this.dropTime = dropTime;
    }

    public int getTripDistance() {
        return tripDistance;
    }

    public void setTripDistance(int tripDistance) {
        this.tripDistance = tripDistance;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public int getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(int totalPayment) {
        this.totalPayment = totalPayment;
    }
}
