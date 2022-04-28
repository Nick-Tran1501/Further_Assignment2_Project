package com.example.Assigment_2_Project.service;

import com.example.Assigment_2_Project.model.Car;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CarService {

    private List<Car> carList;

    @Autowired
    private SessionFactory sessionFactory;

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Long createCar(Car car){

        sessionFactory.getCurrentSession().save(car);
        return car.getId();
    }

}
