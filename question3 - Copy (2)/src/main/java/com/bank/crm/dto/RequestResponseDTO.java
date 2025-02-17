package com.bank.crm.dto;


import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestResponseDTO {
    private String requestId;
    private String status;
    private String message;
    private LocalDateTime timestamp;
}