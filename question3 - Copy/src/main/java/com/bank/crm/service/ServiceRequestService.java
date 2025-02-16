package com.bank.crm.service;

import com.bank.crm.dto.DepartmentAssignmentDTO;
import com.bank.crm.dto.ServiceRequestDTO;
import com.bank.crm.dto.StatusUpdateDTO;
import com.bank.crm.enums.RequestStatus;
import java.util.List;

public interface ServiceRequestService {
    ServiceRequestDTO createRequest(ServiceRequestDTO request);
    ServiceRequestDTO getRequest(Long requestId);
    ServiceRequestDTO updateStatus(Long requestId, StatusUpdateDTO statusUpdate);
    List<ServiceRequestDTO> getRequests(String customerId, RequestStatus status, int page, int size);
    //List<ServiceRequestDTO> getAllRequests();
	ServiceRequestDTO assignRequest(Long requestId, DepartmentAssignmentDTO assignment);
}