package com.bank.crm;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.kafka.common.errors.ResourceNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bank.crm.dto.DepartmentAssignmentDTO;
import com.bank.crm.dto.ServiceRequestDTO;
import com.bank.crm.dto.StatusUpdateDTO;
import com.bank.crm.entity.ServiceRequest;
import com.bank.crm.enums.RequestPriority;
import com.bank.crm.enums.RequestStatus;
import com.bank.crm.enums.RequestType;
import com.bank.crm.event.BaseEvent;
import com.bank.crm.repository.ServiceRequestRepository;
import com.bank.crm.service.ServiceRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class Question3ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceRequestService requestService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void testCreateRequest() throws Exception {
        // Prepare test data
        ServiceRequestDTO requestDTO = new ServiceRequestDTO();
        requestDTO.setCustomerId("CUST123");
        requestDTO.setType(RequestType.PAYMENT_ISSUE);
        requestDTO.setDescription("Test request");
        requestDTO.setPriority(RequestPriority.HIGH);

        when(requestService.createRequest(any(ServiceRequestDTO.class)))
            .thenReturn(requestDTO);

        // Perform test
        mockMvc.perform(post("/api/v1/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("CUST123"))
                .andExpect(jsonPath("$.type").value("PAYMENT_ISSUE"));
    }
    
    @Test
    void testGetRequest() throws Exception {
        // Prepare test data
        ServiceRequestDTO requestDTO = new ServiceRequestDTO();
        requestDTO.setId(1L);
        requestDTO.setCustomerId("CUST123");
        requestDTO.setStatus(RequestStatus.NEW);

        when(requestService.getRequest(1L))
            .thenReturn(requestDTO);

        // Perform test
        mockMvc.perform(get("/api/v1/requests/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerId").value("CUST123"))
                .andExpect(jsonPath("$.status").value("NEW"));
    }
    
    @Test
    void testAssignRequestToDepartment() throws Exception {
        // Prepare test data
        DepartmentAssignmentDTO assignmentDTO = new DepartmentAssignmentDTO();
        assignmentDTO.setDepartmentId("DEPT001");
        assignmentDTO.setAssignedBy("AGENT007");
        assignmentDTO.setPriority("HIGH");

        ServiceRequestDTO responseDTO = new ServiceRequestDTO();
        responseDTO.setId(2L);
        responseDTO.setAssignedDepartment("DEPT001");
        responseDTO.setStatus(RequestStatus.IN_PROGRESS);

        when(requestService.assignRequest(any(Long.class), any(DepartmentAssignmentDTO.class)))
            .thenReturn(responseDTO);

        mockMvc.perform(put("http://localhost:8089/api/v1/requests/2/assign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assignmentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assignedDepartment").value("DEPT001"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }
    
    @Test
    void testAssignRequestToDepartment_NotFound() throws Exception {
        DepartmentAssignmentDTO assignmentDTO = new DepartmentAssignmentDTO();
        assignmentDTO.setDepartmentId("DEPT001");

        when(requestService.assignRequest(any(Long.class), any(DepartmentAssignmentDTO.class)))
            .thenThrow(new ResourceNotFoundException("Request not found"));

        mockMvc.perform(put("/api/v1/requests/999/assign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assignmentDTO)))
                .andExpect(status().isNotFound());
    }
 // 3. Status Update Tests
    @Test
    void testUpdateStatus() throws Exception {
        // Prepare test data
        StatusUpdateDTO statusUpdate = new StatusUpdateDTO();
        statusUpdate.setNewStatus(RequestStatus.IN_PROGRESS);
        statusUpdate.setComment("Processing request");
        statusUpdate.setUpdatedBy("AGENT007");

        ServiceRequestDTO responseDTO = new ServiceRequestDTO();
        responseDTO.setId(1L);
        responseDTO.setStatus(RequestStatus.IN_PROGRESS);

        when(requestService.updateStatus(any(Long.class), any(StatusUpdateDTO.class)))
            .thenReturn(responseDTO);

        // Perform test
        mockMvc.perform(put("/api/v1/requests/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }
    
    @Test
    void testUpdateStatus_InvalidStatus() throws Exception {
        StatusUpdateDTO statusUpdate = new StatusUpdateDTO();
        statusUpdate.setNewStatus(null);
        statusUpdate.setUpdatedBy("AGENT007");

        mockMvc.perform(put("/api/v1/requests/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusUpdate)))
                .andExpect(status().isBadRequest());
    }
}
