package com.example.Assigment_2_Project.service;


import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.model.Driver;
import com.example.Assigment_2_Project.model.Invoice;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import com.example.Assigment_2_Project.repository.DriverRepo;
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

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private DriverRepo driverRepo;

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

    public ResponseEntity<List<Invoice>> findByCustomer(Long cusID, String startDate, String endDate){
        try {
            Customer customer  = customerRepo.findCustomerById(cusID);
            ZonedDateTime start = ZonedDateTime.parse(startDate);
            ZonedDateTime end = ZonedDateTime.parse(endDate);
            List<Invoice> invoiceList =
                    invoiceRepo.findByCustomerAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(customer, start, end);
            return invoiceList == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(invoiceList, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Invoice>> findByDriver(Long driverID, String startDate, String endDate){
        try {
            Driver driver = driverRepo.findDriverById(driverID);
            ZonedDateTime start = ZonedDateTime.parse(startDate);
            ZonedDateTime end = ZonedDateTime.parse(endDate);
            List<Invoice> invoiceList =
                    invoiceRepo.findByDriverAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(driver, start, end);
            return invoiceList == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(invoiceList, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Double> getRevenue( Long ID, String searchBy, String startDate, String endDate) {
        try{
            Double revenue = 0.0;

            ZonedDateTime start = ZonedDateTime.parse(startDate);
            ZonedDateTime end = ZonedDateTime.parse(endDate);
            if (searchBy == "customer") {
                Customer customer = customerRepo.findCustomerById(ID);
                List<Invoice> invoiceList =
                        invoiceRepo.findByCustomerAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(customer, start, end);
                for (Invoice invoice : invoiceList)
                    revenue += invoice.getTotalPayment();
            }
            if (searchBy == "driver"){
                Driver driver = driverRepo.findDriverById(ID);
                List<Invoice> invoiceList =
                        invoiceRepo.findByDriverAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(driver, start, end);
                for (Invoice invoice : invoiceList)
                    revenue += invoice.getTotalPayment();
            }
            return new ResponseEntity<>(revenue, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}


