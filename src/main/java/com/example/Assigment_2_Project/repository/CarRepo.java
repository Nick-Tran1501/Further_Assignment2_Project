package com.example.Assigment_2_Project.repository;

import com.example.Assigment_2_Project.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
    @Override
    default Optional<Car> findById(Long id) {
        return this.findById(id);
    }

    //Find by single attribute (8
    //1
//    List<Car> findByAvailable(boolean available);
//    List<Car> findByAvailableIsTrue(Boolean available);

    List<Car> findByAvailable(String available);
    //2
    List<Car> findByMake(String make);
    //3
    List<Car> findByModel(String model);
    //4
    List<Car> findByColor(String color);
    //5
    List<Car> findByConvertible(Boolean convertible);
    //6
    List<Car> findByLicensePlate(String licensePlate);
    //7
    List<Car> findByRating(Double rating);
    //8
    List<Car> findByRateKilometer(Double rateKilometer);

    //Find by available and (6)
    //Make
    List<Car> findByAvailableAndMake(String available, String make);
    //Model
    List<Car> findByAvailableAndModel(String available, String model);
    //Color
    List<Car> findByAvailableAndColor(String available, String color);
    //Convertible
    List<Car> findByAvailableAndConvertible(String available, Boolean convertible);
    //Rating
    List<Car> findByAvailableAndRating(String available, Double rating);
    //RateKilometer
    List<Car> findByAvailableAndRateKilometer(String available, Double rateKilometer);


}
