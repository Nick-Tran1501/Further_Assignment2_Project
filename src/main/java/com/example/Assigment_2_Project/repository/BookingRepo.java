package com.example.Assigment_2_Project.repository;

import com.example.Assigment_2_Project.model.Booking;
import com.example.Assigment_2_Project.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
    default Optional<Booking> findById(Long id) {
        return this.findById(id);
    }


    Booking findBookingById(Long id);

    List<Booking> findByDropTimeBefore(ZonedDateTime zonedDateTime);
    List<Booking> findByPickupTimeBefore(ZonedDateTime zonedDateTime);
    List<Booking> findByPickupTime(ZonedDateTime pickupTime);

//    List<Booking> findByPickupTime(ZonedDateTime pickupTime);
//    List<Booking> findByCreateDate(ZonedDateTime date);ß
//    List<Booking> findByStartEndDate(ZonedDateTime startDate, ZonedDateTime endDate);
}
