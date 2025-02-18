package com.example.demo.dto;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CustomerDTO {
    private Long customerId;
    
    @NotBlank(message = "Firstname is required")
    @Size(max = 100, message = "Firstname must not exceed 100 characters")
    private String firstname;
    
    public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public LocalDate getCustomerDate() {
		return customerDate;
	}

	public void setCustomerDate(LocalDate customerDate) {
		this.customerDate = customerDate;
	}

	public Boolean getIsVIP() {
		return isVIP;
	}

	public void setIsVIP(Boolean isVIP) {
		this.isVIP = isVIP;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@NotBlank(message = "Lastname is required")
    @Size(max = 100, message = "Lastname must not exceed 100 characters")
    private String lastname;
    
    
    private Boolean isVIP;
    
    public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Size(max = 10, message = "Status code must not exceed 10 characters")
    private String statusCode;
    
    @Column(name = "createdOn")
    private LocalDateTime createdOn;
    
    @Column(name = "modifiedOn")
    private LocalDateTime modifiedOn;
    
    @Column(name = "customerDate")
    private LocalDate customerDate;
    
}