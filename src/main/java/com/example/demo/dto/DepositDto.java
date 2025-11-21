package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositDto {
    private String accountNumber;
    private Double amount;
    private String terminalId;
}
