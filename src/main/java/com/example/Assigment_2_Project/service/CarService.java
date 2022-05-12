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

import javax.transaction.Transactional;
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
        Car car =  carRepo.findCarById(id);
        return new ResponseEntity<>(car, HttpStatus.FOUND);
    }

    // search car by variables
    public ResponseEntity<List<Car>> searchCar(Optional<String> make, Optional<String> model,
                                                           Optional<String> color, Optional<Boolean> convertible,
                                                           Optional<Double> rating, Optional<Double> rateKilometer) {

        try {
            String unavailable = "Cannot find car";
            List<Car> carTemp = carRepo.findByAvailableTrue();
            if (make.isPresent())
                carTemp = carRepo.findByAvailableTrueAndMake(make.get());
            else if (model.isPresent())
                carTemp =  carRepo.findByAvailableTrueAndModel(model.get());
            else if (color.isPresent())
                carTemp = carRepo.findByAvailableTrueAndColor(color.get());
            else if (convertible.isPresent())
                carTemp = carRepo.findByAvailableTrueAndConvertibleTrue();
            else if (rating.isPresent())
                carTemp = carRepo.findByAvailableTrueAndRating(rating.get());
            else if (rateKilometer.isPresent())
                carTemp = carRepo.findByAvailableTrueAndRateKilometer(rateKilometer.get());
            return carTemp == null ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
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
            return new ResponseEntity<HashMap<String,Integer>>(result, HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Delete car by ID
    public ResponseEntity<Car> deleteByID(Long id){
        try{
            carRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete all car
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            carRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}





//    public ResponseEntity<List<Car>> getAvailableCars(Map<String, String> fields) {
//        try {
//                List<Car> cars = carRepo.findByAvailable(fields.get("available"));
////            System.out.println(cars);
//                if (cars.size() == 0) {
//                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//                }
//            System.out.println((ResponseEntity<List<Car>>) cars);
//                return (ResponseEntity<List<Car>>) cars;
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }




//    private List<Car> carList;
//
//    private List<Driver> drivers;
//
//
//
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @Autowired
//    private DriverService driverService;
//
//    public void setCarList(List<Car> carList) {
//        this.carList = carList;
//    }
//
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    public Long createCar(Car car){
//        getAllCar().add(car);
//        assignCarToDriver(car);
//        sessionFactory.getCurrentSession().save(car);
//        return car.getId();
//    }
//    //View all cars
//    public List<Car> getAllCar() {
//        carList = this.sessionFactory.getCurrentSession().createQuery("from Car").list();
//        return carList;
//    }
//
//    public void assignCarToDriver(Car car) {
//        driverService.getAllDriver();
//        for (Driver driver : driverService.getDrivers()) {
//            if (driver.getCar() == null){
//                driver.setCar(car);
//            }
//        }
//    }
//
//    //Delete car
//    public String deleteCarByID(Long id) {
//        Car car = sessionFactory.getCurrentSession().get(Car.class, id);
//        this.sessionFactory.getCurrentSession().delete(car);
//        return "Delete successfully";
//    }
//
//    public Car updateCar(Car car) {
//        Car carTemp = sessionFactory.getCurrentSession().get(Car.class, car.getId());
//        this.sessionFactory.getCurrentSession().update(car);
//        return car;
//    }
//

