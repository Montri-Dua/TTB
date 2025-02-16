package com.bank.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.crm.entity.ServiceRequest;
import com.bank.crm.enums.RequestStatus;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByCustomerId(String customerId);
    List<ServiceRequest> findByStatus(RequestStatus status);
    List<ServiceRequest> findByAssignedDepartment(String department);
}