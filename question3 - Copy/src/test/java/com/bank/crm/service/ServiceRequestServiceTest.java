package com.bank.crm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import java.util.Optional;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import com.bank.crm.dto.ServiceRequestDTO;
import com.bank.crm.dto.StatusUpdateDTO;
import com.bank.crm.entity.ServiceRequest;
import com.bank.crm.enums.RequestPriority;
import com.bank.crm.enums.RequestStatus;
import com.bank.crm.enums.RequestType;
import com.bank.crm.event.BaseEvent;
import com.bank.crm.event.RequestEvent;
import com.bank.crm.event.StatusUpdateEvent;
import com.bank.crm.repository.ServiceRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
public class ServiceRequestServiceTest {

    @Mock
    private ServiceRequestRepository requestRepository;

    @Mock
    private KafkaTemplate<String, BaseEvent> kafkaTemplate;

    @InjectMocks
    private ServiceRequestServiceImpl requestService;

    @Test
    public void createRequest_ShouldSaveAndReturnRequest() {
        // Prepare test data
        ServiceRequestDTO requestDTO = new ServiceRequestDTO();
        requestDTO.setCustomerId("CUST123");
        requestDTO.setType(RequestType.PAYMENT_ISSUE);
        requestDTO.setDescription("Test request");
        requestDTO.setPriority(RequestPriority.HIGH);

        ServiceRequest savedRequest = new ServiceRequest();
        savedRequest.setId(1L);
        savedRequest.setCustomerId("CUST123");
        savedRequest.setStatus(RequestStatus.NEW);

        when(requestRepository.save(any(ServiceRequest.class)))
            .thenReturn(savedRequest);

        // Perform test
        ServiceRequestDTO result = requestService.createRequest(requestDTO);

        // Verify
        assertNotNull(result);
        assertEquals("CUST123", result.getCustomerId());
        assertEquals(RequestStatus.NEW, result.getStatus());
        verify(requestRepository).save(any(ServiceRequest.class));
        verify(kafkaTemplate).send(eq("request-events"), any(RequestEvent.class));
    }

    @Test
    public void getRequest_ShouldReturnRequest() {
        ServiceRequest request = new ServiceRequest();
        request.setId(1L);
        request.setCustomerId("CUST123");

        when(requestRepository.findById(1L))
            .thenReturn(Optional.of(request));

        ServiceRequestDTO result = requestService.getRequest(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("CUST123", result.getCustomerId());
    }

    @Test
    public void getRequest_NotFound_ShouldThrowException() {
        when(requestRepository.findById(1L))
            .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            requestService.getRequest(1L);
        });
    }

    @Test
    public void updateStatus_ShouldUpdateAndReturnRequest() {
        // Prepare test data
        ServiceRequest request = new ServiceRequest();
        request.setId(1L);
        request.setStatus(RequestStatus.NEW);

        StatusUpdateDTO statusUpdate = new StatusUpdateDTO();
        statusUpdate.setNewStatus(RequestStatus.IN_PROGRESS);
        statusUpdate.setComment("Processing");
        statusUpdate.setUpdatedBy("AGENT007");


        when(requestRepository.findById(1L))
            .thenReturn(Optional.of(request));
        when(requestRepository.save(any(ServiceRequest.class)))
            .thenReturn(request);

        // Perform test
        ServiceRequestDTO result = requestService.updateStatus(1L, statusUpdate);

        // Verify
        assertNotNull(result);
        assertEquals(RequestStatus.IN_PROGRESS, result.getStatus());
        verify(kafkaTemplate).send(eq("status-events"), any(StatusUpdateEvent.class));
    }
}