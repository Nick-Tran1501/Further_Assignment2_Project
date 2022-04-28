package com.example.Assigment_2_Project.controller;

import com.example.Assigment_2_Project.model.Driver;
import com.example.Assigment_2_Project.service.DriverService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;

    @RequestMapping(path = "/driver", method = RequestMethod.POST)
    public Long addDriver(@RequestBody Driver driver) {
        driverService.addDriver(driver);
        return driver.getId();
    }

//    @RequestMapping(path = "/driver", method = RequestMethod.GET)
//    public List<Driver> getDrivers() {
//        return this.driverService.getAllDriver();
//    }
}
