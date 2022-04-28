package com.example.Assigment_2_Project.controller;

import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CustomerController")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(path = "/customers", method = RequestMethod.POST)
    public Long addCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }

    @RequestMapping(value = {"/customers"}, method = RequestMethod.GET)
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    @RequestMapping(path = {"/searchName/{name}"}, method = RequestMethod.GET)
    public List<Customer> searchName(@PathVariable String name){
        return customerService.searchName(name);
    }

    @RequestMapping(path = {"searchAddress/{address}"},method = RequestMethod.GET)
    public List<Customer> searchAddress(@PathVariable String address){
        return customerService.searchAddress(address);
    }

    @RequestMapping(path = {"searchPhone/{phone}"},method = RequestMethod.GET)
    public List<Customer> searchPhone(@PathVariable String phone){
        return customerService.searchPhone(phone);
    }

}