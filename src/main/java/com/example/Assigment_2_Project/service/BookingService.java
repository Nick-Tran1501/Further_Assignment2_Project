package com.example.Assigment_2_Project.service;


import com.example.Assigment_2_Project.model.*;
import com.example.Assigment_2_Project.repository.BookingRepo;
import com.example.Assigment_2_Project.repository.CarRepo;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import com.example.Assigment_2_Project.repository.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class BookingService {

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    CarRepo carRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    InvoiceRepo invoiceRepo;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CarService carService;


//    @Autowired
//    private CustomerService customerService;


    // Input booking
    public ResponseEntity<Booking> createBooking(Long cusID, Long carID, Map<String, String> bookingBody) {
        try {
            Customer customer = customerRepo.findCustomerById(cusID);
            Booking booking = new Booking();
            List<Car> carList = carRepo.findByAvailableTrue();
            Invoice invoice = new Invoice();
            Car carData = null;
            Double tripDistance = null;
            for (Car cars :  carList)
                if (cars.getId() == carID){
                    carData = cars;
                    cars.setAvailable(false); // return false value for car
                }
            if (customer == null && carData == null) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            if (bookingBody.containsKey("startLocation"))
                booking.setStartLocation(bookingBody.get("startLocation"));
            if (bookingBody.containsKey("endLocation"))
                booking.setEndLocation(bookingBody.get("endLocation"));
            if (bookingBody.containsKey("pickupTime")) {
                ZonedDateTime pickupTime = ZonedDateTime.parse(bookingBody.get("pickupTime"));
                booking.setPickupTime(pickupTime);
            }
            if (bookingBody.containsKey("tripDistance")){
                tripDistance =  Double.parseDouble(bookingBody.get("tripDistance"));
                booking.setTripDistance(tripDistance);
            }
            Driver driver = carData.getDriver();
            Double rateKilometer = carData.getRateKilometer();
            Double totalPay = tripDistance * rateKilometer;
//            BigDecimal bigDecimal = new BigDecimal(totalPay);
            invoice.setCustomer(customer);
            invoice.setDriver(driver);
            invoice.setTotalPayment(totalPay);
            booking.setCar(carData);
            booking.setCustomer(customer);
            booking.setInvoice(invoice);
            invoiceRepo.save(invoice);
            bookingRepo.save(booking);
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Get available car for customer (tuan)
    public ResponseEntity<List<Car>> getAvailableCarSorted(
            Optional<String> make, Optional<String> model, Optional<String> color,
            Optional<Boolean> convertible, Optional<Double> rating, Optional<Double> rateKilometer) {
        try {
            String unavailable = "Cannot find car";
            List<Car> carTemp = carRepo.findByAvailableTrue();
            if (make.isPresent())
                carTemp = carRepo.findByAvailableTrueAndMake(make.get());
            else if (model.isPresent())
                carTemp =  carRepo.findByAvailableTrueAndModel( model.get());
            else if (color.isPresent())
                carTemp = carRepo.findByAvailableTrueAndColor(color.get());
            else if (convertible.isPresent())
                carTemp = carRepo.findByAvailableTrueAndConvertibleTrue();
            else if (rating.isPresent())
                carTemp = carRepo.findByAvailableTrueAndRating(rating.get());
            else if (rateKilometer.isPresent())
                carTemp = carRepo.findByAvailableTrueAndRateKilometer(rateKilometer.get());
            return carTemp == null ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(carTemp, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Get all booking data
    public ResponseEntity<List<Booking>> getBookings() {
        try {
            List<Booking> bookings = bookingRepo.findAll();
            if (bookings.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //  get customer data (tuan)
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
    //  get car data (tuan)
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

    //  Delete all data (tuan)
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            bookingRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // finish trip (tuan)
//    public ResponseEntity<Booking> finishTrip (Long id){
//        try {
//            Booking booking = bookingRepo.findById(id);
//
//
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


//    ------- KHOI PART --------

    //    //  Get available car list (khoi)
//    public ResponseEntity<List<Car>> getAvailableCar(ZonedDateTime pickupTime) {
//        try {
//            List<Car> carList = carRepo.findAll();
//            List<Booking> bookingList = bookingRepo.findAll();
//            for (Booking booking: bookingList) {
//                if (booking.getPickupTime().equals(pickupTime)) {
//                    carList.remove(booking.getCar());
//                    return new ResponseEntity<>(carList, HttpStatus.FOUND);
//                }
//            }
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    public ResponseEntity<List<Booking>> findByTime(ZonedDateTime pickupTime) {
        try {
            List<Booking> bookingList = bookingRepo.findByPickupTime(pickupTime);
            return new ResponseEntity<>(bookingList, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
