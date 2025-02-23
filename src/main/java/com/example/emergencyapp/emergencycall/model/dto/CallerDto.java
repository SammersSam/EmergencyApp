package com.example.emergencyapp.emergencycall.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallerDto {
    private String name;
    private String lastname;
    private String phoneNumber;
}
