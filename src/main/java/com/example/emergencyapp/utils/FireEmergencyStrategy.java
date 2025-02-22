package com.example.emergencyapp.utils;

import com.example.emergencyapp.emergencycall.model.EmergencyType;
import com.example.emergencyapp.emergencycall.model.ResourcesType;
import com.example.emergencyapp.utils.IEmergencyResourcesStrategy;
import org.springframework.stereotype.Service;

@Service
public class FireEmergencyStrategy implements IEmergencyResourcesStrategy {
    @Override
    public boolean sup(EmergencyType type) {
        return type == EmergencyType.FIRE;
    }

    @Override
    public ResourcesType getResourcesType() {
        return ResourcesType.FIRE_TRUCK;
    }
}
