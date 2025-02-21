package com.example.emergencyapp.commands;

import com.example.emergencyapp.model.Caller;
import com.example.emergencyapp.model.EmergencyType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateEmergencyCallCommand {

    private Long callerId;
    private String location;
    private EmergencyType emergencyType;
    private LocalDateTime time;
}
