package com.example.emergencyapp.emergencycall.service;

import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import com.example.emergencyapp.emergencycall.model.EmergencyResource;
import com.example.emergencyapp.emergencycall.model.ResourcesStatusType;
import com.example.emergencyapp.emergencycall.model.ResourcesType;
import com.example.emergencyapp.emergencycall.repository.EmergencyCallRepository;
import com.example.emergencyapp.emergencycall.repository.EmergencyResourcesRepository;
import com.example.emergencyapp.utils.EmergencyStrategyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataProcessorProvider {

    private static final Logger log = LoggerFactory.getLogger(EmergencyResourcesService.class);
    private final EmergencyResourcesRepository repo;
    private final EmergencyStrategyService strategyService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public EmergencyResource assignResourceToCall(EmergencyCall call) {
        List<EmergencyResource> resourcesList = getResourcesByStatus(call);
        if (resourcesList.isEmpty()) {
            log.warn("No resources of type {} for call ID {}", call.getEmergencyType(), call.getId());
            return null;
        }
        EmergencyResource closestResource = LocationProcessor
                .findClosestResource(call.getLocation(), resourcesList);
        closestResource.setResourcesStatusType(ResourcesStatusType.BUSY);
        closestResource.setEmergencyCall(call);
        repo.save(closestResource);
        return closestResource;
    }

    private List<EmergencyResource> getResourcesByStatus(EmergencyCall call) {
        ResourcesType resources = strategyService.mapEmergencyTypeToResources(call.getEmergencyType());
        return repo.findByResourcesStatusType(resources);

    }
}
