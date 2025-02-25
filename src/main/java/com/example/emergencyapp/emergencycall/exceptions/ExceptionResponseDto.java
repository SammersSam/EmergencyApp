package com.example.emergencyapp.emergencycall.exceptions;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@AllArgsConstructor
public class ExceptionResponseDto {

    private List<String> message;
    private String statusError;
    private LocalDateTime localDateTime;
}
