package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferDto {
    private String fromAccountNumber;
    private String toAccountNumber;
    private Double amount;
    private String pin;
}
