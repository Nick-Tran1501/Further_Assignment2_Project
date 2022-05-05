package com.example.Assigment_2_Project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "invoice_id")
    private long id;

    @CreatedDate
    @JsonIgnore
    private ZonedDateTime createdDate = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);

//    @Column
//    public ZonedDateTime dateTime;


    @Column
    private int totalPayment;

    public Invoice(){}
}
