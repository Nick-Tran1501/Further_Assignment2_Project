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


    Booking findBookingById(Long id);
    Booking deleteBookingById(Long id);

    List<Booking> findByStatus(String status);

    List<Booking> findByDropTimeBefore(ZonedDateTime zonedDateTime);
    List<Booking> findByPickupTimeBefore(ZonedDateTime zonedDateTime);
    List<Booking> findByPickupTime(ZonedDateTime pickupTime);
    List<Booking> findByCreatedDateIsBetween(ZonedDateTime startTime, ZonedDateTime endTime);
    List<Booking> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(ZonedDateTime startTime,
                                                                                  ZonedDateTime endTime);

    List<Booking> findByCreatedDateAfterAndCreatedDateBefore(ZonedDateTime startDate, ZonedDateTime endDate);

}
