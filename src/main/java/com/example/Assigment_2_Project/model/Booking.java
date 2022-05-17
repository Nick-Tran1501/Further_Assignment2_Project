package com.example.Assigment_2_Project.model;


import com.fasterxml.jackson.annotation.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "booking")
public class Booking {

    // Booking attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "booking_id")
    private long id;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private ZonedDateTime createdDate = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime pickupTime;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime dropTime;

    @Column
    private String startLocation;

    @Column
    private String endLocation;

    @Column
    private Double tripDistance;

    @Column
    private String status;

    //Relationship between booking and customer
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    //Relationship between booking and car
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    //Relationship between booking and invoice
    @OneToOne(cascade = CascadeType.ALL)
    private Invoice invoice;

    // Constructor
    public Booking() {
    }


    //Getter setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
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

    public Double getTripDistance() {
        return tripDistance;
    }

    public void setTripDistance(Double tripDistance) {
        this.tripDistance = tripDistance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
