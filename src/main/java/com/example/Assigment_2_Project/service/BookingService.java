package com.example.Assigment_2_Project.service;


import com.example.Assigment_2_Project.model.Booking;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.model.Driver;
import com.example.Assigment_2_Project.repository.BookingRepo;
import com.example.Assigment_2_Project.repository.CarRepo;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.UserTransaction;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional

public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    private CustomerService customerService;

//    @Autowired
//    private CustomerService customerService;

    public ResponseEntity<List<Booking>> getAllBooking() {
        List<Booking> bookingList = bookingRepo.findAll();
        return new ResponseEntity<>(bookingList, HttpStatus.FOUND);
    }


    public ResponseEntity<Booking> createBooking(Long customer_id, Booking booking) {
        try {
            Customer customer = customerRepo.findCustomerById(customer_id);
            List<Booking> bookingList =  new ArrayList<>();
            bookingList.add(booking);
            customer.setBooking(bookingList);
            booking.setCustomer(customer);
            bookingRepo.save(booking);
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//  Get available car for customer
    public ResponseEntity<List<Car>> getAvailableCarSorted(
            Optional<String> make, Optional<String> model,
            Optional<String> color, Optional<Boolean> convertible,
            Optional<Double> rating, Optional<Double> rateKilometer) {
        try {
            String available = "yes";
            String unavailable = "Cannot find car";
            List<Car> carTemp = carRepo.findByAvailable(available);
            if (make.isPresent())
                carTemp = carRepo.findByAvailableAndMake(available, make.get());
            else if (model.isPresent())
                carTemp =  carRepo.findByAvailableAndModel(available, model.get());
            else if (color.isPresent())
                carTemp = carRepo.findByAvailableAndColor(available, color.get());
            else if (convertible.isPresent())
                carTemp = carRepo.findByAvailableAndConvertible(available, convertible.get());
            else if (rating.isPresent())
                carTemp = carRepo.findByAvailableAndRating(available, rating.get());
            else if (rateKilometer.isPresent())
                carTemp = carRepo.findByAvailableAndRateKilometer(available, rateKilometer.get());
            return carTemp == null ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(carTemp, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public ResponseEntity<Booking> addBooking (Long customerID){
//        try{
//            ResponseEntity<List<Customer>> customer = customerService.getByID(customerID);
//            Booking booking = new Booking();
//            booking.setCustomer(customer);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        }
//        catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
