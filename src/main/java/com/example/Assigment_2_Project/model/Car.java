package com.example.Assigment_2_Project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "CAR")
public class Car {
    @Id
    @Column(name = "VIN")
    @GeneratedValue(strategy = GenerationType.AUTO) //Checking later
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
    private Double rateKilometer;

    @Column
    private String available;

    @OneToOne
    private Driver driver;//refresh everyday


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

    public Double getRateKilometer() {
        return rateKilometer;
    }

    public void setRateKilometer(Double rateKilometer) {
        this.rateKilometer = rateKilometer;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
