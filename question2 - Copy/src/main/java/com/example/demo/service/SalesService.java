package com.example.demo.service;

import com.example.demo.entity.Sales;
import com.example.demo.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesService {

    @Autowired
    private SalesRepository salesRepository;

    public List<Sales> getAllSales() {
        return salesRepository.findAll();
    }

    public Optional<Sales> getSaleById(Long id) {
        return salesRepository.findById(id);
    }

    public Sales saveSale(Sales sales) {
        return salesRepository.save(sales);
    }

    public Sales updateSale(Long id, Sales saleDetails) {
        return salesRepository.findById(id).map(sale -> {
            sale.setSaleAmount(saleDetails.getSaleAmount());
            sale.setSaleDate(saleDetails.getSaleDate());
            return salesRepository.save(sale);
        }).orElseThrow(() -> new RuntimeException("Sale not found"));
    }

    public void deleteSale(Long id) {
        salesRepository.deleteById(id);
    }
}
