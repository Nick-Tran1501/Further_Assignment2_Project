package com.example.Assigment_2_Project.repository;

import com.example.Assigment_2_Project.model.Booking;
import com.example.Assigment_2_Project.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.DoubleBinaryOperator;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
    @Override
    default Optional<Car> findById(Long id) {
        return this.findById(id);
    }

    //Find by single attribute (8
    //1
    Car findCarById(Long id);
    List<Car> findByMake(String make);
    List<Car> findByModel(String model);
    List<Car> findByColor(String color);
    List<Car> findByConvertible(Boolean convertible);
    List<Car> findByLicensePlate(String licensePlate);
    List<Car> findByRatingGreaterThanEqual(Double rating);
    List<Car> findByRateKilometerGreaterThanEqual(Double rateKilometer);

}
