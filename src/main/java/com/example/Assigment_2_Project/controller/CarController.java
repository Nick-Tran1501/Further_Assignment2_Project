package com.example.Assigment_2_Project.controller;

import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.repository.CarRepo;
import com.example.Assigment_2_Project.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private CarService carService;


    @Autowired
    CarRepo carRepo;

    @PostMapping(path = "/post")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        return this.carService.addCar(car);
    }

//    @GetMapping
//    @RequestMapping(path = "/get")
    public ResponseEntity<List<Car>> getCars() {
        return this.carService.getCars();
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public List<Car> getAvailableCars(@RequestParam String available) {
        return this.carService.getAvailableCars(available);
    }


    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Car> updateTableColumnById(@PathVariable Long id, @RequestBody Map<String, String> contentField) {
        Car car = carRepo.findById(id).get();
        System.out.println(car);
        if (contentField.size() > 0) {
            if (contentField.containsKey("make")) {
                car.setMake(contentField.get("make"));
            }
            if (contentField.containsKey("model")) {
                car.setModel(contentField.get("model"));
            }
            if (contentField.containsKey("color")) {
                car.setColor(contentField.get("color"));
            }
            if (contentField.containsKey("licensePlate")) {
                car.setLicensePlate(contentField.get("licensePlate"));
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
