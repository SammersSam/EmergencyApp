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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmergencyResourcesService {

    private final EmergencyResourcesRepository repo;
    private final EmergencyStrategyService strategyService;
    private final EmergencyCallRepository callRepo;
    private final ResourceSender sender;


    public EmergencyResource save(EmergencyResource resource) {
        return repo.save(resource);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processCall(Long id) {
        EmergencyCall call = callRepo.findByIdToUpdate(id);
        EmergencyResource resourceToDispatch = findBestResource(call);
        call.setDispatched(true);
        callRepo.save(call);
        sender.dispatchResources(resourceToDispatch);
    }

    private EmergencyResource findBestResource(EmergencyCall call) {
        List<EmergencyResource> resourcesList = getResourcesByStatus(call);
        EmergencyResource closestResource = LocationProcessor.findClosestResource(call.getLocation(), resourcesList)
                .orElseThrow(() -> new NotAvailableResourcesExceptions("Lack of available resources"));

        closestResource.setResourcesStatusType(ResourcesStatusType.BUSY);
        repo.save(closestResource);
        System.out.println("wysłany" + closestResource.getId());
        return closestResource;
    }
    //todo przypisywanie resouurces to call relacje

    private List<EmergencyResource> getResourcesByStatus(EmergencyCall call) {
        ResourcesType resources = strategyService.mapEmergencyTypeToResources(call.getEmergencyType());

        return repo.findByResourcesStatusType(resources)
                .orElseThrow(() -> new NotAvailableResourcesExceptions("Lack of available resources!"));
    }


}
