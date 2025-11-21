package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDto {
    private String email;
    private String password;
    private String nationalId;
    private String thName;
    private String engName;
    private String pin;
}
