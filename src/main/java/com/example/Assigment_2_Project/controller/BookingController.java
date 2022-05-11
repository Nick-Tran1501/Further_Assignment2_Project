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
import org.apache.tomcat.jni.Time;
import org.apache.tomcat.util.net.AprEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.time.ZonedDateTime;
import java.util.Date;
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
    private BookingRepo bookingRepo;

    @Autowired
    private BookingService bookingService;

    @Override
    public ResponseEntity updateTableColumnById(Long id, Map contentField) {
        return null;
    }


//  Get available cars
    @GetMapping(path = "/search")
    public ResponseEntity<List<Car>> getAvailableCar(@RequestParam String date, @RequestParam String time ){
        return bookingService.getAvailableCar(date, time);
    }

//  Booking
    @PostMapping(path = "/post/{customer_id}/{car_id}")
    public ResponseEntity<Booking> createBooking(@PathVariable("customer_id") Long customer_id,
                                                 @PathVariable("car_id") Long car_id,
                                                 @RequestBody Map<String, String> booking) {
        return bookingService.createBooking(customer_id,car_id,booking);
    }


//  Get all booking data
    @GetMapping(path = "/all")
    public ResponseEntity<List<Booking>> getBooking() {
        bookingService.autoFinished();
        return bookingService.getBookings();
    }

//   Get customer data by booking data
    @GetMapping(path = "/customer/{id}")
    public ResponseEntity<Customer> customerData(@PathVariable("id") Long id) {
        return bookingService.customerData(id);
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

//  Finish trip
    @PostMapping(path = "/finish/{id}")
    public ResponseEntity<Booking> finishTrip(@PathVariable("id") Long id){
        return bookingService.finishTrip(id);
    }


//  ---------- KHOI PART ------------
    //  ( khoi)
//    @GetMapping(path = "/car")
//    public ResponseEntity<List<Car>> getAvailableCar(@RequestParam
//                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime pickupTime) {
//        return bookingService.getAvailableCar(pickupTime);
//    }
//

//    @GetMapping(path = "/test")
//    public ResponseEntity<List<Booking>> findByTime(@RequestBody
//                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime pickupTime) {
//        return bookingService.findByTime(pickupTime);
//    }
//
//        @PostMapping(path = "/test/{cusID}/{carID}")
//        public ResponseEntity<Booking> createBooingTest2(@PathVariable("cusID") Long cusID,
//                                                         @PathVariable("carID") Long carID,
//                                                         @RequestBody Map<String, String> booking) {
//        return bookingService.createBookingTest2(cusID, carID, booking);
//        }
//    public ResponseEntity<Booking> bookingTest(@RequestBody
//                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Map<String, ZonedDateTime> pickupTime) {
//        return bookingService.bookingTest(pickupTime);
//    }
    @GetMapping(path = "/between")
    public ResponseEntity<List<Booking>> findByPeriod(@RequestParam String startDate,
                                                      @RequestParam String endDate) {
        return bookingService.findByPeriod(startDate, endDate);
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
}
