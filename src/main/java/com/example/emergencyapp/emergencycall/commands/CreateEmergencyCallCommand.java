package com.example.emergencyapp.emergencycall.commands;

import com.example.emergencyapp.emergencycall.model.EmergencyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateEmergencyCallCommand {

    private Long callerId;
    @NotBlank
    private String location;
    @NotNull
    private EmergencyType emergencyType;
    @NotNull
    private LocalDateTime time;
}
