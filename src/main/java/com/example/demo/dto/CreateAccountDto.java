package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountDto {
    private String nationalId;
    private String thName;
    private String engName;

}
