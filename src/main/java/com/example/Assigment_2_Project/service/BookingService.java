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
    private CustomerRepo customerRepo;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CarService carService;

    public ResponseEntity<List<Car>> getAvailableCar(String date, String time){
        try {
            return new ResponseEntity<>(carService.getAvailableCar(date, time), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public Booking createBookingBody(String date, String time, String startLocation,
                                     String endLocation, String tripDistance,
                                     Invoice invoice, Car car, Customer customer) {
        Booking booking = new Booking();
        ZonedDateTime pickupTime = ZonedDateTime.parse(date +"T" +time + ":00.000Z");
        booking.setCreatedDate(pickupTime);
        booking.setPickupTime(pickupTime);
        booking.setStartLocation(startLocation);
        booking.setEndLocation(endLocation);
        booking.setInvoice(invoice);
        booking.setCar(car);
        booking.setTripDistance(Double.parseDouble(tripDistance));
        booking.setCustomer(customer);
        booking.setStatus("Ready");
        customer.getBookingList().add(booking);
        bookingRepo.save(booking);
        return booking;
    }

    // Input booking
    public ResponseEntity<Booking> createBooking(Long cusID, Long carID, Map<String, String> bookingBody) {
        try {
            Customer customer = customerRepo.findCustomerById(cusID);

            String startLocation  = bookingBody.get("startLocation");
            String endLocation = bookingBody.get("endLocation");
            String tripDistance = bookingBody.get("tripDistance");

            String date = bookingBody.get("Date");
            String time = bookingBody.get("Time");
            String strPickup = date + "T" + time + ":00.000Z";
            ZonedDateTime pickupTime = ZonedDateTime.parse(strPickup);

            List<Car> carList = carService.getAvailableCar(date, time);

            Car carData = null;
            for (Car cars :  carList)
                if (cars.getId() == carID){
                    carData = cars;
                    if(pickupTime.getMonth().equals(ZonedDateTime.now().getMonth()) &&
                        pickupTime.getYear() == ZonedDateTime.now().getYear() &&
                        pickupTime.getDayOfMonth() == ZonedDateTime.now().getDayOfMonth())
                        cars.setAvailable(false); // return false value for car
                }

            if (customer == null || carData == null) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            Driver driver = carData.getDriver();
            Double rateKilometer = carData.getRateKilometer();

            Invoice invoice = invoiceService.addInvoice(customer, driver, rateKilometer, tripDistance);
            Booking booking = createBookingBody(date, time, startLocation, endLocation, tripDistance, invoice, carData, customer);

            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    //  Get all booking data
    public ResponseEntity<List<Booking>> getBookings() {
        try {
            List<Booking> bookingList = bookingRepo.findAll();
            return new ResponseEntity<>(bookingList, HttpStatus.FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }



    // Finish trip
    public ResponseEntity<Booking> finishTrip(Long id){
        try {
            Booking booking = bookingRepo.findBookingById(id);
            Car car  = booking.getCar();
            ZonedDateTime dropTime =  ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            booking.setDropTime(dropTime);
            booking.setStatus("Finished");
            carService.setCarAvailableFinish(car);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void autoCancelled(Booking booking){
        Integer nowHour = ZonedDateTime.now().getHour();
        Integer nowDate = ZonedDateTime.now().getDayOfMonth();
        Integer bookingDate = booking.getPickupTime().getDayOfMonth();
        Integer nowMinute = ZonedDateTime.now().getMinute();
        if (bookingDate.equals(nowDate) && nowHour.equals(23) && nowMinute.equals(59) && booking.getStatus().equals("Ready")){
            booking.getCar().setAvailable(true);
            booking.setStatus("Cancelled");
            carService.setCarAvailableFinish(booking.getCar());
            invoiceService.setTotalPayment(booking.getInvoice());
            bookingRepo.save(booking);
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
            for (Booking booking : bookingList)
                autoCancelled(booking);
            return new ResponseEntity<>(bookingList, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
