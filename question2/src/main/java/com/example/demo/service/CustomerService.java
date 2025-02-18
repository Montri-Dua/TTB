package com.example.demo.service;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        return convertToDTO(customer);
    }
    
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        customer = customerRepository.save(customer);
        return convertToDTO(customer);
    }
    
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        
        updateCustomerFromDTO(existingCustomer, customerDTO);
        existingCustomer = customerRepository.save(existingCustomer);
        return convertToDTO(existingCustomer);
    }
    
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
    
    // Convert Entity to DTO
    private CustomerDTO convertToDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setFirstname(customer.getFirstname());
        customerDTO.setLastname(customer.getLastname());
        customerDTO.setCustomerDate(customer.getCustomerDate());
        customerDTO.setIsVIP(customer.getIsVIP());
        customerDTO.setStatusCode(customer.getStatusCode());
        customerDTO.setCreatedOn(customer.getCreatedOn());
        //CreatedOn
        return customerDTO;
    }
    
    // Convert DTO to Entity
    private Customer convertToEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }
        
        Customer customer = new Customer();
        updateCustomerFromDTO(customer, customerDTO);
        return customer;
    }
    
    // Update entity from DTO
    private void updateCustomerFromDTO(Customer customer, CustomerDTO customerDTO) {
        customer.setFirstname(customerDTO.getFirstname());
        customer.setLastname(customerDTO.getLastname());
        customer.setCustomerDate(customerDTO.getCustomerDate());
        customer.setIsVIP(customerDTO.getIsVIP());
        customer.setStatusCode(customerDTO.getStatusCode());
        customer.setCreatedOn(customerDTO.getCreatedOn());
    }
}