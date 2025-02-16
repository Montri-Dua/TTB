package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.example.demo.repository.*;
import com.example.demo.dto.ErrorResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    public CustomerController(CustomerService customerRepository) {
        this.customerService = customerRepository;
    }
    
    
    // GET - ดึงลูกค้าทั้งหมด
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // POST - Create Customer	
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }
    
 // ✅ GET Customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
        	 return ResponseEntity.status(404).body(new ErrorResponse("NOT_FOUND", "Customer ID " + id + " not found."));
        }
    }

    // ✅ UPDATE Customer by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setFirstname(updatedCustomer.getFirstname());
            customer.setLastname(updatedCustomer.getLastname());
            customer.setCustomerDate(updatedCustomer.getCustomerDate());
            customer.setIsVIP(updatedCustomer.getIsVIP());
            customer.setStatusCode(updatedCustomer.getStatusCode());
            customer.setModifiedOn(updatedCustomer.getModifiedOn());

            customerRepository.save(customer);
            return ResponseEntity.ok("Customer ID " + id + " updated successfully.");
        } else {
        	 return ResponseEntity.status(404).body(new ErrorResponse("NOT_FOUND", "Customer ID " + id + " not found."));
        }
    }

    // ✅ DELETE Customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            customerRepository.deleteById(id);
            return ResponseEntity.ok("Customer ID " + id + " deleted successfully.");
        } else {
        	 return ResponseEntity.status(404).body(new ErrorResponse("NOT_FOUND", "Customer ID " + id + " not found."));
        }
    }
    
 }
