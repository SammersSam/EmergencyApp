package com.example.emergencyapp.utils.strategy;

import com.example.emergencyapp.emergencycall.model.EmergencyType;
import com.example.emergencyapp.emergencycall.model.ResourcesType;

public interface IEmergencyResourcesStrategy {
    boolean sup(EmergencyType type);
    ResourcesType getResourcesType();
}
