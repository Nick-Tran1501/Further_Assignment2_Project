package com.example.Assigment_2_Project.service;

import com.example.Assigment_2_Project.model.Driver;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DriverService {


    @Autowired
    private SessionFactory sessionFactory;

    private List<Driver> drivers;


    public List<Driver> getDrivers() {

        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        drivers = sessionFactory.getCurrentSession().createQuery("from Driver ").list();
        this.drivers = drivers;
    }

    public Long addDriver(Driver driver) {
//        getDrivers().add(driver);
        this.sessionFactory.getCurrentSession().save(driver);
        return driver.getId();
    }


//    public List<Driver> getAllDriver() {
//        this.drivers = sessionFactory.getCurrentSession().createQuery("from Driver ").list();
//        return this.drivers;
//    }
//
//    public String

}
