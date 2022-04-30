package com.example.Assigment_2_Project.model;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Map;

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

    private Map<Object,Object> bookData;

    @Column
    private String customerName;

    @Column
    private String serviceCar;



    @OneToOne
    private Invoice invoice;

    @OneToOne
    private Customer customer;

    @OneToOne
    private Driver driver;

    @OneToOne
    private Car car;

    public Booking() {}

//  Get & Set Customers
    public Customer getCustomer() {return customer;}
    public void setCustomer(Customer customer) {this.customer = customer;}
//  Get & Set Car
    public Car getCar() {return car;}
    public void setCar(Car car) {this.car = car;}
//  book get & set
    public Map<Object,Object> getBookData() { return bookData; }
    public void setBookData(Map<Object,Object> bookData) { this.bookData = bookData; }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getServiceCar() {
        return serviceCar;
    }

    public void setServiceCar(String serviceCar) {
        this.serviceCar = serviceCar;
    }
}
