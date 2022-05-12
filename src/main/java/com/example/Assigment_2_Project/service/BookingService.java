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
    private InvoiceService invoiceService;

    @Autowired
    private DriverRepo driverRepo;


    public Booking createBookingBody(ZonedDateTime pickupDate, String startLocation,
                                     String endLocation, Double tripDistance,
                                     Invoice invoice, Car car, Customer customer) {
        Booking booking = new Booking();
        booking.setPickupTime(pickupDate);
        booking.setStartLocation(startLocation);
        booking.setEndLocation(endLocation);
        booking.setInvoice(invoice);
        booking.setCar(car);
        booking.setTripDistance(tripDistance);
        booking.setCreatedDate(pickupDate);
        booking.setCustomer(customer);
        return booking;
    }

    // Input booking
    public ResponseEntity<Booking> createBooking(Long cusID, Long carID, Map<String, String> bookingBody) {
        try {
            Customer customer = customerRepo.findCustomerById(cusID);

            String startLocation  = bookingBody.get("startLocation");
            String endLocation = bookingBody.get("endLocation");
            Double tripDistance = Double.parseDouble(bookingBody.get("tripDistance"));

            String date = bookingBody.get("Date");
            String time = bookingBody.get("Time");

            String strPickup = date + "T" + time + ":00.000Z";
            ZonedDateTime pickupTime = ZonedDateTime.parse(strPickup);
            List<Car> carList = getAvailableCar(pickupTime);
            Car carData = null;
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

            Driver driver = carData.getDriver();
            Double rateKilometer = carData.getRateKilometer();

            Invoice invoice = invoiceService.addInvoice(customer, driver, rateKilometer, tripDistance);
            Booking booking = createBookingBody(pickupTime, startLocation, endLocation, tripDistance, invoice, carData, customer);

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

    public List<Car> getAvailableCar(ZonedDateTime pickupTime) {
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
        return carList;
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
