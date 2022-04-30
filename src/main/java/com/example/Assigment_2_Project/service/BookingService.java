package com.example.Assigment_2_Project.service;


import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.repository.BookingRepo;
import com.example.Assigment_2_Project.repository.CarRepo;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private CustomerRepo customerRepo;

    // Get all car
    public ResponseEntity<List<Car>> getCars() {
        try {
            List<Car> cars = carRepo.findAll();
            if (cars.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Get car by available
    public List<Car> getAvailableCars(String available) {
        List<Car> cars = carRepo.findByAvailable(available);
        return cars;
    }

}
