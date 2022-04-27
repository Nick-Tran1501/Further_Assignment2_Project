package com.example.Assigment_2_Project.model;


import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;


    public Customer(){}

}
