# Taxi Booking service

This project is a system that automates business processes of a taxi company, including:  
. Providing the organization an automatical system that create, managing and storing data of booking, customers, vehicles and invoices. 
. Supporting customers with an online platform to book and reserve the taxi according to their demands at their own conviniences.
. Assisting the drivers in selecting vehicles, accepting booking, cancelling or finishing booking.

## Authors

- [@KhoiNguyen-281](https://github.com/KhoiNguyen-281)
- [@Andy1703](https://github.com/Andy1703)
- [@Nick-Tran1501](https://github.com/Nick-Tran1501)
- [@ngocduan99](https://github.com/ngocduan99)


## Tech Stack

**IntelliJ:**
- IDE to support coding and run the system.
**Java:** 
- Main programming language in this project;
- Applying the Spring Boot, JPA Hibernate, Repository and Maven to perform CRUD for all entities;

**Spring Boot:** 
- Spring Boot, Spring Web, Spring MVC, creating a web application of Spring;
- By the Spring Web MVC, application will have a static homepage and accept the HTTP request through the URL: "HTTP://localhost:8080/method_path";
- Spring Data JPA helps to implement JPA repositories;
- Spring MVC (Model, View, Controller) supporting implement Spring framework's features;
**Hibernate:**
- Is a Java ORM (Object Relational Mapping) tool, maps the domain objects and takes action to the relational database with the assistance of POSTMAN, without SQL syntax.
**PgAdmin4:**
- Display data table and save all data changed.
**Spring Data Repository:**
- Supporting in performing CRUD, get the model by its own attributes, and store its data.
**POSTMAN:**
- Static homepage to send HTTP request to system with URL: "HTTP://localhost:8080/method_path".
**Github:**
- A cloud platform used to store versions of code, managing the process of the project
## Features

- CRUD basic features, supporting Create, Read, Update and Delete objects of models (Customer, Driver, Car, Booking, Invoice).
**Advance features:**
- Drivers are able to perform actions: 
    - Select available cars
    - Change booking status (after the booking is finished)
- Viewing cars' used duration within a month
- Auto cancel booking (Booking can be reserved within a day)

