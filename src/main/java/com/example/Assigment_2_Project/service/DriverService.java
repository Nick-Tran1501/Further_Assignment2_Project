package com.example.Assigment_2_Project.service;

import com.example.Assigment_2_Project.controller.CarController;
import com.example.Assigment_2_Project.model.Car;
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

@Service
@Transactional
public class DriverService {

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private CarRepo carRepo;


    public ResponseEntity<List<Driver>> getAllDriver() {
        try {
            List<Driver> drivers = driverRepo.findAll();
            return new ResponseEntity<>(drivers, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Driver> addDriver(Driver driver) {
        try {
            List<Driver> driverList = driverRepo.findAll();
            for (Driver temp: driverList)
                if (driver.getLicense().equalsIgnoreCase(temp.getLicense())){
                    return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
                }
                driverRepo.save(driver);
                return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Driver> selectCar(Long driverID, Map<String, Long> car_id) {
        try {
            Driver driver = driverRepo.findDriverById(driverID);
            List<Car> cars = carRepo.findAll();
            Car car = carRepo.findCarById(car_id.get("car_id"));
            if (car.getDriver() == null){
                car.setDriver(driver);
                driverRepo.save(driver);
                return new ResponseEntity<>(driver, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(driver, HttpStatus.valueOf("Car was assigned, please choose another"));
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


//    @Autowired
//    private SessionFactory sessionFactory;
//
//
//    private List<Driver> drivers;
//
//
//    public List<Driver> getDrivers() {
//
//        return drivers;
//    }
//
//    public void setDrivers(List<Driver> drivers) {
//        drivers = sessionFactory.getCurrentSession().createQuery("from Driver ").list();
//        this.drivers = drivers;
//    }
//
//    public Long addDriver(Driver driver) {
//        getAllDriver().add(driver);
//        this.sessionFactory.getCurrentSession().save(driver);
//        return driver.getId();
//    }
//
//
//    public List<Driver> getAllDriver() {
//        this.drivers = sessionFactory.getCurrentSession().createQuery("from Driver ").list();
//        return this.drivers;
//    }

//    public String


