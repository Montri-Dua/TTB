package com.bank.crm.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bank.crm.dto.ServiceRequestDTO;
import com.bank.crm.enums.RequestPriority;
import com.bank.crm.enums.RequestType;
import com.bank.crm.service.ServiceRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;

class ServiceRequestControllerTest {

   @Autowired
    private MockMvc mockMvc;


    private ServiceRequestService requestService;

    @Autowired
    private ObjectMapper objectMapper;
	    
	@Test
	void test() {
		fail("Not yet implemented");
	}
	@Test
    public void createRequest_ShouldReturnCreatedRequest() throws Exception {
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
    public void getRequest_ShouldReturnRequest() throws Exception {
        ServiceRequestDTO requestDTO = new ServiceRequestDTO();
        requestDTO.setId(1L);
        requestDTO.setCustomerId("CUST123");

        when(requestService.getRequest(1L)).thenReturn(requestDTO);

        mockMvc.perform(get("/api/v1/requests/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerId").value("CUST123"));
    }

    @Test
    public void getRequest_NotFound_ShouldReturn404() throws Exception {
        when(requestService.getRequest(1L))
            .thenThrow(new ResourceNotFoundException("Request not found"));

        mockMvc.perform(get("/api/v1/requests/1"))
                .andExpect(status().isNotFound());
    }
}

