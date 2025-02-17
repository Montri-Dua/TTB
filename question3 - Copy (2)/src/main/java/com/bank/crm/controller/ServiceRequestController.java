package com.bank.crm.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bank.crm.dto.DepartmentAssignmentDTO;
import com.bank.crm.dto.ServiceRequestDTO;
import com.bank.crm.dto.StatusUpdateDTO;
import com.bank.crm.enums.RequestStatus;
import com.bank.crm.enums.RequestType;
import com.bank.crm.service.ServiceRequestService;
import com.bank.crm.enums.RequestPriority;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequestMapping("/api/v1/requests")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService requestService;

    @PostMapping
    public ResponseEntity<ServiceRequestDTO> createRequest(@RequestBody ServiceRequestDTO request) {
        return ResponseEntity.ok(requestService.createRequest(request));
    }

   // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{requestId}")
    public ResponseEntity<ServiceRequestDTO> getRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(requestService.getRequest(requestId));
    }

    
    @PutMapping("/{requestId}/status")
    public ResponseEntity<ServiceRequestDTO> updateStatus(
            @PathVariable Long requestId,
            @RequestBody StatusUpdateDTO statusUpdate) {
        return ResponseEntity.ok(requestService.updateStatus(requestId, statusUpdate));
    }

    @GetMapping
    public ResponseEntity<List<ServiceRequestDTO>> getRequests(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) RequestStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(requestService.getRequests(customerId, status, page, size));
    }
    
    @PutMapping("/{requestId}/assign")
    public ResponseEntity<ServiceRequestDTO> assignRequest(
            @PathVariable Long requestId,
            @RequestBody DepartmentAssignmentDTO assignment) {
        return ResponseEntity.ok(requestService.assignRequest(requestId, assignment));
    }
}