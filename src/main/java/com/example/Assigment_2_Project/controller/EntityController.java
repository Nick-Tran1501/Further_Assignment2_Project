package com.example.Assigment_2_Project.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class EntityController<T> {
    protected final JpaRepository<T, Long> repo;

    public EntityController(JpaRepository<T, Long> repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<T> add(@RequestBody T t) {
        try {
            return new ResponseEntity<>(repo.save(t), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<T>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        try {
            Pageable createPage =  PageRequest.of(page, pageSize, Sort.by(sortBy));
            Page<T> pageRe = repo.findAll(createPage);
            List<T> res = null;
            if (pageRe.hasContent()) {
                res = pageRe.getContent();
            } else {
                res = new ArrayList<T>();
            }
            return new ResponseEntity<>(res, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<T> getByID(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(repo.findById(id).get(), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Override method to update column by id
    @PutMapping(path = "/{id}")
    public abstract ResponseEntity<T> updateTableColumnById(@PathVariable("id") Long id, @RequestBody Map<String, String> contentField);

    // Override method to post list of sample data
    @PostMapping(path = "/testData")
    public abstract ResponseEntity<List<T>> inputDemoData(@RequestBody List<T> data);

    // Method to delete all data in table
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            repo.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method to delete row data by id
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
