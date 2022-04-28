package com.example.Assigment_2_Project.model;


import javax.persistence.*;

@Entity
@Table(name = "CAR")
public class Car {
    @Id
    @Column(name = "VIN")
    @GeneratedValue(strategy = GenerationType.AUTO) //Checking later
    private long id;

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
    private String rateKilometer;

    @OneToOne
    private Driver driver;//refresh everyday


    public Car(){}

    public Car(Long id, String make, String model, String color, boolean convertible, double rating, String licensePlate, String rateKilometer) {
        this.model = model;
        this.id = id;
        this.make = make;
        this.color = color;
        this.convertible = convertible;
        this.rating = rating;
        this.licensePlate = licensePlate;
        this.rateKilometer = rateKilometer;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getRateKilometer() {
        return rateKilometer;
    }

    public void setRateKilometer(String rateKilometer) {
        this.rateKilometer = rateKilometer;
    }


}
