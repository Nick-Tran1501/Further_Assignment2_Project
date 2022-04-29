package com.example.Assigment_2_Project.service;

import com.example.Assigment_2_Project.model.Customer;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService{

    @Autowired
    CustomerRepo customerRepo;

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
            List<Customer> customers = customerRepo.findAll();
            if (customers.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


//  Get customer data by specific attribute
    public ResponseEntity<List<Customer>> customerSearch(Optional<String> name, Optional<String> address, Optional<String> phone){
        try {
            List<Customer> customers = null;
            if (name.isPresent() && address.isPresent() && phone.isPresent())
                customers = ((CustomerRepo) customerRepo).findByNameAndPhoneAndAddress(name.get(), address.get(),
                        phone.get());
            else if (name.isPresent() && address.isPresent())
                customers = ((CustomerRepo) customerRepo).findByNameAndAddress(name.get(), address.get());
            else if (name.isPresent() && phone.isPresent())
                customers = ((CustomerRepo) customerRepo).findByNameAndPhone(name.get(), phone.get());
            else if (address.isPresent() && phone.isPresent())
                customers = ((CustomerRepo) customerRepo).findByPhoneAndAddress(address.get(), phone.get());
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


//    @Autowired
//    private SessionFactory sessionFactory;
//
//    private Customer customer;
//
//    @ModelAttribute
//    public Customer customer(HttpServletRequest request) {
//        request.setAttribute("name", customer.getName());
//        return customer;
//    }
//
//    //  Add Customer on Table
//    public Long addCustomer(Customer customer){
//        this.sessionFactory.getCurrentSession().saveOrUpdate(customer);
//        return customer.getId();
//    }
//
//    //  Get all Customer data
//    public List<Customer> getAllCustomer(){
//        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
//        return criteria.list();
//    }
//
//    //  Search customer by name
//    public List<Customer> searchName(String name){
//        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
//        criteria.add(Restrictions.like("name",name, MatchMode.ANYWHERE));
//        return criteria.list();
//    }
//
//    //  search customer by address
//    public List<Customer> searchAddress(String address){
//        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
//        criteria.add(Restrictions.like("address",address,MatchMode.ANYWHERE));
//        return criteria.list();
//    }
//
//    //  search customer by phone
//    public List<Customer> searchPhone(String phone){
//        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
//        criteria.add(Restrictions.like("phone",phone,MatchMode.ANYWHERE));
//        return criteria.list();
//    }
//
//    public List<Customer> searchByName(String name) {
//        List<Customer> customers = sessionFactory.getCurrentSession().createQuery("Select Customer from Customer where name =: name ")
//                .setParameter("name", name).list();
//        return customers;
//    }
}
