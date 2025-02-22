package com.example.emergencyapp.emergencycall.service;

import com.example.emergencyapp.emergencycall.model.ResourcesStatusType;
import com.example.emergencyapp.emergencycall.exceptions.NotAvailableResourcesExceptions;
import com.example.emergencyapp.utils.EmergencyStrategyService;
import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import com.example.emergencyapp.emergencycall.model.EmergencyResource;
import com.example.emergencyapp.emergencycall.model.ResourcesType;
import com.example.emergencyapp.emergencycall.repository.EmergencyResourcesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmergencyResourcesService {

    private final EmergencyResourcesRepository repo;
    private final EmergencyStrategyService strategyService;


    public EmergencyResource save(EmergencyResource resource){
        return repo.save(resource);
    }


    public EmergencyResource dispatchResource(EmergencyCall call) {
        List<EmergencyResource> resourcesList = getResourcesByCriteria(call);
        EmergencyResource closestResource = LocationProcessor.findClosestResource(call.getLocation(), resourcesList);
        closestResource.setResourcesStatusType(ResourcesStatusType.BUSY);
        return save(closestResource);
    }



    private List<EmergencyResource> getResourcesByCriteria(EmergencyCall call){
        ResourcesType resources = strategyService.mapEmergencyTypeToResources(call.getEmergencyType());

        return repo.findByResourcesStatusTypeAndLocation(resources, call.getLocation())
                .orElseThrow(() -> new NotAvailableResourcesExceptions("Lack of available resources!"));
    }




}
