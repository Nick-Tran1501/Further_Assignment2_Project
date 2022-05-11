package com.example.Assigment_2_Project.controller;

import com.example.Assigment_2_Project.repository.CarRepo;
import org.apache.catalina.util.ResourceSet;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
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
import java.util.ResourceBundle;

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

    @PutMapping(path = "/{id}")
    public abstract ResponseEntity<T> updateTableColumnById(@PathVariable("id") Long id, @RequestBody Map<String, String> contentField);


    @PostMapping(path = "/testData")
    public abstract ResponseEntity<List<T>> inputDemoData(@RequestBody List<T> data);

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            repo.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
