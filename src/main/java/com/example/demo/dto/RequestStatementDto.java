package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestStatementDto {
    private Long accountId;
    private String pin;
    private Integer month;
}
