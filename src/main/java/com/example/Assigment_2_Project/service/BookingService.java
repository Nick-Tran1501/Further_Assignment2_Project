package com.example.Assigment_2_Project.service;


import com.example.Assigment_2_Project.model.Booking;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.repository.BookingRepo;
import com.example.Assigment_2_Project.repository.CarRepo;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private CustomerService customerService;

    @Autowired
    private CarService carService;


//    @Autowired
//    private CustomerService customerService;





//    @GetMapping(path = "/{id}")
//    public ResponseEntity<T> getByID(@PathVariable("id") Long id) {
//        try {
//            return new ResponseEntity<>(repo.findById(id).get(), HttpStatus.FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    //  Get booking data by booking ID ( car )
//    public ResponseEntity<Car> carData(Long id) {
//        try {
//            Booking booking = bookingRepo.findBookingById(id);
//            List<Car> carList = carRepo.findAll();
//            Car car= null;
//            for (Car carDATA :carList)
//                if (booking.getCar().getId() == carDATA.getId())
//                    car = carDATA;
//            return car == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
//                    : new ResponseEntity<>(car, HttpStatus.OK);
//        }
//        catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    //  Get booking data by booking ID ( customer )
    public ResponseEntity<List<Car>> getAvailableCar(ZonedDateTime pickupTime) {
        try {
            List<Car> carList = carRepo.findAll();
            List<Booking> bookingList = bookingRepo.findAll();
            for (Booking booking: bookingList) {
                if (booking.getPickupTime().equals(pickupTime)) {
                    carList.remove(booking.getCar());
                    return new ResponseEntity<>(carList, HttpStatus.FOUND);
                }
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Customer> customerData(Long id) {
        try {
            Booking booking = bookingRepo.findBookingById(id);
            Customer customer = booking.getCustomer();

            return new ResponseEntity<>(customer, HttpStatus.NOT_FOUND);

            return customer == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(customer, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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



//  Delete all data
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            bookingRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//  Create booking

    public ResponseEntity<Booking> createBookingTest(Long car_id ,Booking booking, ZonedDateTime pickupTime) {
        try {
            List<Car> carList = carRepo.findByAvailableTrue();
            Car carData = null;
            for (Car car :  carList)
                if (car.getId() == car_id){
                    carData = car;
                }
            if ( carData == null) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            booking.setPickupTime(pickupTime);
            booking.setCar(carData);
            bookingRepo.save(booking);
            return  new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // xu
    public ResponseEntity<Booking> createBooking(Long customer_id,Long car_id ,Booking booking) {
        try {
            Customer customer = customerRepo.findCustomerById(customer_id);
            List<Car> carList = carRepo.findByAvailableTrue();
            Car carData = null;
            for (Car car :  carList)
                if (car.getId() == car_id){
                    carData = car;
                }
            if (customer == null && carData == null) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            booking.setCar(carData);
            booking.setCustomer(customer);
            bookingRepo.save(booking);
            return  new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public ResponseEntity<List<Car>> getAvailableCar()




//  Get available car for customer
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

}
