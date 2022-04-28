package com.example.Assigment_2_Project.repository;

import com.example.Assigment_2_Project.model.Customer;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    default Optional<Customer> findById(Long id) {
        return this.findById(id);
    }

    List<Customer> findByName(String name);
    List<Customer> findByPhone(String phone);
    List<Customer> findByAddress(String address);
    List<Customer> findByNameAddress(String name, String address);
    List<Customer> findByNamePhone(String name, String phone);
    List<Customer> findByAddressPhone (String address, String phone);
    List<Customer> findByAll(String name, String address, String phone);
}
