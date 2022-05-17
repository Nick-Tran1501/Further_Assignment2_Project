package com.example.Assigment_2_Project.repository;

import com.example.Assigment_2_Project.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {

    // Method to get a driver by id
    Driver findDriverById(Long id);

    // Methods to search driver
    List<Driver> findByLicense(String license);
    List<Driver> findByPhone(String phone);
    List<Driver> findByRating(Double rating);
    List<Driver> findByName(String name);
    List<Driver> findByNameAndPhone(String name, String phone);
    List<Driver> findByNameAndRating(String name, Double rating);
}
