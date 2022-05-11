package com.example.Assigment_2_Project.controller;

import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import com.example.Assigment_2_Project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/customer")
public class CustomerController extends EntityController<Customer>{

    @Autowired
    public CustomerController(CustomerRepo customerRepo){
        super(customerRepo);
    }

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepo customerRepo;


    // Update customer by ID
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateTableColumnById(@PathVariable("id") Long id, @RequestBody Map<String, String> contentField) {
        try {
            Customer customer = customerRepo.findCustomerById(id);
            if (contentField.containsKey("name"))
                customer.setName(contentField.get("name"));

            if (contentField.containsKey("address"))
                customer.setAddress(contentField.get("address"));

            if (contentField.containsKey("phone"))
                customer.setPhone(contentField.get("phone"));

            customerRepo.save(customer);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create list customer sample
    @Override
    @PostMapping(path = "/demo")
    public ResponseEntity<List<Customer>> inputDemoData(@Validated @RequestBody List<Customer> data) {
        try {
            customerRepo.saveAll(data);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create customer manual
    @PostMapping(path = "/post")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customers ) {
        return customerService.addCustomer(customers);
    }

    //  Get all customer data
    @GetMapping(path = "/all")
    public ResponseEntity<List<Customer>> getCustomer(){
        return customerService.getCustomers();
    }

    // Search customer by variables
    @GetMapping(path = "/search")
    public ResponseEntity<List<Customer>> customerSearch(
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<String> phone,
            @RequestParam(required = false) Optional<String> address){
        return customerService.customerSearch(name,phone,address);
    }

    //  Find by ID
    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Customer> getById(@PathVariable("id") Long id){
        return customerService.getByID(id);
    }

    // Delete all customer
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll(){
        return customerService.deleteAll();
    }

    // Delete by ID
    @DeleteMapping(path = "/deleteID/{id}")
    public ResponseEntity<Customer> deleteByID(@PathVariable("id") Long id){
        return customerService.deleteByID(id);
    }

}