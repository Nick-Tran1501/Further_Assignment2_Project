package com.example.Assigment_2_Project.controller;


import com.example.Assigment_2_Project.model.Booking;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.repository.BookingRepo;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import com.example.Assigment_2_Project.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @RequestMapping(path = "/booking", method = RequestMethod.GET)
    public List<Car> getAvailableCar(@RequestParam String available){
        return bookingService.getAvailableCars(available);
    }




}
