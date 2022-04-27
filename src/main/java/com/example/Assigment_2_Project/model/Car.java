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


}
