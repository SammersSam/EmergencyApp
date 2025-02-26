package com.example.emergencyapp.utils;

import com.example.emergencyapp.emergencycall.exceptions.IncorrectStrategyException;
import com.example.emergencyapp.emergencycall.model.EmergencyType;
import com.example.emergencyapp.emergencycall.model.ResourcesType;
import com.example.emergencyapp.utils.IEmergencyResourcesStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmergencyStrategyService {

    private final List<IEmergencyResourcesStrategy> strategies;

    public ResourcesType mapEmergencyTypeToResources(EmergencyType type){
       return  strategies.stream()
                .filter(s -> s.sup(type))
                .findFirst()
                .map(IEmergencyResourcesStrategy::getResourcesType)
                .orElseThrow(() -> new IncorrectStrategyException("Wrong strategy!"));
    }

}
