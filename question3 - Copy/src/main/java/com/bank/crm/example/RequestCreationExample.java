package com.bank.crm.example;

import com.bank.crm.dto.ServiceRequestDTO;
import com.bank.crm.dto.StatusUpdateDTO;
import com.bank.crm.enums.RequestStatus;
import com.bank.crm.enums.RequestType;
import com.bank.crm.enums.RequestPriority;

import java.util.Map;
import java.util.HashMap;
public class RequestCreationExample {
    public void createRequestExample() {
        // Create new request
        ServiceRequestDTO request = new ServiceRequestDTO();
        request.setCustomerId("CUST123");
        request.setType(RequestType.PAYMENT_ISSUE);
        request.setDescription("Payment failed for transaction");
        request.setStatus(RequestStatus.NEW);
        request.setPriority(RequestPriority.HIGH);
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("transactionId", "TRX123");
        metadata.put("amount", 1000.00);
        metadata.put("errorCode", "PAY_001");
        request.setMetadata(metadata);

        // Update status
        StatusUpdateDTO statusUpdate = new StatusUpdateDTO();
        statusUpdate.setNewStatus(RequestStatus.IN_PROGRESS);
        statusUpdate.setComment("Started processing payment issue");
        statusUpdate.setUpdatedBy("AGENT007");
        statusUpdate.setReasonCode("START_PROCESS");
    }
}