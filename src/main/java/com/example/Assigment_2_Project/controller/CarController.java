package com.example.Assigment_2_Project.controller;

import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.repository.CarRepo;
import com.example.Assigment_2_Project.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    private CarRepo carRepo;

    // create car manual
    @PostMapping(path = "/post")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

    // Get all car
    @GetMapping(path = "/all")
    public ResponseEntity<List<Car>> getCars() {
        return this.carService.getCars();
    }

    // Get car by ID
    @GetMapping(path = "/search/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") Long id){
        return this.carService.getCarById(id);
    }

    // search car by variables
    @GetMapping(path = "/search")
    public ResponseEntity<List<Car>> searchCar(@RequestParam(required = false) Optional<String> make,
                                                           @RequestParam(required = false) Optional<String> model,
                                                           @RequestParam(required = false) Optional<String> color,
                                                           @RequestParam(required = false) Optional<Boolean> convertible,
                                                           @RequestParam(required = false) Optional<String> licensePlate,
                                                           @RequestParam(required = false) Optional<Double> rating,
                                                           @RequestParam(required = false) Optional<Double> rateKilometer,
                                                            @RequestParam(required = false) Optional<Boolean> available) {
        return this.carService.searchCar(make, model, color, convertible, licensePlate, rating, rateKilometer, available);
    }

    // update car by ID
    @Override
    @PutMapping(path = "/{id}")
    public ResponseEntity<Car> updateTableColumnById(@PathVariable("id") Long id, @RequestBody Map<String, String> contentField) {
        try {
            Car car = carRepo.findCarById(id);
            if (contentField.containsKey("make"))
                car.setMake(contentField.get("make"));
            if (contentField.containsKey("model"))
                car.setModel(contentField.get("model"));
            if (contentField.containsKey("color"))
                car.setColor(contentField.get("color"));
            if (contentField.containsKey("licensePlate"))
                car.setLicensePlate(contentField.get("licensePlate"));
            if (contentField.containsKey("rating"))
                car.setRating(Double.parseDouble(contentField.get("rating")));
            if (contentField.containsKey("convertible"))
                car.setConvertible(Boolean.parseBoolean(contentField.get("convertible")));
            if (contentField.containsKey("rateKilometer"))
                car.setRateKilometer(Double.parseDouble(contentField.get("rateKilometer")));
            carRepo.save(car);
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create list car sample
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

    //   Get cars used in month
    @GetMapping(path = "/carsUsed")
    public ResponseEntity<HashMap<String,Integer>> carsUsed(@RequestParam String year, @RequestParam String month){
        return carService.carsUsed(year,month);
    }

    // Delete all car
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll(){
        return carService.deleteAll();
    }

    // Delete by ID
    @DeleteMapping(path = "/deleteID/{id}")
    public ResponseEntity<Car> deleteByID(@PathVariable("id") Long id){
        return carService.deleteByID(id);
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


}