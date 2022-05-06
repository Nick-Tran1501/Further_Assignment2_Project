package com.example.Assigment_2_Project.repository;

import com.example.Assigment_2_Project.model.Customer;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    default Optional<Customer> findById(Long id) {
        return this.findById(id);
    }

    Customer findCustomerById(Long id);

    List<Customer> findByName(String name);

    List<Customer> findByPhone(String phone);

    List<Customer> findByAddress(String address);

    List<Customer> findByNameAndAddress(String name, String address);

    List<Customer> findByPhoneAndAddress(String phone, String address);

    List<Customer> findByNameAndPhone(String name, String phone);

    List<Customer> findByNameAndPhoneAndAddress(String name, String phone, String address);

//    List<Customer> findAllByName(String name, Pageable pageable);

}