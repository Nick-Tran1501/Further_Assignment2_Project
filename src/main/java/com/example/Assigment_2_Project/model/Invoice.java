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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(int totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Invoice(long id, int totalPayment) {
        this.id = id;
        this.totalPayment = totalPayment;
    }

    public Invoice(){}
}
