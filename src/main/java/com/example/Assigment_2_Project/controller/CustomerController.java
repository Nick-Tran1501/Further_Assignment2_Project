package com.example.Assigment_2_Project.controller;

import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.repository.CustomerRepo;
import com.example.Assigment_2_Project.service.CustomerService;
import org.apache.catalina.util.ResourceSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController extends EntityController<Customer>{

    @Autowired
    public CustomerController(CustomerRepo customerRepo){
        super(customerRepo);
    }

    @Autowired
    private CustomerService customerService;


    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateTableColumnById(Long id, Map<String, String> contentField) {
        return null;
    }

    @Override
    public ResponseEntity<List<Customer>> inputDemoData(List<Customer> data) {
        return null;
    }


    //  Add student on table
    @PostMapping(path = "/post")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customers ) {
        return customerService.addCustomer(customers);
    }


//  Get all customer data
    public ResponseEntity<List<Customer>> getCustomer(){
        return customerService.getCustomers();
    }

//  Implement search customer function
//  http://localhost:8080/Customer/search?name=Tuan (search 1 param)
//  http://localhost:8080/Customer/search?name=Tuan&&address=Sky Garden (search > 1 params) (%20 = space)
    @GetMapping(path = "/search")
    public ResponseEntity<List<Customer>> customerSearch(
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<String> address,
            @RequestParam(required = false) Optional<String> phone){
        return customerService.customerSearch(name,address,phone);
    }

//  Find by ID
    @GetMapping(path = "/id/{id}")
    public ResponseEntity<List<Customer>> getById(@PathVariable("id") Long id){
        return customerService.getByID(id);
    }


// 


////   Old

//    @Autowired
//    private CustomerService customerService;
//
//    @RequestMapping(path = "/customers", method = RequestMethod.DELETE)
//    public Long deleteCustomers(@PathVariable Long id){
//
//    }

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