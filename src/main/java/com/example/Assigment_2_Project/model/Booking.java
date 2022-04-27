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


}
