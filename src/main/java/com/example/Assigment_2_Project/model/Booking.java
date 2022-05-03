package com.example.Assigment_2_Project.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Map;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "booking_id")
    private long id;

    @CreatedDate
    @JsonIgnore
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @Column
    private String startLocation;

    @Column
    private String endLocation;

    @Column
    private ZonedDateTime pickupTime;

    @Column
    private ZonedDateTime dropTime;

    @Column
    private Double tripDistance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;


//    @Column(name = "customer_id")
//    private long customerID;

//


//    private Map<String,String> bookData;

//    @OneToOne
//    private Invoice invoice;

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

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public Customer getCustomer() { return customer;}

    public void setCustomer(Customer customer) { this.customer = customer;}

    public Car getCar() { return car; }

    public void setCar(Car car) { this.car = car; }

}
