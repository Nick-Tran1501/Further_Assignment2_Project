package com.example.Assigment_2_Project.repository;

import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.model.Driver;
import com.example.Assigment_2_Project.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long> {
    default Optional<Invoice> findById(Long id) {
        return this.findById(id);
    }

    Invoice findInvoiceById(Long id);
    Invoice deleteInvoiceById(Long id);


    List<Invoice> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(ZonedDateTime startTime, ZonedDateTime endOTime);
    List<Invoice> findByCustomerAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(Customer customer, ZonedDateTime startDate,
                                                                                          ZonedDateTime endDate);
    List<Invoice> findByDriverAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(Driver driver,
                                                                                        ZonedDateTime startDate,
                                                                                        ZonedDateTime endDate);
}
