package com.example.Assigment_2_Project.service;

import com.example.Assigment_2_Project.model.Car;
import com.example.Assigment_2_Project.model.Driver;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CarService {

    private List<Car> carList;

    private List<Driver> drivers;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DriverService driverService;

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Long createCar(Car car){
        getAllCar().add(car);
        assignCarToDriver(car);
        sessionFactory.getCurrentSession().save(car);
        return car.getId();
    }

    public List<Car> getAllCar() {
        carList = this.sessionFactory.getCurrentSession().createQuery("from Car").list();
        return carList;
    }

    public void assignCarToDriver(Car car) {
        driverService.getAllDriver();
        for (Driver driver : driverService.getDrivers()) {
            if (driver.getCar() == null){
                driver.setCar(car);
            }
        }
    }

}
