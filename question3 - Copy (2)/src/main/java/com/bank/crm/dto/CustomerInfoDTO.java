package com.bank.crm.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoDTO {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String accountNumber;
}