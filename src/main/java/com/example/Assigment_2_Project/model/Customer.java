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

    public Customer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Customer(){}

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
}
