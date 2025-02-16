package com.bank.crm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.crm.dto.DepartmentAssignmentDTO;
import com.bank.crm.dto.ServiceRequestDTO;
import com.bank.crm.dto.StatusUpdateDTO;
import com.bank.crm.entity.ServiceRequest;
import com.bank.crm.entity.StatusHistory;
import com.bank.crm.enums.RequestEventType;
import com.bank.crm.enums.RequestPriority;
import com.bank.crm.enums.RequestStatus;
import com.bank.crm.event.AssignmentEvent;
import com.bank.crm.event.BaseEvent;
import com.bank.crm.event.RequestEvent;
import com.bank.crm.event.StatusUpdateEvent;
import com.bank.crm.mapper.ServiceRequestMapper;
import com.bank.crm.repository.ServiceRequestRepository;
import com.bank.crm.repository.StatusHistoryRepository;
import org.springframework.kafka.core.KafkaTemplate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;


@Service
@Transactional
public class ServiceRequestServiceImpl implements ServiceRequestService {
    
//    @Autowired
//    private ServiceRequestRepository requestRepository;

    @Autowired
    private StatusHistoryRepository historyRepository;

//    @Autowired
//    private KafkaTemplate<String, RequestEvent> kafkaTemplate;


    @Autowired
    private final ServiceRequestRepository requestRepository;
 
    @Autowired(required=true)
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;
    
    @Autowired
    public ServiceRequestServiceImpl(
            ServiceRequestRepository requestRepository,
            KafkaTemplate<String, BaseEvent> kafkaTemplate) {
        this.requestRepository = requestRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    
    @Override
    public ServiceRequestDTO createRequest(ServiceRequestDTO dto) {
    	try {
    		
	       ServiceRequest request = new ServiceRequest();
           request.setCustomerId(dto.getCustomerId());
           request.setType(dto.getType());
           request.setDescription(dto.getDescription());
           request.setStatus(RequestStatus.NEW);
           request.setPriority(dto.getPriority());
           request.setCreatedAt(LocalDateTime.now());
           request.setMetadata(dto.getMetadata());

           ServiceRequest savedRequest = requestRepository.save(request);
           
           // Create and publish event
           RequestEvent event = new RequestEvent();
           event.setRequestId(savedRequest.getId());
           event.setCustomerId(savedRequest.getCustomerId());
           event.setType(savedRequest.getType());
           event.setStatus(savedRequest.getStatus());

           kafkaTemplate.send("request-events", event);

           return mapToDTO(savedRequest);
	        
    	} catch (Exception e) {
    			throw new RuntimeException("Error creating request: " + e.getMessage());
    	}
    }

	@Override
    public ServiceRequestDTO updateStatus(Long requestId, StatusUpdateDTO dto) {
        ServiceRequest request = requestRepository.findById(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

      //  System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx1 :"+ requestId);
        // Create status history
        StatusHistory history = new StatusHistory();
        history.setRequestId(requestId);
        history.setOldStatus(request.getStatus());
        history.setNewStatus(dto.getNewStatus());
        history.setComment(dto.getComment());
        history.setUpdatedBy(dto.getUpdatedBy());
        history.setUpdatedAt(LocalDateTime.now());

        // Update request
        request.setStatus(dto.getNewStatus());
        request.setUpdatedAt(LocalDateTime.now());

        // Save changes
        historyRepository.save(history);
        ServiceRequest updatedRequest = requestRepository.save(request);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx2 : "+ requestId  +" : "+ dto.getNewStatus().toString());
//        StatusUpdateEvent event = new StatusUpdateEvent(
//                requestId,
//                dto.getNewStatus(),
//                dto.getComment()
//            );

        // Create and publish event
        StatusUpdateEvent event = new StatusUpdateEvent();
        event.setRequestId(requestId);
        event.setOldStatus(request.getStatus());
        event.setNewStatus(dto.getNewStatus());
        event.setComment(dto.getComment());
        event.setUpdatedBy(dto.getUpdatedBy());

        kafkaTemplate.send("status-events", event);

        return mapToDTO(updatedRequest);
    }
	
	 @Override
	    public ServiceRequestDTO assignRequest(Long requestId, DepartmentAssignmentDTO assignment) {
	        ServiceRequest request = requestRepository.findById(requestId)
	                .orElseThrow(() -> new ResourceNotFoundException("Request not found with id: " + requestId));

	        // Update request
	        request.setAssignedDepartment(assignment.getDepartmentId());
	        request.setPriority(RequestPriority.valueOf(assignment.getPriority()));
	        request.setStatus(RequestStatus.IN_PROGRESS);
	        request.setUpdatedAt(LocalDateTime.now());

	        // Save updated request
	        ServiceRequest updatedRequest = requestRepository.save(request);

	        // Create and publish assignment event
	        AssignmentEvent event = new AssignmentEvent();
	        event.setRequestId(requestId);
	        event.setDepartmentId(assignment.getDepartmentId());
	        event.setAssignedBy(assignment.getAssignedBy());
	        event.setPriority(assignment.getPriority());

	        kafkaTemplate.send("assignment-events", event);

	        return mapToDTO(updatedRequest);
	    }
	
	
	// Add the mapToDTO method
    private ServiceRequestDTO mapToDTO(ServiceRequest entity) {
        ServiceRequestDTO dto = new ServiceRequestDTO();
        dto.setId(entity.getId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setType(entity.getType());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setPriority(entity.getPriority());
        dto.setAssignedDepartment(entity.getAssignedDepartment());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setMetadata(entity.getMetadata());
        return dto;
    }
 // Optional: Add method to map DTO to Entity
    private ServiceRequest mapToEntity(ServiceRequestDTO dto) {
        ServiceRequest entity = new ServiceRequest();
        entity.setCustomerId(dto.getCustomerId());
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());
        entity.setPriority(dto.getPriority());
        entity.setAssignedDepartment(dto.getAssignedDepartment());
        entity.setMetadata(dto.getMetadata());
        return entity;
    }

    @Override
    public ServiceRequestDTO getRequest(Long requestId) {
        ServiceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        return mapToDTO(request);
    }

	@Override
	public List<ServiceRequestDTO> getRequests(String customerId, RequestStatus status, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

}