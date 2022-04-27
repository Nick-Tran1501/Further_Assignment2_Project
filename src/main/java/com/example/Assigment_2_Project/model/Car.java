package com.example.Assigment_2_Project.model;


import javax.persistence.*;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long VIN;

    @Column
    private String make;

    @Column
    private String color;

    @Column
    private boolean convertible;

    @Column
    private double rating;

    @Column
    private String licensePlate;

    @Column
    private String rateKilometer;

    @Column
    @OneToOne
    private Driver driver;



    public Car(){}


}
