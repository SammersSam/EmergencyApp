package com.example.emergencyapp.emergencycall.service;

import com.example.emergencyapp.emergencycall.exceptions.NotAvailableResourcesExceptions;
import com.example.emergencyapp.emergencycall.repository.EmergencyCallRepository;
import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import com.example.emergencyapp.emergencycall.model.EmergencyResource;
import com.example.emergencyapp.emergencycall.repository.EmergencyResourcesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class EmergencyResourcesService {

    private final EmergencyCallRepository callRepo;
    private final DataProcessorProvider provider;

    /**
     * Retrieves a limited number of high-severity EmergencyCalls,
     * assigns a resource to each call, and returns the list of assigned resources.
     *
     * @param size the maximum number of calls to process
     * @return a list of EmergencyResources assigned to the calls
     * @throws NotAvailableResourcesExceptions if no resources are available
     */
    public List<EmergencyResource> getAssignedResources(int size) {
        List<EmergencyCall> calls = callRepo
                .findAllOrderBySeverityTypeDescTimeAsc(Pageable.ofSize(size));

        List<EmergencyResource> resourceList = new ArrayList<>();
        for (EmergencyCall call : calls) {
            EmergencyResource emergencyResource = provider.assignResourceToCall(call);
            if (emergencyResource != null) {
                resourceList.add(emergencyResource);
            }
        }
        if (resourceList.isEmpty())
            throw new NotAvailableResourcesExceptions("Lack of available resources!");

        return resourceList;
    }
}
