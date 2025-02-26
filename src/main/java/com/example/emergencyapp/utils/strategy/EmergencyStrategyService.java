package com.example.emergencyapp.utils.strategy;

import com.example.emergencyapp.emergencycall.exceptions.IncorrectStrategyException;
import com.example.emergencyapp.emergencycall.model.EmergencyType;
import com.example.emergencyapp.emergencycall.model.ResourcesType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmergencyStrategyService {

    private final List<IEmergencyResourcesStrategy> strategies;
    /**
     * Maps the given emergency type to the appropriate emergency resources strategy
     * by searching through the list. If no matching strategy
     * is found, an IncorrectStrategyException is thrown.
     *
     * @param type the emergency type (e.g., MEDICAL, FIRE, POLICE)
     * @return  ResourcesType that matches the specified type
     * @throws IncorrectStrategyException if no matching strategy is found
     */
    public ResourcesType mapEmergencyTypeToResources(EmergencyType type){
       return  strategies.stream()
                .filter(s -> s.sup(type))
                .findFirst()
                .map(IEmergencyResourcesStrategy::getResourcesType)
                .orElseThrow(() -> new IncorrectStrategyException("Wrong strategy!"));
    }

}
