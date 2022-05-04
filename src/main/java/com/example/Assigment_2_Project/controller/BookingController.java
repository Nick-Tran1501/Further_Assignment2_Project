package com.example.Assigment_2_Project.controller;


import com.example.Assigment_2_Project.model.Booking;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.repository.BookingRepo;
import com.example.Assigment_2_Project.repository.CarRepo;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import com.example.Assigment_2_Project.service.BookingService;
import com.example.Assigment_2_Project.service.CarService;
import org.apache.catalina.util.ResourceSet;
import org.apache.tomcat.util.net.AprEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
@Validated
public class BookingController extends EntityController<Booking>{

    @Autowired
    public BookingController(BookingRepo bookingRepo){
        super(bookingRepo);
    }

    @Autowired
    public CarController carController;

    @Autowired
    BookingRepo bookingRepo;


    @Autowired
    private BookingService bookingService;

    @Autowired
    private CarService carService;

    @Override
    public ResponseEntity updateTableColumnById(Long id, Map contentField) {
        return null;
    }

    @Override
    @PostMapping(path = "/demo")
    public ResponseEntity<List<Booking>> inputDemoData(@Validated @RequestBody
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) List<Booking> data) {
        try {
            bookingRepo.saveAll(data);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//  Get available cars
    @GetMapping(path = "/search")
    public ResponseEntity<List<Car>> getAvailableCarSorted(@RequestParam(required = false) Optional<String> make,
                                                           @RequestParam(required = false) Optional<String> model,
                                                           @RequestParam(required = false) Optional<String> color,
                                                           @RequestParam(required = false) Optional<Boolean> convertible,
                                                           @RequestParam(required = false) Optional<Double> rating,
                                                           @RequestParam(required = false) Optional<Double> rateKilometer) {
        return bookingService.getAvailableCarSorted(make, model, color, convertible, rating, rateKilometer);
    }



//  Booking
//    @PostMapping(path = "/post/{customer_id}/{car_id}")
//    public ResponseEntity<Booking> createBooking(@PathVariable("customer_id") Long customer_id,
//                                                 @PathVariable("car_id") Long car_id,
//                                                 @RequestBody Booking booking){
//        return bookingService.createBooking(customer_id,car_id,booking);
//    }

//    @PostMapping(path = "/test")
//    public ResponseEntity<Booking> bookingTest(@RequestBody
//                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Map<String, ZonedDateTime> pickupTime) {
//        return bookingService.bookingTest(pickupTime);
//    }

//  Get all booking data
//    @GetMapping(path = "/car/{id}")
//    public ResponseEntity<Car> getBooking(@PathVariable("id") Long id) {
//        return this.bookingService.carData(id);
//    }

//   Get customer data by booking data
    @GetMapping(path = "/customer/{id}")
    public ResponseEntity<Customer> customerData(@PathVariable("id") Long id) {
        return bookingService.customerData(id);
    }


    @GetMapping(path = "/car")
    public ResponseEntity<List<Car>> getAvailableCar(@RequestParam
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime pickupTime) {
        return bookingService.getAvailableCar(pickupTime);
    }

//   Get car data by booking data
    @GetMapping(path = "/car/{id}")
    public ResponseEntity<Car> carData(@PathVariable("id") Long id) {
        return bookingService.carData(id);
    }

//   Delete data
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll(){
        return bookingService.deleteAll();
    }

    @PostMapping(path = "/post/{id}")
    public ResponseEntity<Booking> createBookingTest(@PathVariable("id") Long carID,
            @RequestBody Booking booking,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime pickupTime ) {
        return bookingService.createBookingTest(carID, booking, pickupTime);
    }

}
