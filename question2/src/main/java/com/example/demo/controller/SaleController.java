package com.example.demo.controller;

import com.example.demo.entity.Sales;
import com.example.demo.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SalesService salesService;

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
    public Sales createSale(@RequestBody Sales sales) {
        return salesService.saveSale(sales);
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
