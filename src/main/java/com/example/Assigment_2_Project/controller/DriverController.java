package com.example.Assigment_2_Project.controller;

import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Driver;
import com.example.Assigment_2_Project.repository.DriverRepo;
import com.example.Assigment_2_Project.service.CarService;
import com.example.Assigment_2_Project.service.DriverService;
import org.apache.catalina.util.ResourceSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resources;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping(path = "/drivers")
public class DriverController extends EntityController<Driver> {

    @Autowired
    private DriverService driverService;

    @Autowired
    DriverRepo driverRepo;

    @Autowired
    public DriverController(DriverRepo driverRepo) {
        super(driverRepo);
    }


//    @GetMapping
    public ResponseEntity<List<Driver>> getAllDriver() {
        return this.driverService.getAllDriver();
    }


    @PostMapping(path = "/post")
    public ResponseEntity<Driver> addDriver(@RequestBody Driver driver) {
        return this.driverService.addDriver(driver);
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<Driver> selectCar(@PathVariable("id") Long id, @RequestBody Map<String, Long> carID) {
        return this.driverService.selectCar(id, carID);
    }

    @Override
    @PutMapping(path = "/{id}")
    public ResponseEntity<Driver> updateTableColumnById(@PathVariable("id") Long id, @RequestBody Map<String, String> contentField) {
        return null;
    }

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


//    @RequestMapping(path = "/driver", method = RequestMethod.POST)
//    public Long addDriver(@RequestBody Driver driver) {
//        driverService.addDriver(driver);
//        return driver.getId();
//    }

//    @RequestMapping(path = "/driver", method = RequestMethod.GET)
//    public List<Driver> getDrivers() {
//        return this.driverService.getAllDriver();
//    }
}
