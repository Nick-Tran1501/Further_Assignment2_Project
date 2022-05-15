package com.example.Assigment_2_Project.service;

import com.example.Assigment_2_Project.model.Booking;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.repository.BookingRepo;
import com.example.Assigment_2_Project.repository.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
public class CarService {

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private BookingRepo bookingRepo;


    // Create car manual
    public ResponseEntity<Car> addCar(Car car) {
        try {
            List<Car> carList = carRepo.findAll();
            for (Car temp: carList)
                if (car.getLicensePlate().equalsIgnoreCase(temp.getLicensePlate())){
                    return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
                }
            carRepo.save(car);
            return new ResponseEntity<>(car, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all car
    public ResponseEntity<List<Car>> getCars() {
        try {
            List<Car> cars = carRepo.findAll();
            if (cars.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Get car by id
    public ResponseEntity<Car> getCarById(Long id){
        try {
            Car car =  carRepo.findCarById(id);
            return  car == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND):
                    new ResponseEntity<>(car, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public List<Car> getAvailableCar(String date, String time) {
        String strDateTime = date + "T" + time+ ":00.000Z";
        ZonedDateTime pickupTime = ZonedDateTime.parse(strDateTime);
        List<Car> carList = carRepo.findAll();
        carList.removeIf(car -> car.getDriver() == null);
        List<Booking> bookingList = bookingRepo.findByStatus("Ready");
        for (Booking booking : bookingList){
            ZonedDateTime timeTemp = booking.getPickupTime();
            if (pickupTime.compareTo(timeTemp) < 1){
                carList.remove(booking.getCar());
            }
        }

//        for (Booking booking : bookingList) {
//            ZonedDateTime timeTemp = booking.getPickupTime();
//            if (timeTemp.getYear() == pickupTime.getYear()
//                    && timeTemp.getMonth().equals(pickupTime.getMonth())
//                    && timeTemp.getDayOfMonth() == pickupTime.getDayOfMonth()){
//                carList.remove(booking.getCar());
//            }
//        }
        return carList;
    }

    public void setCarAvailableFinish(Car car){
        car.setAvailable(true);
        carRepo.save(car);
    }

    // search car by variables
    public ResponseEntity<List<Car>> searchCar(Optional<String> make, Optional<String> model,
                                               Optional<String> color, Optional<Boolean> convertible,
                                               Optional<String> licensePlate, Optional<Double> rating,
                                               Optional<Double> rateKilometer, Optional<Boolean> available) {

        try {
            List<Car> carTemp = null;
            if (make.isPresent())
                carTemp = carRepo.findByMake(make.get());
            else if (model.isPresent())
                carTemp =  carRepo.findByModel(model.get());
            else if (color.isPresent())
                carTemp = carRepo.findByColor(color.get());
            else if (licensePlate.isPresent())
                carTemp = carRepo.findByLicensePlate(licensePlate.get());
            else if (convertible.isPresent())
                carTemp = carRepo.findByConvertible(convertible.get());
            else if (rating.isPresent())
                carTemp = carRepo.findByRatingGreaterThanEqual(rating.get());
            else if (rateKilometer.isPresent())
                carTemp = carRepo.findByRateKilometerGreaterThanEqual(rateKilometer.get());
            else if (available.isPresent())
                carTemp = carRepo.findByAvailable(available.get());
            return carTemp.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(carTemp, HttpStatus.FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // car used by month
    public ResponseEntity<HashMap<String,Integer>> carsUsed(String year, String month){
        try{
            List<Booking> bookingList = bookingRepo.findAll();
            HashMap<String, Integer> result = new HashMap<>();
            for (Booking booking : bookingList) {
                String bookingYear = String.valueOf(booking.getCreatedDate().getYear());
                String bookingMonth = String.valueOf(booking.getCreatedDate().getMonth());
                if (bookingYear.equalsIgnoreCase(year) && bookingMonth.equalsIgnoreCase(month)){
                    String key = booking.getCar().getLicensePlate();
                    if (result.containsKey(key)){
                        Integer days = result.get(key) + 1;
                        result.remove(key);
                        result.put(key,days);
                    }
                    else {
                        result.put(key,1);
                    }
                }
            }
            return new ResponseEntity<>(result, HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Delete car by ID
    public ResponseEntity<Car> deleteByID(Long id){
        try{
            if (carRepo.findCarById(id) == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            carRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete all car
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            carRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
