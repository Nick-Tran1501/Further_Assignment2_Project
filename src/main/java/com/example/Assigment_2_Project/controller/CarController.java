package com.example.Assigment_2_Project.controller;

import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.repository.CarRepo;
import com.example.Assigment_2_Project.service.CarService;
import org.apache.catalina.util.ResourceSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import java.util.List;

@Validated
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
    @GetMapping(path = "/all")
    public ResponseEntity<List<Car>> getCars() {
        return this.carService.getCars();
    }


    @GetMapping(path = "/search/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") Long id){
        return this.carService.getCarById(id);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<Car>> getAvailableCarSorted(@RequestParam(required = false) Optional<String> make,
                                           @RequestParam(required = false) Optional<String> model,
                                           @RequestParam(required = false) Optional<String> color,
                                           @RequestParam(required = false) Optional<Boolean> convertible,
                                           @RequestParam(required = false) Optional<Double> rating,
                                           @RequestParam(required = false) Optional<Double> rateKilometer) {
        return this.carService.getAvailableCarSorted(make, model, color, convertible, rating, rateKilometer);
    }


    @Override
//    @PutMapping(path = "/{id}")
    public ResponseEntity<Car> updateTableColumnById(@PathVariable("id") Long id, @RequestBody Map<String, String> contentField) {
//        Car car =  carRepo.findById(id).get();
//        System.out.println(car.getMake());
//        System.out.println(car);
//        if (contentField.size() > 0) {
//            if (contentField.containsKey("make")) {
//                car.setMake(contentField.get("make"));
//            }
//            if (contentField.containsKey("model")) {
//                car.setModel(contentField.get("model"));
//            }
//            if (contentField.containsKey("color")) {
//                car.setColor(contentField.get("color"));
//            }
//            if (contentField.containsKey("licensePlate")) {
//                car.setLicensePlate(contentField.get("licensePlate"));
//            }
//            if (contentField.containsKey("available")){
//                car.setAvailable(contentField.get("available"));
//            }
//            if (contentField.containsKey("rating")){
//                car.setRating(Double.parseDouble(contentField.get("rating")));
//            }
//            if (contentField.containsKey("convertible")){
//                car.setConvertible(Boolean.parseBoolean(contentField.get("convertible")));
//            }
//            if (contentField.containsKey("rateKilometer")){
//                car.setRateKilometer(contentField.get("rateKilometer"));
//            }
//            return new ResponseEntity<>(carRepo.save(car), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
        return null;
    }

    @Override
    @PostMapping(path = "/demo")
    public ResponseEntity<List<Car>> inputDemoData(@Validated @RequestBody List<Car> data) {
        try {
            carRepo.saveAll(data);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


//    @GetMapping(path = "/{id}")
//    public ResponseEntity<Car> getCarByID(@PathVariable("id") Long id) {
//        try {
//            Car car = carRepo.findById(id).get();
//            return new ResponseEntity<>(car, HttpStatus.FOUND);
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }




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
