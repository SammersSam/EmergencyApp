package com.example.emergencyapp.emergencycall.model.dto;

import com.example.emergencyapp.emergencycall.model.Caller;
import com.example.emergencyapp.emergencycall.model.EmergencyType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmergencyCallDto {
    private CallerDto callerDetails;
    private String location;
    private EmergencyType emergencyType;
    private LocalDateTime time;
}
