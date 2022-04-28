package com.example.Assigment_2_Project.repository;

import com.example.Assigment_2_Project.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {
    default Optional<Driver> findById(Long id) {
        return this.findById(id);
    }
}
