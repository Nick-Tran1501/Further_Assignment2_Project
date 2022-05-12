package com.example.Assigment_2_Project.TestUnits;


import com.example.Assigment_2_Project.controller.CarController;
import com.example.Assigment_2_Project.controller.DriverController;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Driver;
import com.example.Assigment_2_Project.repository.CarRepo;
import com.example.Assigment_2_Project.repository.DriverRepo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.swing.text.html.Option;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DriverControllerTest {

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private DriverController driverController;


    @Autowired
    private CarController carController;

    @Test
    @Order(1)
    void loadContext() {
        assertNotNull(driverController);

    }

    @Test
    @Order(2)
    void createDriver() {
        Driver driver = new Driver();
        driver.setName("Khoi Solid");
        driver.setLicense("07A2021");
        driver.setPhone("0777042801");
        driver.setRating(9.5);

        ResponseEntity<Driver> res = driverController.addDriver(driver);
        System.out.println(driver.getId());

        assertEquals(res.getBody(), driver);
        assertEquals(res.getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    @Order(3)
    void createDriverFalse() {
        Driver driver = new Driver();
        driver.setName("Khoi Solid");
        driver.setLicense("07A2021");
        driver.setPhone("0777042801");
        driver.setRating(9.5);

        System.out.println(driver.getId());

        ResponseEntity<Driver> res = driverController.addDriver(driver);
        assertEquals(res.getStatusCode(), HttpStatus.ALREADY_REPORTED);
    }

//
//
//
//
    @Test
    @Order(4)
    void updateDriver() {

        Long id = 1L;
        System.out.println(driverRepo.findDriverById(id).getName());

        Map<String, String> contentField = new HashMap<>();
        contentField.put("name", "Khoi Crypto");

        ResponseEntity<Driver> res = driverController.updateTableColumnById(id, contentField);
        System.out.println(driverRepo.findDriverById(id).getName());
        assertEquals(res.getBody().getName(), "Khoi Crypto");

    }
//
    @Test
    @Order(5)
    void selectCar() {

        Car car = new Car();
        car.setMake("China");
        car.setColor("Red");
        car.setModel("Sport");
        car.setConvertible(true);
        car.setRating(9.5);
        car.setLicensePlate("51A077.07");
        car.setRateKilometer(2.0);
        car.setAvailable(true);


        ResponseEntity<Car> newCar = carController.addCar(car);
        Long carID = car.getId();

        Long id = 1L;
        System.out.println(driverRepo.findDriverById(id).getName());
        ResponseEntity<Driver> res = driverController.selectCar(id, carID);

        assertEquals(res.getStatusCode(), HttpStatus.OK);
//
    }
//
    @Test
    @Order(7)
    void deleteDriverById() {

        Long id = 1L;
        System.out.println(driverRepo.findDriverById(id).getName());
        ResponseEntity<Driver> res = driverController.deleteByID(id);
        System.out.println(driverRepo.findAll());
        assertEquals(res.getStatusCode(), HttpStatus.NO_CONTENT);

    }
//
    @Test
    @Order(8)
    void deleteAllDriver() {
        Driver driver = new Driver();
        driver.setName("Khoi Solid");
        driver.setLicense("07A2025");
        driver.setPhone("0777042805");
        driver.setRating(9.5);

        ResponseEntity<Driver> newDriver = driverController.addDriver(driver);
        ResponseEntity<HttpStatus> res =  driverController.deleteAll();

        assertEquals(res.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    @Order(6)
    void searchDriver() {
        Optional<String> name = Optional.of("Khoi Solid");
        Optional<String> license = Optional.empty();
        Optional<String> phone = Optional.empty();
        Optional<Double> rating = Optional.empty();

        ResponseEntity<List<Driver>> res = driverController.searchDriver(name, phone, license, rating);

//        assertEquals(res.getBody(), driverRepo);
        assertEquals(res.getStatusCode(), HttpStatus.FOUND);

    }
//
//

}
