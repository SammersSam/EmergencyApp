package com.example.emergencyapp.utils.strategy;

import com.example.emergencyapp.emergencycall.model.EmergencyType;
import com.example.emergencyapp.emergencycall.model.ResourcesType;
import com.example.emergencyapp.utils.strategy.IEmergencyResourcesStrategy;
import org.springframework.stereotype.Service;

@Service
public class PoliceEmergencyStrategy implements IEmergencyResourcesStrategy {

    @Override
    public boolean sup(EmergencyType type) {
        return type == EmergencyType.POLICE;
    }

    @Override
    public ResourcesType getResourcesType() {
        return ResourcesType.POLICE_CAR;
    }
}
