package com.example.Assigment_2_Project.TestUnits;


import com.example.Assigment_2_Project.controller.CarController;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.repository.CarRepo;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarControllerTest {

    @Autowired
    private CarRepo carRepo;


    @Autowired
    private CarController carController;


    @Test
    void loadContest() { assertNotNull(carController);}

    @Test public
    @Order(1)
    void createCar() {
        Car car = new Car();
        car.setMake("China");
        car.setColor("red");
        car.setModel("sport");
        car.setConvertible(true);
        car.setRating(9.5);
        car.setLicensePlate("50A077.07");
        car.setRateKilometer(2.0);
        car.setAvailable(true);

        ResponseEntity<Car> res = carController.addCar(car);
        assertEquals(res.getBody(), car);
        assertEquals(res.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    @Order(2)
    void updateCar() {
        Long id = 1L;
        Map<String, String> contentField = new HashMap<>();
        contentField.put("make", "USA");

        ResponseEntity<Car> res = carController.updateTableColumnById(id, contentField);
        assertEquals(res.getBody().getMake(), "USA");
    }

    @Test
    @Order(3)
    void getCarById() {
        Long id = 1L;
        ResponseEntity<Car> res = carController.getCarById(id);
        Car car = res.getBody();
        assertEquals(res.getBody(), car);
        assertEquals(res.getStatusCode(), HttpStatus.FOUND);
    }

    @Test
    @Order(4)
    void getAllCar() {
        ResponseEntity<List<Car>> res = carController.getCars();
        List<Car> carList = res.getBody();
        assertEquals(res.getBody(), carList);
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Order(5)
    void deleteCarById() {
        Car car = new Car();
        car.setMake("China");
        car.setColor("red");
        car.setModel("sport");
        car.setConvertible(true);
        car.setRating(9.5);
        car.setLicensePlate("50A077.08");
        car.setRateKilometer(2.0);
        car.setAvailable(true);
        ResponseEntity<Car> addCar = carController.addCar(car);
        Long id = car.getId();

        ResponseEntity<Car> res =  carController.deleteByID(id);
        assertEquals(res.getStatusCode(), HttpStatus.NO_CONTENT);
    }


    @Test
    @Order(6)
    void deleteAllCar() {
        ResponseEntity<HttpStatus> res =  carController.deleteAll();
        assertEquals(res.getStatusCode(), HttpStatus.NO_CONTENT);
    }
}
