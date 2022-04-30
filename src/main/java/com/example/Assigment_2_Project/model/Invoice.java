package com.example.Assigment_2_Project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreatedDate
    @JsonIgnore
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @Column
    public ZonedDateTime dateTime;


    @Column
    private int totalPayment;

    public Invoice(){}
}
