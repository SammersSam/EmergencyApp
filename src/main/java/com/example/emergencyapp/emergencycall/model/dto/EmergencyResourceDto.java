package com.example.emergencyapp.emergencycall.model.dto;

import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import com.example.emergencyapp.emergencycall.model.ResourcesStatusType;
import com.example.emergencyapp.emergencycall.model.ResourcesType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmergencyResourceDto {

    private ResourcesType type;
    private ResourcesStatusType resourcesStatusType;
    private String location;
    private EmergencyCallDto emergencyCall;
}
