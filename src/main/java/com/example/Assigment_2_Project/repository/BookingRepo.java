package com.example.Assigment_2_Project.repository;

import com.example.Assigment_2_Project.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
    default Optional<Booking> findById(Long id) {
        return this.findById(id);
    }

    // Method to get booking by id (Long)
    Booking findBookingById(Long id);

    // Method to get a list of booking by status
    List<Booking> findByStatus(String status);

    // Method to get a list of booking in a period
    List<Booking> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(ZonedDateTime startTime,
                                                                                  ZonedDateTime endTime);

}
