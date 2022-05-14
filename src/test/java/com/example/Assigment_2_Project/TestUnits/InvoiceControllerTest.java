package com.example.Assigment_2_Project.TestUnits;


import com.example.Assigment_2_Project.controller.InvoiceController;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.model.Driver;
import com.example.Assigment_2_Project.model.Invoice;
import com.example.Assigment_2_Project.repository.InvoiceRepo;
import com.example.Assigment_2_Project.service.InvoiceService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceControllerTest {

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private InvoiceService invoiceService;

    @Test
    void loadContext() {assertNotNull(invoiceService);}

    @Test
    @Order(1)
    void createInvoice() {
        Customer customer = new Customer();
        Driver driver = new Driver();
        Double rateKilometer = 12.0;
        String tripDistance = "12.0";
        Invoice res = invoiceService.addInvoice(customer, driver, rateKilometer, tripDistance);
    }
}
