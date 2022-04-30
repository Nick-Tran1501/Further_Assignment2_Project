package com.example.Assigment_2_Project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "customer")
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreatedDate
    @JsonIgnore
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String address;

//    @OneToMany(mappedBy = "customer")
////    @JoinColumn(name = "customer", referencedColumnName = "name")
//    private List<Booking> booking;

    public Customer() {};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public List<Booking> getBooking() {
//        return booking;
//    }
//
//    public void setBooking(List<Booking> booking) {
//        this.booking = booking;
//    }

}
