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
import org.springframework.web.bind.annotation.*;

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
    private InvoiceService invoiceService;

    // Get all invoices, invoices by customers, invoice by drivers
    @GetMapping(path = "/search")
    public ResponseEntity<List<Invoice>> findInvoice(@RequestParam String searchBy,
                                                     @RequestParam Long id,
                                                     @RequestParam String start,
                                                     @RequestParam String end){
        return invoiceService.findInvoice(searchBy, id, start, end);
    }

    // Get total revenue, revenue by customers, revenues by drivers
    @GetMapping(path = "/revenue")
    public ResponseEntity<Double> getRevenue(@RequestParam String searchBy,
                                             @RequestParam Long id,
                                             @RequestParam String start,
                                             @RequestParam String end) {
        return invoiceService.getRevenue(searchBy, id, start, end);
    }

    // Override method to update invoice table by id (not used)
    @Override
    @PutMapping(path = "/{id}")
    public ResponseEntity<Invoice> updateTableColumnById(@PathVariable("id") Long id, @RequestParam Map<String, String> contentField) {
        return null;
    }

    // Override method to create a list of sample data (not used)
    @Override
    public ResponseEntity<List<Invoice>> inputDemoData(List<Invoice> data) {
        return null;
    }
}
