package com.example.Assigment_2_Project.TestUnits;


import com.example.Assigment_2_Project.controller.BookingController;
import com.example.Assigment_2_Project.model.Booking;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.repository.BookingRepo;
import com.example.Assigment_2_Project.service.InvoiceService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingControllerTest {


    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private BookingController bookingController;

    private CarControllerTest carControllerTest;

    private CustomerControllerTest customerControllerTest;

    @Autowired
    private InvoiceService invoiceService;


    @Test
    void loadContext() {assertNotNull(bookingController);}

    @Test
    @Order(1)
    void createBooking() {
//        booking.setPickupTime(pickupDate);
//        booking.setStartLocation(startLocation);
//        booking.setEndLocation(endLocation);
//        booking.setInvoice(invoice);
//        booking.setCar(car);
//        booking.setTripDistance(tripDistance);
//        booking.setCreatedDate(pickupDate);
//        booking.setCustomer(customer);
//        booking.setStatus("Ready");
        ZonedDateTime pickupTime = ZonedDateTime.parse("2022-05-14T04:30:00.000Z");
        Double tripDistance = 12.0;
        Double rateKilometer = 2.0;
    }

}
