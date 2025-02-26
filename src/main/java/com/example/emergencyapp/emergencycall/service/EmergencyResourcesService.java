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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class EmergencyResourcesService {

    private final EmergencyResourcesRepository repo;
    private final EmergencyCallRepository callRepo;
    private final DataProcessorProvider provider;


    public EmergencyResource save(EmergencyResource resource) {
        return repo.save(resource);
    }

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
