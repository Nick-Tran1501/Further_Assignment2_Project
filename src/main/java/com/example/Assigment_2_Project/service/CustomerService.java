package com.example.Assigment_2_Project.service;

import com.example.Assigment_2_Project.model.Customer;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private SessionFactory sessionFactory;

    //  ADD Customer on Table
    public Long addCustomer(Customer customer){
        this.sessionFactory.getCurrentSession().saveOrUpdate(customer);
        return customer.getId();
    }

    //  Get all Customer data
    public List<Customer> getAllCustomer(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        return criteria.list();
    }

    //  Seacrh customer by name
    public List<Customer> searchName(String name){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        criteria.add(Restrictions.like("name",name, MatchMode.ANYWHERE));
        return criteria.list();
    }

    //  search customer by address
    public List<Customer> searchAddress(String address){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        criteria.add(Restrictions.like("address",address,MatchMode.ANYWHERE));
        return criteria.list();
    }

    //  search customer by phone
    public List<Customer> searchPhone(String phone){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        criteria.add(Restrictions.like("phone",phone,MatchMode.ANYWHERE));
        return criteria.list();
    }
}
