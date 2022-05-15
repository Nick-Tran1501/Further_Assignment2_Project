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
import java.time.Month;
import java.time.Year;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.IllegalFormatCodePointException;
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
    private InvoiceService invoiceService;

    @Autowired
    private CarService carService;

    public ResponseEntity<List<Car>> getAvailableCar(String date, String time){
        try {
            List<Car> carList = carService.getAvailableCar(date, time);
            if (carList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(carList, HttpStatus.FOUND);
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
            for (Car car :  carList)
                if (car.getId() == carID){
                    carData = car;
                }

            if (customer == null || carData == null) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            Driver driver = carData.getDriver();
            Double rateKilometer = carData.getRateKilometer();

            Invoice invoice = invoiceService.addInvoice(customer, driver, rateKilometer, tripDistance);
            Booking booking = createBookingBody(date, time, startLocation, endLocation, tripDistance, invoice, carData, customer);
            carRepo.save(carData);

            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    //  Get all booking data
    public ResponseEntity<List<Booking>> getBookings() {
        try {
            List<Booking> bookingList = bookingRepo.findAll();
            for (Booking booking : bookingList)
                autoCancelled(booking);
            return new ResponseEntity<>(bookingList, HttpStatus.FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // Finish trip
    public ResponseEntity<Booking> finishTrip(Long id){
        try {
            Booking booking = bookingRepo.findBookingById(id);

            if ((booking == null)){
                return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
            }
            Car car  = booking.getCar();
            ZonedDateTime dropTime =  ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            booking.setDropTime(dropTime);
            booking.setStatus("Finished");
//            carService.setCarAvailableFinish(car);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void autoCancelled(Booking booking){

        ZonedDateTime bookingDate =  booking.getCreatedDate();

        if (bookingDate.compareTo(ZonedDateTime.now()) < 0 && booking.getStatus().equalsIgnoreCase("Ready")) {
            booking.getCar().setAvailable(true);
            booking.setStatus("Cancelled");
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
            if (bookingList.isEmpty()){
                return new ResponseEntity<>(bookingList, HttpStatus.NOT_FOUND);
            }
            for (Booking booking : bookingList)
                autoCancelled(booking);
            return new ResponseEntity<>(bookingList, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
