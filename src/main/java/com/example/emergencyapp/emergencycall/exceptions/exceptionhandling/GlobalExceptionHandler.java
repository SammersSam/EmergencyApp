package com.example.emergencyapp.emergencycall.exceptions.exceptionhandling;

import com.example.emergencyapp.emergencycall.exceptions.IncorrectStrategyException;
import com.example.emergencyapp.emergencycall.exceptions.NotAvailableResourcesExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotAvailableResourcesExceptions.class,IncorrectStrategyException.class})
    public ResponseEntity<ExceptionResponseDto> getNotAvailableResourcesExceptions(NotAvailableResourcesExceptions e){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                List.of(e.getMessage()),"NOT_FOUND", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDto);
    }
}
