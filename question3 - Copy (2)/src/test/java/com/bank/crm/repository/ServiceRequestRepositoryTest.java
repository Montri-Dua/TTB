package com.bank.crm.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bank.crm.entity.ServiceRequest;
import com.bank.crm.enums.RequestStatus;
import com.bank.crm.enums.RequestType;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ServiceRequestRepositoryTest {

    @Autowired
    private ServiceRequestRepository requestRepository;

    @Test
    public void saveRequest_ShouldReturnSavedRequest() {
        // Prepare test data
        ServiceRequest request = new ServiceRequest();
        request.setCustomerId("CUST123");
        request.setType(RequestType.PAYMENT_ISSUE);
        request.setStatus(RequestStatus.NEW);

        // Save request
        ServiceRequest savedRequest = requestRepository.save(request);

        // Verify
        assertNotNull(savedRequest.getId());
        assertEquals("CUST123", savedRequest.getCustomerId());
        assertEquals(RequestType.PAYMENT_ISSUE, savedRequest.getType());
    }

    @Test
    public void findByCustomerId_ShouldReturnRequests() {
        // Prepare test data
        ServiceRequest request1 = new ServiceRequest();
        request1.setCustomerId("CUST123");
        request1.setStatus(RequestStatus.NEW);
        requestRepository.save(request1);

        ServiceRequest request2 = new ServiceRequest();
        request2.setCustomerId("CUST123");
        request2.setStatus(RequestStatus.IN_PROGRESS);
        requestRepository.save(request2);

        // Find requests
        List<ServiceRequest> requests = requestRepository
            .findByCustomerId("CUST123");

        // Verify
        assertEquals(2, requests.size());
        assertTrue(requests.stream()
            .allMatch(r -> r.getCustomerId().equals("CUST123")));
    }
}