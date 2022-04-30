package com.example.Assigment_2_Project.controller;


import com.example.Assigment_2_Project.model.Booking;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.repository.BookingRepo;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import com.example.Assigment_2_Project.service.BookingService;
import org.apache.catalina.util.ResourceSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/booking")

public class BookingController extends EntityController<Booking>{


    @Autowired
    public BookingController(BookingRepo bookingRepo){
        super(bookingRepo);
    }

    @Override
    public ResponseEntity updateTableColumnById(Long id, Map contentField) {
        return null;
    }

    @Autowired
    private BookingService bookingService;


    @GetMapping(path = "/search")
    public ResponseEntity<List<Car>> getAvailableCarSorted(@RequestParam(required = false) Optional<String> make,
                                                           @RequestParam(required = false) Optional<String> model,
                                                           @RequestParam(required = false) Optional<String> color,
                                                           @RequestParam(required = false) Optional<Boolean> convertible,
                                                           @RequestParam(required = false) Optional<Double> rating,
                                                           @RequestParam(required = false) Optional<Double> rateKilometer) {
        return this.bookingService.getAvailableCarSorted(make, model, color, convertible, rating, rateKilometer);
    }


    @PostMapping(path = "/post/{id}")
    public ResponseEntity<Booking> createBooking(@PathVariable("id") Long id, @RequestBody Booking booking){
        return this.bookingService.createBooking(id, booking);
    }

    public ResponseEntity<List<Booking>> getAllBooking() {
        return this.bookingService.getAllBooking();
    }

    @Override
    public ResponseEntity<List<Booking>> inputDemoData(List<Booking> data) {
        return null;
    }

//    30/4



}
