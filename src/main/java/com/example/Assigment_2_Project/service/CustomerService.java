package com.example.Assigment_2_Project.service;

import com.example.Assigment_2_Project.model.Booking;
import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.repository.BookingRepo;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private BookingRepo bookingRepo;

    // Add customers
    public ResponseEntity<Customer> addCustomer(Customer customers) {
        try {
            customerRepo.save(customers);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Get all customers data
    public ResponseEntity<List<Customer>> getCustomers() {
        try {
            List<Customer> customer = customerRepo.findAll();
            if (customer.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Get customer data by specific attribute
    // Must follow structure of customer class
    public ResponseEntity<List<Customer>> customerSearch(Optional<String> name, Optional<String> phone, Optional<String> address) {
        try {
            List<Customer> customers = null; // result list
            if (name.isPresent() && phone.isPresent() && address.isPresent()) // check 3 elements exist
                customers = ((CustomerRepo) customerRepo).findByNameAndPhoneAndAddress(name.get(), phone.get(),
                        address.get());
            else if (name.isPresent() && address.isPresent())
                customers = ((CustomerRepo) customerRepo).findByNameAndAddress(name.get(), address.get());
            else if (name.isPresent() && phone.isPresent())
                customers = ((CustomerRepo) customerRepo).findByNameAndPhone(name.get(), phone.get());
            else if (phone.isPresent() && address.isPresent() )
                customers = ((CustomerRepo) customerRepo).findByPhoneAndAddress(phone.get(), address.get());
            else if (name.isPresent())
                customers = ((CustomerRepo) customerRepo).findByName(name.get());
            else if (address.isPresent())
                customers = ((CustomerRepo) customerRepo).findByAddress(address.get());
            else if (phone.isPresent())
                customers = ((CustomerRepo) customerRepo).findByPhone(phone.get());

            return customers == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

//  Get customer by id
    public ResponseEntity<Customer> getByID(Long id){
        try {
            Customer customers = customerRepo.findCustomerById(id);
            return customers == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(customers, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//  Delete by ID
    public ResponseEntity<Customer> deleteByID(Long id){
        try{
            customerRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
// delete all
    public ResponseEntity<HttpStatus> deleteAll() {
         try {
             customerRepo.deleteAll();
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         } catch (Exception e){
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
          }
    }

}