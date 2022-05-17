package com.example.Assigment_2_Project.controller;

import com.example.Assigment_2_Project.model.Driver;
import com.example.Assigment_2_Project.repository.DriverRepo;

import com.example.Assigment_2_Project.service.DriverService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(path = "/drivers")
public class DriverController extends EntityController<Driver> {

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    public DriverController(DriverRepo driverRepo) {
        super(driverRepo);
    }


    // Get all driver
    public ResponseEntity<List<Driver>> getAllDriver() {
        return this.driverService.getAllDriver();
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<Driver>> searchDriver(@RequestParam(required = false)Optional<String> name,
                                                        @RequestParam(required = false)Optional<String> phone,
                                                        @RequestParam(required = false)Optional<String> license,
                                                        @RequestParam(required = false)Optional<Double> rating){
        return driverService.searchDriver(name, phone, license, rating);
    }

    @PostMapping(path = "/post")
    public ResponseEntity<Driver> addDriver(@RequestBody Driver driver) {
        return this.driverService.addDriver(driver);
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<String> selectCar(@PathVariable("id") Long id, @RequestParam("Car id") Long carID) {
        return driverService.selectCar(id, carID);
    }



    //  Find by ID
    @GetMapping(path = "/searchID/{id}")
    public ResponseEntity<Driver> getById(@PathVariable("id") Long id){
        return driverService.getByID(id);
    }

    // Delete all driver
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll(){
        return driverService.deleteAll();
    }

    // Delete driver by ID
    @DeleteMapping(path = "/deleteByID/{id}")
    public ResponseEntity<Driver> deleteByID(@PathVariable("id") Long id){
        return driverService.deleteByID(id);
    }

    // Update driver by id
    @Override
    @PutMapping(path = "/{id}")
    public ResponseEntity<Driver> updateTableColumnById(@PathVariable("id") Long id, @RequestBody Map<String, String> contentField) {
        try {
            Driver driver = driverRepo.findDriverById(id);
            if (contentField.containsKey("name"))
                driver.setName(contentField.get("name"));
            if (contentField.containsKey("license"))
                driver.setLicense(contentField.get("license"));
            if (contentField.containsKey("phone"))
                driver.setPhone(contentField.get("phone"));
            if (contentField.containsKey("rating"))
                driver.setRating(Double.parseDouble(contentField.get("rating")));
            driverRepo.save(driver);
            return new ResponseEntity<>(driver, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a list of sample drivers
    @Override
    @PostMapping(path = "/demo")
    public ResponseEntity<List<Driver>> inputDemoData(@Validated @RequestBody List<Driver> data) {
        try {
            driverRepo.saveAll(data);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
