package com.example.demo.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDto {
    private String status;
    private String message;
    private Object result;
    private List<?> results;
}
