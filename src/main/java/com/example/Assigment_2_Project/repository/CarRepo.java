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
    List<Car> findByAvailableTrue();
    List<Car> findByAvailable(Boolean available);
    List<Car> findByMake(String make);
    List<Car> findByModel(String model);
    List<Car> findByColor(String color);
    List<Car> findByConvertibleTrue();
    List<Car> findByConvertible(Boolean convertible);
    List<Car> findByLicensePlate(String licensePlate);
    List<Car> findByRatingGreaterThanEqual(Double rating);
    List<Car> findByRateKilometerGreaterThanEqual(Double rateKilometer);

    List<Car> findByMakeAndModel(String make, String model);
    List<Car> findByMakeAndColor(String make, String color);
    List<Car> findByMakeAndConvertible(String make, Boolean convertible);
    List<Car> findByMakeAndRatingGreaterThanEqual(String make, Double rating);
    List<Car> findByMakeAndRateKilometerGreaterThanEqual(String make, Double rateKilometer);
    List<Car> findByMakeAndAvailable(String make, Boolean available);

    List<Car> findByModelAndColor(String model, String color);
    List<Car> findByModelAndConvertible(String model, Boolean convertible);
    List<Car> findByModelAndRatingGreaterThanEqual(String model, Double rating);
    List<Car> findByModelAndRateKilometerGreaterThanEqual(String model, Double rateKilometer);
    List<Car> findByModelAndAvailable(String model, Boolean available);

    List<Car> findByColorAndConvertible(String color, Boolean convertible);
    List<Car> findByColorAndRatingGreaterThanEqual(String color, Double rating);
    List<Car> findByColorAndRateKilometerGreaterThanEqual(String color, Double rateKilometer);
    List<Car> findByColorAndAvailable(String color, Boolean available);

    List<Car> findByConvertibleAndRatingGreaterThanEqual(Boolean convertible, Double rating);
    List<Car> findByConvertibleAndRateKilometerGreaterThanEqual(Boolean convertible, Double rateKilometer);
    List<Car> findByConvertibleAndAvailable(Boolean convertible, Boolean available);

    List<Car> findByRatingGreaterThanEqualAndAvailable(Double rating, Boolean available);
    List<Car> findByRatingGreaterThanEqualAndRateKilometerGreaterThanEqual(Double rating, Double rateKilometer);

    List<Car> findByRateKilometerGreaterThanEqualAndAvailable(Double rateKilometer, Boolean available);

    List<Car> findByMakeAndModelAndColor(String make, String model, String color);
    List<Car> findByMakeAndModelAndConvertible(String make, String model, Boolean convertible);
    List<Car> findByMakeAndModelAndRatingGreaterThanEqual(String make, String model, Double rating);
    List<Car> findByMakeAndModelAndRateKilometerGreaterThan(String make, String model, Double rateKilometer);
    List<Car> findByMakeAndModelAndAvailable(String make, String model, Boolean available);

    List<Car> findByMakeAndColorAndConvertible(String make, String color, Boolean convertible);
    List<Car> findByMakeAndColorAndRatingGreaterThanEqual(String make, String color, Double rating);
    List<Car> findByMakeAndColorAndRateKilometerGreaterThanEqual(String make, String color, Double rateKilometer);
}
