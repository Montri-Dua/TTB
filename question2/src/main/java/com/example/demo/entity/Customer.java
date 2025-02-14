package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    
    @Column(name = "Firstname")
    private String firstname;
    
    @Column(name = "lastname")
    private String lastname;
    
    @Column(name = "customerDate")
    private LocalDateTime customerDate;
    
    @Column(name = "isVIP")
    private Boolean isVIP;
    
    @Column(name = "statusCode")
    private String statusCode;
    
    @Column(name = "createdOn")
    private LocalDateTime createdOn;
    
    @Column(name = "modifiedOn")
    private LocalDateTime modifiedOn;

    // Getters & Setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    
    public LocalDateTime getCustomerDate() { return customerDate; }
    public void setCustomerDate(LocalDateTime customerDate) { this.customerDate = customerDate; }
    
    public Boolean getIsVIP() { return isVIP; }
    public void setIsVIP(Boolean isVIP) { this.isVIP = isVIP; }
    
    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }
    
    public LocalDateTime getCreatedOn() { return createdOn; }
    public void setCreatedOn(LocalDateTime createdOn) { this.createdOn = createdOn; }
    
    public LocalDateTime getModifiedOn() { return modifiedOn; }
    public void setModifiedOn(LocalDateTime modifiedOn) { this.modifiedOn = modifiedOn; }
}
