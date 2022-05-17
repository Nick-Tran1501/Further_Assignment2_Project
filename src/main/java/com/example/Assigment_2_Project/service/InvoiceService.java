package com.example.Assigment_2_Project.service;


import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.model.Driver;
import com.example.Assigment_2_Project.model.Invoice;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import com.example.Assigment_2_Project.repository.DriverRepo;
import com.example.Assigment_2_Project.repository.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    //Method to set total payment to zero if the booking is cancelled
    public void setTotalPayment(Invoice invoice) {
        invoice.setTotalPayment(0.0);
        invoiceRepo.save(invoice);
    }

    // Method to create invoices and save to repository (Called in booking service)
    public Invoice addInvoice(Customer customer, Driver driver, Double rateKilometer, String tripDistance) {
            Invoice invoice =  new Invoice();
            Double totalPayment = rateKilometer * Double.parseDouble(tripDistance);
            invoice.setCustomer(customer);
            invoice.setDriver(driver);
            invoice.setTotalPayment(totalPayment);
            driver.getInvoiceList().add(invoice);
            customer.getInvoiceList().add(invoice);
            invoiceRepo.save(invoice);
            return invoice;
    }

    // Method to get all invoices, invoices by customers, invoices by drivers in a periods
    public ResponseEntity<List<Invoice>> findInvoice(String searchBy, Long ID, String startDate, String endDate){
        try {
            String time = "T00:00:00.000Z";
            String startTime = startDate + time;
            String endTime = endDate + time;
            ZonedDateTime start = ZonedDateTime.parse(startTime);
            ZonedDateTime end = ZonedDateTime.parse(endTime);
            if (searchBy.equalsIgnoreCase("customer")){
                Customer customer  = customerRepo.findCustomerById(ID);
                List<Invoice> invoiceList =
                        invoiceRepo.findByCustomerAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(customer, start, end);
                if (invoiceList.isEmpty()){
                    return new ResponseEntity<>(invoiceList,HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(invoiceList, HttpStatus.FOUND);
            }
            if (searchBy.equalsIgnoreCase("driver")){
                Driver driver = driverRepo.findDriverById(ID);
                List<Invoice> invoiceList =
                        invoiceRepo.findByDriverAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(driver, start, end);
                if (invoiceList.isEmpty()){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(invoiceList, HttpStatus.FOUND);
            }
            if (searchBy.equalsIgnoreCase("all")){
                List<Invoice> invoiceList = invoiceRepo.findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(start, end);
                if (invoiceList.isEmpty()){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(invoiceList, HttpStatus.FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method to get total revenue, revenue by customers, revenue by drivers in a periods
    public ResponseEntity<Double> getRevenue(String searchBy, Long ID, String startDate, String endDate) {
        try{
            Double revenue = 0.0;
            String time = "T00:00:00.000Z";
            String startTime = startDate + time;
            String endTime = endDate + time;
            ZonedDateTime start = ZonedDateTime.parse(startTime);
            ZonedDateTime end = ZonedDateTime.parse(endTime);

            if (searchBy.equalsIgnoreCase("customer")) {
                Customer customer = customerRepo.findCustomerById(ID);
                List<Invoice> invoiceList =
                        invoiceRepo.findByCustomerAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(customer, start, end);
                for (Invoice invoice : invoiceList)
                    revenue += invoice.getTotalPayment();

                return new ResponseEntity<>(revenue, HttpStatus.FOUND);
            }
            if (searchBy.equalsIgnoreCase("driver")){
                Driver driver = driverRepo.findDriverById(ID);
                List<Invoice> invoiceList =
                        invoiceRepo.findByDriverAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(driver, start, end);
                for (Invoice invoice : invoiceList)
                    revenue += invoice.getTotalPayment();

                return new ResponseEntity<>(revenue, HttpStatus.FOUND);
            }
            if (searchBy.equalsIgnoreCase("all")){
                List<Invoice> invoiceList =
                        invoiceRepo.findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(start,end);
                for (Invoice invoice : invoiceList)
                    revenue += invoice.getTotalPayment();

                return new ResponseEntity<>(revenue, HttpStatus.FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}


