package com.example.Assigment_2_Project.service;


import com.example.Assigment_2_Project.model.*;
import com.example.Assigment_2_Project.repository.*;
import net.bytebuddy.ClassFileVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CarService carService;

    @Autowired
    private DriverRepo driverRepo;


    // Input booking
    public ResponseEntity<Booking> createBooking(Long cusID, Long carID, Map<String, String> bookingBody) {
        try {
            Customer customer = customerRepo.findCustomerById(cusID);
            Booking booking = new Booking();
            String date = null;
            String time = null;
            if (bookingBody.containsKey("Date")){
                date = bookingBody.get("Date");
            }
            if (bookingBody.containsKey("Time")){
                time = bookingBody.get("Time");
            }

            String strPickup = date + "T" + time + ":00.000Z";
            ZonedDateTime pickupTime = ZonedDateTime.parse(strPickup);
            List<Car> carList = carRepo.findByAvailableTrue();
            Invoice invoice = new Invoice();
            Car carData = null;
            Double tripDistance = null;
            for (Car cars :  carList)
                if (cars.getId() == carID){
                    carData = cars;
                    if(pickupTime.getMonth().equals(ZonedDateTime.now().getMonth())
                            && pickupTime.getYear() == ZonedDateTime.now().getYear()
                            && pickupTime.getDayOfMonth() == ZonedDateTime.now().getDayOfMonth())
                        cars.setAvailable(false); // return false value for car
                }
            if (customer == null || carData == null) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            if (bookingBody.containsKey("startLocation"))
                booking.setStartLocation(bookingBody.get("startLocation"));
            if (bookingBody.containsKey("endLocation"))
                booking.setEndLocation(bookingBody.get("endLocation"));
            if (bookingBody.containsKey("tripDistance")){
                tripDistance =  Double.parseDouble(bookingBody.get("tripDistance"));
                booking.setTripDistance(tripDistance);
            }
            Driver driver = carData.getDriver();
            Double rateKilometer = carData.getRateKilometer();
            Double totalPay = tripDistance * rateKilometer;

            invoice.setCustomer(customer);
            invoice.setDriver(driver);
            invoice.setTotalPayment(totalPay);
            invoice.setCreatedDate(pickupTime);
            invoice.setBooking(booking);

            booking.setCreatedDate(pickupTime);
            booking.setCar(carData);
            booking.setCustomer(customer);
            booking.setInvoice(invoice);
            booking.setPickupTime(pickupTime);
            booking.setStatus("Ready");

            customer.getInvoiceList().add(invoice);
            customer.getBookingList().add(booking);
            driver.getInvoiceList().add(invoice);


            invoiceRepo.save(invoice);
            bookingRepo.save(booking);
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Get available car for customer
    public ResponseEntity<List<Car>> getAvailableCar(String date, String time){
        try{
            String strDateTime = date + "T" + time+ ":00.000Z";
            ZonedDateTime pickupTime = ZonedDateTime.parse(strDateTime);
            List<Car> carList = carRepo.findAll();
            List<Booking> bookingList = bookingRepo.findAll();
            ZonedDateTime timeTemp = null;
            for (Booking booking : bookingList) {
                timeTemp = booking.getCreatedDate();
                if (timeTemp.getYear() == pickupTime.getYear()
                        && timeTemp.getMonth().equals(pickupTime.getMonth())
                        && timeTemp.getDayOfMonth() == pickupTime.getDayOfMonth()){
                    carList.remove(booking.getCar());
                }
            }
            return new ResponseEntity<>(carList, HttpStatus.FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void autoFinished(){
        List<Booking> bookingList = bookingRepo.findAll();
        Integer nowDate = ZonedDateTime.now().getDayOfMonth();
        for (Booking booking : bookingList) {
            Integer bookingDate = booking.getPickupTime().getDayOfMonth();
            if (bookingDate.compareTo(nowDate) < 0){
                booking.getCar().setAvailable(true);
                booking.setStatus("Cancelled");
                bookingRepo.save(booking);
            }
        }
    }


    //  Get all booking data
    public ResponseEntity<List<Booking>> getBookings() {
        try {
//            ZonedDateTime zonedDateTime = ZonedDateTime.now();
//            Integer nowDate = zonedDateTime.getDayOfMonth();
//            Integer bDate = null;
//            List<Booking> bookings = bookingRepo.findAll();
//            if (bookings.size() == 0) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(bookings, HttpStatus.OK);
            List<Booking> bookingList = bookingRepo.findAll();
            return new ResponseEntity<>(bookingList, HttpStatus.FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //  get customer data
    public ResponseEntity<Customer> customerData(Long id) {
        try {
            Booking booking = bookingRepo.findBookingById(id);
            Customer customer = booking.getCustomer();
            return new ResponseEntity<>(customer, HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //  get car data
    public ResponseEntity<Car> carData(Long id) {
        try {
            Booking booking = bookingRepo.findBookingById(id);
            Car car = booking.getCar();
            return car == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(car, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // Finish trip
    public ResponseEntity<Booking> finishTrip(Long id){
        try {
            Booking booking = bookingRepo.findBookingById(id);
            booking.getCar().setAvailable(true);
            ZonedDateTime dropTime =  ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            booking.setDropTime(dropTime);
            booking.setStatus("Finished");
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Find by booking by time
    public ResponseEntity<List<Booking>> findByPeriod(String startDate, String endDate) {
        try {
            String time = "T00:00:00.000Z";
            String startTime = startDate + time;
            String endTime = endDate + time;
            ZonedDateTime start = ZonedDateTime.parse(startTime);
            ZonedDateTime end = ZonedDateTime.parse(endTime);
            List<Booking> bookingList = bookingRepo.findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(start, end);
            return new ResponseEntity<>(bookingList, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
