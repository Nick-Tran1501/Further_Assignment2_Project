package com.example.Assigment_2_Project.service;

import com.example.Assigment_2_Project.model.Booking;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.repository.BookingRepo;
import com.example.Assigment_2_Project.repository.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@Transactional
public class CarService {

    @Autowired
    CarRepo carRepo;

    @Autowired
    private BookingRepo bookingRepo;


    //Create car
    public ResponseEntity<Car> addCar(Car car) {
        try {
            carRepo.save(car);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get all car convertible
    public ResponseEntity<List<Car>> getConvertible() {
        try {
            List<Car> cars  =  carRepo.findByConvertibleTrue();
            return new ResponseEntity<>(cars, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get all car
    public ResponseEntity<List<Car>> getCars() {
        try {
            List<Car> cars = carRepo.findAll();
            if (cars.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //Get car by id
    public ResponseEntity<Car> getCarById(Long id){
        Car car =  carRepo.findCarById(id);
        return new ResponseEntity<>(car, HttpStatus.FOUND);
    }
    public ResponseEntity<List<Car>> getAvailableCarSorted(Optional<String> make, Optional<String> model,
                                                           Optional<String> color, Optional<Boolean> convertible,
                                                           Optional<Double> rating, Optional<Double> rateKilometer) {

        try {
            String unavailable = "Cannot find car";
            List<Car> carTemp = carRepo.findByAvailableTrue();
            if (make.isPresent())
                carTemp = carRepo.findByAvailableTrueAndMake(make.get());
            else if (model.isPresent())
                carTemp =  carRepo.findByAvailableTrueAndModel(model.get());
            else if (color.isPresent())
                carTemp = carRepo.findByAvailableTrueAndColor(color.get());
            else if (convertible.isPresent())
                carTemp = carRepo.findByAvailableTrueAndConvertibleTrue();
            else if (rating.isPresent())
                carTemp = carRepo.findByAvailableTrueAndRating(rating.get());
            else if (rateKilometer.isPresent())
                carTemp = carRepo.findByAvailableTrueAndRateKilometer(rateKilometer.get());
            return carTemp == null ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(carTemp, HttpStatus.FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    public ResponseEntity<List<Car>> getAvailableCars(Map<String, String> fields) {
//        try {
//                List<Car> cars = carRepo.findByAvailable(fields.get("available"));
////            System.out.println(cars);
//                if (cars.size() == 0) {
//                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//                }
//            System.out.println((ResponseEntity<List<Car>>) cars);
//                return (ResponseEntity<List<Car>>) cars;
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }




}
//    private List<Car> carList;
//
//    private List<Driver> drivers;
//
//
//
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @Autowired
//    private DriverService driverService;
//
//    public void setCarList(List<Car> carList) {
//        this.carList = carList;
//    }
//
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    public Long createCar(Car car){
//        getAllCar().add(car);
//        assignCarToDriver(car);
//        sessionFactory.getCurrentSession().save(car);
//        return car.getId();
//    }
//    //View all cars
//    public List<Car> getAllCar() {
//        carList = this.sessionFactory.getCurrentSession().createQuery("from Car").list();
//        return carList;
//    }
//
//    public void assignCarToDriver(Car car) {
//        driverService.getAllDriver();
//        for (Driver driver : driverService.getDrivers()) {
//            if (driver.getCar() == null){
//                driver.setCar(car);
//            }
//        }
//    }
//
//    //Delete car
//    public String deleteCarByID(Long id) {
//        Car car = sessionFactory.getCurrentSession().get(Car.class, id);
//        this.sessionFactory.getCurrentSession().delete(car);
//        return "Delete successfully";
//    }
//
//    public Car updateCar(Car car) {
//        Car carTemp = sessionFactory.getCurrentSession().get(Car.class, car.getId());
//        this.sessionFactory.getCurrentSession().update(car);
//        return car;
//    }
//

