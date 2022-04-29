package com.example.Assigment_2_Project.controller;


import com.example.Assigment_2_Project.model.Booking;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.repository.BookingRepo;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import com.example.Assigment_2_Project.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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
                                                           @RequestParam(required = false) Optional<String> rateKilometer) {
        return this.bookingService.getAvailableCarSorted(make, model, color, convertible, rating, rateKilometer);
    }





}
