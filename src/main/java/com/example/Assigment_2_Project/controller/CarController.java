package com.example.Assigment_2_Project.controller;

import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.repository.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping(path = "/cars")
public class CarController extends EntityController<Car> {

    @Autowired
    public CarController(CarRepo carRepo) {
        super(carRepo);
    }

    //
    @Autowired
    private CarRepo carRepo;

//    @PostMapping(path = "/post")
    @PostMapping(path = "/post")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        try {

            carRepo.save(car);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping
//    @RequestMapping(path = "/get")
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

    @Override
    @PatchMapping(path = "/{VIN}")
    public ResponseEntity<Car> updateTableColumnById(Long VIN, @RequestBody Map<String, String> columnData) {
        Car car = carRepo.findById(VIN).get();
        if (columnData.size() > 0) {
            if (columnData.containsKey("make")) {
                car.setMake(columnData.get("make"));
            }
            if (columnData.containsKey("model")) {
                car.setModel(columnData.get("model"));
            }
            if (columnData.containsKey("color")) {
                car.setColor(columnData.get("color"));
            }
            if (columnData.containsKey("licensePlate")) {
                car.setLicensePlate(columnData.get("licensePlate"));
            }
            return new ResponseEntity<>(carRepo.save(car), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}

//    @RequestMapping(path = "/post", method = RequestMethod.POST)
//    public Long createCar(@RequestBody Car car){
//        return carService.createCar(car);
//    }
//
//    @RequestMapping(path = "/get", method = RequestMethod.GET)
//    public List<Car> getAllCars() {
//        return carService.getAllCar();
//    }
//
//    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
//    public String deleteCarByID(@PathVariable Long id) {
//        return carService.deleteCarByID(id);
//    }
//
//    @RequestMapping(path = "/update", method = RequestMethod.PUT)
//    public Car updateCar(@RequestParam) {
//        return carService.updateCar(id, param);
//    }
//
//    @Override
//    public ResponseEntity<Car> updateTableColumnById(Long id, Map<String, String> contentField) {
//        return null;
//    }
