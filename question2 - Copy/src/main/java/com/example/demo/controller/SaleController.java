package com.example.demo.controller;

import com.example.demo.entity.Sales;
import com.example.demo.service.CustomerService;
import com.example.demo.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.example.demo.entity.*;
import com.example.demo.repository.CustomerRepository;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SalesService salesService;
    
    @Autowired
    private CustomerRepository customerRepository;
    

    // 🔹 GET - ดึงข้อมูลการขายทั้งหมด
    @GetMapping
    public List<Sales> getAllSales() {
        return salesService.getAllSales();
    }

    // 🔹 GET - ดึงข้อมูลการขายตาม ID
    @GetMapping("/{id}")
    public Optional<Sales> getSaleById(@PathVariable Long id) {
        return salesService.getSaleById(id);
    }

    // 🔹 POST - เพิ่มรายการขาย
    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody Sales sales) {
        Long customerId = sales.getCustomer().getCustomerId(); // ดึง customerId จาก request
        
        // ตรวจสอบว่ามี customerId อยู่ในฐานข้อมูลหรือไม่
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            return ResponseEntity.badRequest().body("Customer ID " + customerId + " not found.");
        }

        // ถ้าพบ Customer ให้สร้าง Sale ได้
        sales.setCustomer(customer.get());
        Sales savedSale = salesService.saveSale(sales);
        return ResponseEntity.ok(savedSale);
        
    }

    // 🔹 PUT - อัปเดตข้อมูลการขาย
    @PutMapping("/{id}")
    public Sales updateSale(@PathVariable Long id, @RequestBody Sales saleDetails) {
        return salesService.updateSale(id, saleDetails);
    }

    // 🔹 DELETE - ลบข้อมูลการขาย
    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable Long id) {
        salesService.deleteSale(id);
    }
}
