package com.example.Assigment_2_Project.controller;

import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import com.example.Assigment_2_Project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Customer")
public class CustomerController extends EntityController<Customer>{

    @Autowired
    public CustomerController(CustomerRepo customerRepo){
        super(customerRepo);
    }

    @Autowired
    CustomerRepo customerRepo;

    @Override
    public ResponseEntity<Customer> updateTableColumnById(Long id, Map<String, String> contentField) {
        return null;
    }

    @PostMapping(path = "/post")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customers ) {
        try {
            customerRepo.save(customers);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Customer>> getCustomers() {
        try {
            List<Customer> customers = customerRepo.findAll();
            if (customers.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }






//    Hello world


//    @Autowired
//    private CustomerService customerService;


//    @RequestMapping(path = "/customers", method = RequestMethod.POST)
//    public Long addCustomer(@RequestBody Customer customer){
//        return customerService.addCustomer(customer);
//    }
//
//    @RequestMapping(value = {"/customers"}, method = RequestMethod.GET)
//    public List<Customer> getAllCustomer(){
//        return customerService.getAllCustomer();
//    }
//
//    @RequestMapping(path = {"/searchName/{name}"}, method = RequestMethod.GET)
//    public List<Customer> searchName(@PathVariable String name){
//        return customerService.searchName(name);
//    }
//
//    @RequestMapping(path = {"/get"}, method = RequestMethod.GET)
//    public List<Customer> searchByName(@RequestAttribute("name") String name) {
//        return customerService.searchByName(name);
//    }
//
//    @RequestMapping(path = {"searchAddress/{address}"},method = RequestMethod.GET)
//    public List<Customer> searchAddress(@PathVariable String address){
//        return customerService.searchAddress(address);
//    }
//
//    @RequestMapping(path = {"searchPhone/{phone}"},method = RequestMethod.GET)
//    public List<Customer> searchPhone(@PathVariable String phone){
//        return customerService.searchPhone(phone);
//    }



}