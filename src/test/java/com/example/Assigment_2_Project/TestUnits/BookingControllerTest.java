package com.example.Assigment_2_Project.TestUnits;


import com.example.Assigment_2_Project.controller.BookingController;
import com.example.Assigment_2_Project.model.*;
import com.example.Assigment_2_Project.repository.BookingRepo;
import com.example.Assigment_2_Project.service.InvoiceService;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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



    @Test
    void loadContext() {assertNotNull(bookingController);}





    @Test
    @Order(1)
    void createBooking() {
        Map<String, String> bookingBody = new HashMap<>();
        bookingBody.put("Date", "2022-05-14");
        bookingBody.put("Time", "17:00");
        bookingBody.put("startLocation", "HCM");
        bookingBody.put("endLocation", "Ha Noi");
        bookingBody.put("tripDistance", "12.0");

        Driver driver = new Driver();

//        Double tripDistance = Double.parseDouble(bookingBody.get("trpDistance"));

        Car car = new Car();
        car.setRateKilometer(2.0);
        car.setAvailable(true);
        car.setDriver(driver);

        Customer customer = new Customer();

        ResponseEntity<Booking> res = bookingController.createBooking(customer.getId(), car.getId(), bookingBody);
        Booking booking = res.getBody();
        assertEquals(res.getBody(), booking);
        assertEquals(res.getStatusCode(), HttpStatus.CREATED);

    }

}
