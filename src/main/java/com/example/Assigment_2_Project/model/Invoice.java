package com.example.Assigment_2_Project.model;


import com.fasterxml.jackson.annotation.*;
import com.sun.source.tree.LambdaExpressionTree;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "invoice")
public class Invoice {

    // Invoice attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "invoice_id")
    private long id;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private ZonedDateTime createdDate = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @Column
    private Double totalPayment;

    // Relationship between invoices and customer
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    // Relationship between invoices and driver
    @ManyToOne(cascade = CascadeType.ALL)
    private Driver driver;

    //Relationship between booking and invoice
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Booking booking;


    // Constructor
    public Invoice(){}

    // Getter setter
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }
}
