package com.example.emergencyapp.emergencycall.service;

import com.example.emergencyapp.emergencycall.model.ResourcesStatusType;
import com.example.emergencyapp.emergencycall.exceptions.NotAvailableResourcesExceptions;
import com.example.emergencyapp.emergencycall.repository.EmergencyCallRepository;
import com.example.emergencyapp.utils.EmergencyStrategyService;
import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import com.example.emergencyapp.emergencycall.model.EmergencyResource;
import com.example.emergencyapp.emergencycall.model.ResourcesType;
import com.example.emergencyapp.emergencycall.repository.EmergencyResourcesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmergencyResourcesService {

    private final EmergencyResourcesRepository repo;
    private final EmergencyStrategyService strategyService;
    private final EmergencyCallRepository callRepo;


    public EmergencyResource save(EmergencyResource resource) {
        return repo.save(resource);
    }



    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processCall(Long id) {
        EmergencyCall call = callRepo.findByIdToUpdate(id);
        assignResourceToCall(call);
        call.setDispatched(true);
        callRepo.save(call);
    }

    private EmergencyResource assignResourceToCall(EmergencyCall call) {
        List<EmergencyResource> resourcesList = getResourcesByStatus(call);
//        Optional<EmergencyResource> bestResourceOpt = resourcesList.stream()
//                .min(Comparator.comparingDouble(resource -> calculateDistance(call.getLocation(), resource.getLocation())));
        EmergencyResource closestResource = LocationProcessor.findClosestResource(call.getLocation(), resourcesList);
        closestResource.setResourcesStatusType(ResourcesStatusType.BUSY);
        repo.save(closestResource);
        return closestResource;
    }
    //todo przypisywanie resouurces to call relacje

    private List<EmergencyResource> getResourcesByStatus(EmergencyCall call) {
        ResourcesType resources = strategyService.mapEmergencyTypeToResources(call.getEmergencyType());

        return repo.findByResourcesStatusTypeAndLocation(resources, call.getLocation())
                .orElseThrow(() -> new NotAvailableResourcesExceptions("Lack of available resources!"));
    }


}
