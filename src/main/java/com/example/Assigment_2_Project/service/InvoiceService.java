package com.example.Assigment_2_Project.service;


import com.example.Assigment_2_Project.model.Invoice;
import com.example.Assigment_2_Project.repository.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;


@Service
@Transactional
public class InvoiceService {
    @Autowired
    private InvoiceRepo invoiceRepo;

    public ResponseEntity<List<Invoice>> findByPeriod(String startTime, String endTime) {
        try{
            ZonedDateTime start = ZonedDateTime.parse(startTime);
            ZonedDateTime end = ZonedDateTime.parse(endTime);
            List<Invoice> invoiceList = invoiceRepo.findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(start, end);
            return new ResponseEntity<>(invoiceList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
