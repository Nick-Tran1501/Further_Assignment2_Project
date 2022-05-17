package com.example.Assigment_2_Project.service;

import com.example.Assigment_2_Project.controller.CarController;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.model.Driver;
import com.example.Assigment_2_Project.repository.CarRepo;
import com.example.Assigment_2_Project.repository.DriverRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class DriverService {

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private CarRepo carRepo;

    // Get all driver
    public ResponseEntity<List<Driver>> getAllDriver() {
        try {
            List<Driver> drivers = driverRepo.findAll();
            return new ResponseEntity<>(drivers, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method to create driver and save to repository
    public ResponseEntity<Driver> addDriver(Driver driver) {
        try {
            List<Driver> driverList = driverRepo.findAll();
            for (Driver temp: driverList)
                if (driver.getLicense().equalsIgnoreCase(temp.getLicense())){
                    return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
                }
            driverRepo.save(driver);
            return new ResponseEntity<>(driver, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method for drivers to select cars
    public ResponseEntity<String> selectCar(Long driverID, Long carID) {
        try {
            Driver driver = driverRepo.findDriverById(driverID);
            Car car = carRepo.findCarById(carID);
            if (car.getDriver() == null){
                car.setDriver(driver);
                driver.setCar(car);
                driverRepo.save(driver);
                return new ResponseEntity<>("Car selected successfully", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("This car has been selected, please choose another",HttpStatus.ALREADY_REPORTED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search driver by attributes
    public ResponseEntity<List<Driver>> searchDriver(Optional<String> name, Optional<String> phone,
                                                        Optional<String> license, Optional<Double> rating){
        try{
            List<Driver> driverList = null;
            if (name.isPresent())
                driverList = driverRepo.findByName(name.get());
            if (phone.isPresent())
                driverList = driverRepo.findByPhone(phone.get());
            if (license.isPresent())
                driverList = driverRepo.findByLicense(license.get());
            if (rating.isPresent())
                driverList = driverRepo.findByRating(rating.get());
            if (name.isPresent() && phone.isPresent())
                driverList = driverRepo.findByNameAndPhone(name.get(), phone.get());
            if (name.isPresent() && rating.isPresent())
                driverList = driverRepo.findByNameAndRating(name.get(), rating.get());
            return driverList.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(driverList, HttpStatus.FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Get driver by id
    public ResponseEntity<Driver> getByID(Long id){
        try {
            Driver driver  = driverRepo.findDriverById(id);

            return driver == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(driver, HttpStatus.FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Delete driver by ID
    public ResponseEntity<Driver> deleteByID(Long id){
        try{
            if (driverRepo.findDriverById(id)  == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            driverRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // delete all driver
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            driverRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



