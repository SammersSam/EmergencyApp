package com.example.emergencyapp.model.dto;

import com.example.emergencyapp.model.Caller;
import com.example.emergencyapp.model.EmergencyType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmergencyCallDto {
    private Caller callerDetails;
    private String location;
    private EmergencyType emergencyType;
    private LocalDateTime time;
}
