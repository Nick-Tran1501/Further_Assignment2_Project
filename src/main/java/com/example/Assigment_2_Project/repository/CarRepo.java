package com.example.Assigment_2_Project.repository;

import com.example.Assigment_2_Project.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepo extends JpaRepository<Car, Long > {
    default Optional<Car> findById(Long id) {
        return this.findById(id);
    }

    List<Car> findByAvailable(String available);
}
