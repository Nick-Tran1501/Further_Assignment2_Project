package com.example.Assigment_2_Project.model;


import com.fasterxml.jackson.annotation.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "CAR")

public class Car {
    @Id
    @Column(name = "VIN")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "car_id") //Checking later
    private long id;

    @CreatedDate
    @JsonIgnore
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @Column
    private String make;

    @Column
    private String color;

    @Column
    private String model;

    @Column
    private boolean convertible;

    @Column
    private double rating;

    @Column
    private String licensePlate;

    @Column
    private double rateKilometer;

    @Column
    private boolean available;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver; //refresh everyday

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Booking booking;

    public Car(){}

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

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isConvertible() {
        return convertible;
    }

    public void setConvertible(boolean convertible) {
        this.convertible = convertible;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getRateKilometer() {
        return rateKilometer;
    }

    public void setRateKilometer(double rateKilometer) {
        this.rateKilometer = rateKilometer;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
