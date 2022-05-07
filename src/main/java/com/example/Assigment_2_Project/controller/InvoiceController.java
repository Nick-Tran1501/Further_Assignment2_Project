package com.example.Assigment_2_Project.controller;

import com.example.Assigment_2_Project.model.Invoice;
import com.example.Assigment_2_Project.repository.InvoiceRepo;
import com.example.Assigment_2_Project.service.InvoiceService;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Access;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/invoice")
@Validated
public class InvoiceController extends EntityController<Invoice> {

    public InvoiceController(JpaRepository<Invoice, Long> repo) {
        super(repo);
    }

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private InvoiceService invoiceService;


    @GetMapping(path = "/test")
    public ResponseEntity<List<Invoice>> findByPeriod(@RequestParam String start, @RequestParam String end){
        return invoiceService.findByPeriod(start, end);
    }

    @Override
    public ResponseEntity<Invoice> updateTableColumnById(Long id, Map<String, String> contentField) {
        return null;
    }

    @Override
    public ResponseEntity<List<Invoice>> inputDemoData(List<Invoice> data) {
        return null;
    }
}
