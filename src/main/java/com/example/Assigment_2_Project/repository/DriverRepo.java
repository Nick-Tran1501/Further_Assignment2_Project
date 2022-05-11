package com.example.Assigment_2_Project.repository;

import com.example.Assigment_2_Project.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {
    default Optional<Driver> findById(Long id) {
        return this.findById(id);
    }

    Driver findDriverById(Long id);
    Driver deleteDriverById(Long id);

    List<Driver> findByLicense(String license);
    List<Driver> findByPhone(String phone);
    List<Driver> findByRating(Double rating);
    List<Driver> findByName(String name);
    List<Driver> findByNameAndPhone(String name, String phone);
    List<Driver> findByNameAndRating(String name, Double rating);
}
