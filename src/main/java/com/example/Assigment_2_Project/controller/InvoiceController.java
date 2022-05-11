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
    private InvoiceRepo invoiceRepo;

    @Autowired
    private InvoiceService invoiceService;


//    @GetMapping(path = "/test")
//    public ResponseEntity<List<Invoice>> findByPeriod(@RequestParam String start, @RequestParam String end){
//        return invoiceService.findByPeriod(start, end);
//    }

    //    @GetMapping(path = "/byCustomer")
//    public ResponseEntity<List<Invoice>> findByCustomer(@RequestParam Long cusID,
//                                                        @RequestParam String startDate,
//                                                        @RequestParam String endDate) {
//        return invoiceService.findByCustomer(cusID, startDate, endDate);
//    }
//
//    @GetMapping(path = "/byDriver/{id}")
//    public ResponseEntity<List<Invoice>> findByDriver(@PathVariable("id") Long driverID,
//                                                      @RequestParam String start,
//                                                      @RequestParam String end){
//        return invoiceService.findByDriver(driverID, start, end);
//    }
    @GetMapping(path = "/search")
    public ResponseEntity<List<Invoice>> findInvoice(@RequestParam String searchBy,
                                                     @RequestParam Long id,
                                                     @RequestParam String start,
                                                     @RequestParam String end){
        return invoiceService.findInvoice(searchBy, id, start, end);
    }


    @GetMapping(path = "/revenue")
    public ResponseEntity<Double> getRevenue(@RequestParam String searchBy,
                                             @RequestParam Long id,
                                             @RequestParam String start,
                                             @RequestParam String end) {
        return invoiceService.getRevenue(searchBy, id, start, end);
    }

    @Override
    @PutMapping(path = "/{id}")
    public ResponseEntity<Invoice> updateTableColumnById(@PathVariable("id") Long id, @RequestParam Map<String, String> contentField) {
        return null;
    }

    @Override
    public ResponseEntity<List<Invoice>> inputDemoData(List<Invoice> data) {
        return null;
    }
}
