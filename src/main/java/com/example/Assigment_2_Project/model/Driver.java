package com.example.Assigment_2_Project.model;

import javax.persistence.*;


@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private int licenseNumber;

    @Column
    private int phoneNumber;

    @Column
    private double rating;

    @OneToOne
    private Car car; //Refresh everyday

    public Driver() {}

}
