package com.example.Assigment_2_Project.TestUnits;

import com.example.Assigment_2_Project.controller.BookingController;
import com.example.Assigment_2_Project.controller.CarController;
import com.example.Assigment_2_Project.controller.CustomerController;
import com.example.Assigment_2_Project.controller.DriverController;
import com.example.Assigment_2_Project.model.Booking;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.model.Driver;
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
public class TestAllController {


    @Autowired
    private DriverController driverController;

    @Autowired
    private CarController carController;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private BookingController bookingController;

    @Test
    @Order(1)
    void loadContextDriver() {
        assertNotNull(driverController);
    }

    @Test
    @Order(2)
    void loadContextCar() {
        assertNotNull(carController);
    }

    @Test
    @Order(3)
    void loadContextCustomer() {
        assertNotNull(customerController);
    }

    @Test
    @Order(4)
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
    public
    @Order(5)
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
    @Order(6)
    void createCustomer() {
        Customer customer = new Customer();
        customer.setPhone("0777042801");
        customer.setAddress("702 Nguyen Hue");
        customer.setName("Khoi Nguyen");

        ResponseEntity<Customer> res = customerController.addCustomer(customer);
        assertEquals(res.getBody(), customer);
        assertEquals(res.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    @Order(7)
    void selectCar() {
        Long id = 1L;
        Long carID =  1L;
        ResponseEntity<Driver> res = driverController.selectCar(id, carID);
        Driver driver = res.getBody();

        assertEquals(res.getBody(), driver);
        assertEquals(res.getStatusCode(),HttpStatus.OK);
    }

    @Test
    @Order(8)
    void createBooking() {
        Map<String, String> bookingBody = new HashMap<>();
        bookingBody.put("Date", "2022-05-14");
        bookingBody.put("Time", "17:00");
        bookingBody.put("startLocation", "HCM");
        bookingBody.put("endLocation", "Ha Noi");
        bookingBody.put("tripDistance", "12.0");

        Long cusID = 1L;
        Long carID = 1L;

        ResponseEntity<Booking> res = bookingController.createBooking(cusID, carID, bookingBody);
        Booking booking = res.getBody();

        assertEquals(res.getBody(), booking);
        assertEquals(res.getStatusCode(), HttpStatus.CREATED);
    }



}
