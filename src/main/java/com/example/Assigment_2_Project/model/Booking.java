package com.example.Assigment_2_Project.model;


import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @CreatedDate
//    @JsonIgnore
//    private ZonedDateTime createdDate = ZonedDateTime.now();
//
//    @Column
//    private String startLocation;
//
//    @Column
//    private String endLocation;
//
//    @Column
//    private ZonedDateTime pickupTime;
//
//    @Column
//    private ZonedDateTime dropTime;
//
//    @Column
//    private int tripDistance;
//
//    @OneToOne
//    private Invoice invoice;

//    @ManyToOne
//    @Column
//    private ResponseEntity<List<Customer>> customer;

//    @Column(name = "customerName")
//    private ResponseEntity<List<Customer>> name;

//    @OneToOne
//    private Car car;

    public Booking() {
    }


//  Get & Set Customers
//    public ResponseEntity<List<Customer>> getCustomer() {return customer;}
//    public void setCustomer(ResponseEntity<List<Customer>> customer) {this.customer = customer;}
//
//    public ResponseEntity<List<Customer>> getName() {
//        return name;
//    }
//
//    public void setName(ResponseEntity<List<Customer>> name) {
//        this.name = name;
//    }

    ////  Get & Set Car
//    public Car getCar() {return car;}
//    public void setCar(Car car) {this.car = car;}


}
