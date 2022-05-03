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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

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
    BookingRepo bookingRepo;

    @Autowired
    CarRepo carRepo;

    @Autowired
    CustomerRepo customerRepo;

//    @Autowired
//    private CustomerService customerService;

    @Autowired
    private CarService carService;

    @Autowired
    private CarService carService;

//    @Autowired
//    private CustomerService customerService;

//    public ResponseEntity<List<Booking>> getAllBooking() {
//        List<Booking> bookingList = bookingRepo.findAll();
//        return new ResponseEntity<>(bookingList, HttpStatus.FOUND);
//    }

//  Get all booking data
    public ResponseEntity<List<Booking>> getBookings() {
        try {
            List<Booking> booking = bookingRepo.findAll();
            if (booking.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }  catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

//  Delete all data
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            bookingRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//  Create booking
// xu
    public ResponseEntity<Booking> createBooking(Long customer_id,Booking booking) {
        try {
            Customer customer = customerRepo.findCustomerById(customer_id);
//            Car car = carRepo.findCarById(car_id);
            if (customer != null)
                booking.setCustomer(customer);
//            booking.setCar(car);
            bookingRepo.save(booking);
//            List<Booking> bookingList =  new ArrayList<>();
//            bookingList.add(booking);
//            customer.setBooking(bookingList);
//            booking.setCustomer(customer);
            return customer == null ? new ResponseEntity<>(HttpStatus.valueOf("Booking invalid"))
                    : new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//  Get available car for customer
    public ResponseEntity<List<Car>> getAvailableCarSorted(
            Optional<String> make, Optional<String> model, Optional<String> color,
            Optional<Boolean> convertible, Optional<Double> rating, Optional<Double> rateKilometer) {
        try {
            String unavailable = "Cannot find car";
            List<Car> carTemp = carRepo.findByAvailableTrue();
            if (make.isPresent())
                carTemp = carRepo.findByAvailableTrueAndMake(make.get());
            else if (model.isPresent())
                carTemp =  carRepo.findByAvailableTrueAndModel( model.get());
            else if (color.isPresent())
                carTemp = carRepo.findByAvailableTrueAndColor(color.get());
            else if (convertible.isPresent())
                carTemp = carRepo.findByAvailableTrueAndConvertibleTrue();
            else if (rating.isPresent())
                carTemp = carRepo.findByAvailableTrueAndRating(rating.get());
            else if (rateKilometer.isPresent())
                carTemp = carRepo.findByAvailableTrueAndRateKilometer(rateKilometer.get());
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
