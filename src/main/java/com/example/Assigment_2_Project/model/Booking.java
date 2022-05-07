package com.example.Assigment_2_Project.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
@JsonIgnoreProperties(  {"handler","hibernateLazyInitializer"} )
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "booking_id")
    private long id;

    @CreatedDate
    @JsonIgnore
    private ZonedDateTime createdDate = ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES);

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

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

//    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//    @JsonIgnore
//    private Customer customer;

//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JsonIgnore
//    private Customer customer;

//    @OneToOne
//    private Driver driver;

    public Booking() {

    }

//    Get & Set date time
//    public String getPickupTime() {
//        return new SimpleDateFormat("dd/MM/yyyy").format(pickupTime);
//    }


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
