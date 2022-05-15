package com.example.Assigment_2_Project.TestUnits;

import com.example.Assigment_2_Project.controller.*;
import com.example.Assigment_2_Project.model.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.print.attribute.standard.PresentationDirection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingAndInvoiceControllerTest {


    @Autowired
    private DriverController driverController;

    @Autowired
    private CarController carController;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private BookingController bookingController;

    @Autowired
    private InvoiceController invoiceController;

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
    void loadConTextBooking() {
        assertNotNull(bookingController);
    }

    @Test
    @Order(5)
    void loadContext() {
        assertNotNull(invoiceController);
    }

    @Test
    @Order(6)
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
    @Order(7)
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
    @Order(8)
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
    @Order(9)
    void selectCar() {
        Long id = 1L;
        Long carID =  1L;
        ResponseEntity<String> res = driverController.selectCar(id, carID);
        String string = res.getBody();

        assertEquals(res.getBody(), string);
        assertEquals(res.getStatusCode(),HttpStatus.OK);
    }

    @Test
    @Order(10)
    void getAvailableCar() {
        String date = "2022-05-15";
        String time = "17:00";

        ResponseEntity<List<Car>> res = bookingController.getAvailableCar(date, time);
        List<Car> carList = res.getBody();

        assertEquals(res.getBody(), carList);
        assertEquals(res.getStatusCode(), HttpStatus.FOUND);
    }

    @Test
    @Order(11)
    void createBooking() {
        Map<String, String> bookingBody = new HashMap<>();
        bookingBody.put("Date", "2022-05-15");
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

    @Test
    @Order(12)
    void getAllBookingPeriods() {
        String start = "2022-05-01";
        String end = "2022-05-30";
        ResponseEntity<List<Booking>> res = bookingController.findByPeriod(start, end);
        List<Booking> bookingList = res.getBody();

        assertEquals(res.getBody(), bookingList);
        assertEquals(res.getStatusCode(), HttpStatus.FOUND);
    }

    @Test
    @Order(13)
    void getAllInvoice() {
        String searchBy = "all";
//        String searchBy = "customer";
//        String searchBy = "driver";
        Long id = 1L;
        String start = "2022-05-01";
        String end = "2022-05-30";
        ResponseEntity<List<Invoice>> res = invoiceController.findInvoice(searchBy, id, start, end);
        List<Invoice> invoiceList = res.getBody();
        assertEquals(res.getBody(), invoiceList);
        assertEquals(res.getStatusCode(), HttpStatus.FOUND);
    }

    @Test
    @Order(14)
    void getRevenue() {
        String searchBy = "all";
//        String searchBy = "customer";
//        String searchBy = "driver";
        Long id = 1L;
        String start = "2022-05-01";
        String end = "2022-05-30";
        ResponseEntity<Double> res = invoiceController.getRevenue(searchBy, id, start, end);
        Double revenue  = res.getBody();
        assertEquals(res.getBody(), revenue);
        assertEquals(res.getStatusCode(), HttpStatus.FOUND);

    }
    @Test
    @Order(15)
    void carUsedByMonth(){
        String year = "2022";
        String month = "may";
        ResponseEntity<HashMap<String,Integer>> res = carController.carsUsed(year,month);
        HashMap<String,Integer> carsUsed = res.getBody();

        assertEquals(res.getBody(),carsUsed);
        assertEquals(res.getStatusCode(),HttpStatus.FOUND);
    }

    @Test
    @Order(21)
    void finishTrip(){
        Long id = 1L;
        ResponseEntity<Booking> res = bookingController.finishTrip(id);
        Booking booking = res.getBody();

        assertEquals(res.getBody(),booking);
        assertEquals(res.getStatusCode(), HttpStatus.OK);

    }



//+++++++++++++++++++++++++++++++++++ Negative test ++++++++++++++++++++++++++
    @Test
    @Order(16)
    void falseGetAvailableCar(){
        String date = "2022-05-15";
        String time = "17:00";

        ResponseEntity<List<Car>> res = bookingController.getAvailableCar(date, time);
        List<Car> carList = res.getBody();

        assertEquals(res.getBody(), carList);
        assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(17)
    void falseCreateBooking(){
        Map<String, String> bookingBody = new HashMap<>();
        bookingBody.put("Date", "2022-05-15");
        bookingBody.put("Time", "17:00");
        bookingBody.put("startLocation", "HCM");
        bookingBody.put("endLocation", "Ha Noi");
        bookingBody.put("tripDistance", "12.0");

        Long cusID = 1L;
        Long carID = 1L;

        ResponseEntity<Booking> res = bookingController.createBooking(cusID, carID, bookingBody);
        Booking booking = res.getBody();

        assertEquals(res.getBody(), booking);
        assertEquals(res.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    @Order(18)
    void falseGetAllBookingPeriods() {
        String start = "2022-04-01";
        String end = "2022-04-30";
        ResponseEntity<List<Booking>> res = bookingController.findByPeriod(start, end);
        List<Booking> bookingList = res.getBody();

        assertEquals(res.getBody(), bookingList);
        assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    @Test
    @Order(19)
    void falseFetAllInvoice() {

        String searchBy = "driver";
//        String searchBy = "customer";
//        String searchBy = "all";
//        String searchBy = "null";


        Long id = 1L;
        String start = "2022-04-01";
        String end = "2022-04-30";
        ResponseEntity<List<Invoice>> res = invoiceController.findInvoice(searchBy, id, start, end);
        List<Invoice> invoiceList = res.getBody();
        assertEquals(res.getBody(), invoiceList);
        assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(20)
    void falseGetRevenue() {
        String searchBy = "all";
//        String searchBy = "customer";
//        String searchBy = "driver";
        Long id = 1L;
        String start = "2022-04-01";
        String end = "2022-04-30";
        ResponseEntity<Double> res = invoiceController.getRevenue(searchBy, id, start, end);
        Double revenue = res.getBody();
        assertEquals(res.getBody(), revenue);
        assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    @Order(22)
    void falseCarUsedByMonth(){
        String year = "2022";
        String month = "January";
        ResponseEntity<HashMap<String,Integer>> res = carController.carsUsed(year,month);
        HashMap<String,Integer> carsUsed = res.getBody();

        assertEquals(res.getBody(),carsUsed);
        assertEquals(res.getStatusCode(),HttpStatus.NOT_FOUND);
    }





}
