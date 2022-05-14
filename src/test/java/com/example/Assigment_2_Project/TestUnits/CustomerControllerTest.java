package com.example.Assigment_2_Project.TestUnits;


import com.example.Assigment_2_Project.controller.CustomerController;
import com.example.Assigment_2_Project.model.Customer;
import com.example.Assigment_2_Project.repository.CustomerRepo;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerTest {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CustomerController customerController;

    @Test
    void loadContest() { assertNotNull(customerController);}


    @Test
    @Order(1)
    void createCustomer() {
        Customer customer = new Customer();
        customer.setPhone("0777042801");
        customer.setAddress("702 Nguyen Hue");
        customer.setName("Khoi Nguyen");

        ResponseEntity<Customer> res = customerController.addCustomer(customer);
        assertEquals(res.getBody(), customer);
        assertEquals(res.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    @Order(2)
    void searchCustomer() {
        Optional<String> name = Optional.of("Khoi Nguyen");
        Optional<String> phone = Optional.of("0777042801");
        Optional<String> address = Optional.of("702 Nguyen Hue");

        ResponseEntity<List<Customer>> res = customerController.customerSearch(name, phone, address);
        List<Customer> customerList = res.getBody();
        assertEquals(res.getBody(), customerList);
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Order(3)
    void updateCustomer() {
        Long id  = 1L;
        Map<String, String> contentField = new HashMap<>();
        contentField.put("name", "Tuan Oh Yeah");

        ResponseEntity<Customer> res = customerController.updateTableColumnById(id, contentField);
        Customer customer = res.getBody();
        assertEquals(res.getBody(), customer);
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Order(4)
    void getCustomerById() {
        Long id = 1L;
        ResponseEntity<Customer> res = customerController.getById(id);
        Customer customer = res.getBody();
        assertEquals(res.getBody(), customer);
        assertEquals(res.getStatusCode(), HttpStatus.FOUND);

    }

    @Test
    @Order(5)
    void deleteById() {
        Customer customer = new Customer();
        customer.setPhone("0777042802");
        customer.setAddress("702 Nguyen Hue");
        customer.setName("Khoi Nguyen");

        ResponseEntity<Customer> addCustomer = customerController.addCustomer(customer);

        Long id = customer.getId();
        ResponseEntity<Customer> res = customerController.deleteByID(id);
        assertEquals(res.getStatusCode(), HttpStatus.NO_CONTENT);

    }

    @Test
    @Order(6)
    void deleteAll() {
        ResponseEntity<HttpStatus> res = customerController.deleteAll();
        assertEquals(res.getStatusCode(), HttpStatus.NO_CONTENT);
    }

}
