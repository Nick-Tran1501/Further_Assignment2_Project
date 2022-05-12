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

    @Test
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
}
