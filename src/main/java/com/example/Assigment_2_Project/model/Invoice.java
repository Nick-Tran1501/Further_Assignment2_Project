package com.example.Assigment_2_Project.model;


import javax.persistence.*;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private int totalPayment;

    public Invoice(){}
}
