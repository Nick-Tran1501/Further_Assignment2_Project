package com.example.Assigment_2_Project.service;

import com.example.Assigment_2_Project.controller.CarController;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Driver;
import com.example.Assigment_2_Project.repository.CarRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Service
public class CarService<updateCarName> {


    @Autowired
    private CarRepo carRepo;


    //Create car
    public ResponseEntity<Car> addCar(Car car) {
        try {
            carRepo.save(car);
            return new ResponseEntity<>(HttpStatus.CREATED);
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
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //Get car by available
    public List<Car> getAvailableCars(String available ) {
        List<Car> cars = carRepo.findByAvailable(available);
        return cars;
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

