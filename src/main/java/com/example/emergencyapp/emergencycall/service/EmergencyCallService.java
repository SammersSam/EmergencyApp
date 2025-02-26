package com.example.emergencyapp.emergencycall.service;

import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import com.example.emergencyapp.emergencycall.repository.EmergencyCallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmergencyCallService {

    private final EmergencyCallRepository repo;

    /**
     * Saves a new EmergencyCall in the repository.
     *
     * @param emergencyCall the emergency call object to save
     * @return the saved EmergencyCall with an assigned ID
     */
    public EmergencyCall save(EmergencyCall emergencyCall){
        return repo.save(emergencyCall);
    }

    /**
     * Retrieves emergency calls based on given criteria:
     * date range (from, to) and an additional parameter (criteria).
     *
     * @param criteria additional filter (e.g., event type)
     * @param pageable pagination and sorting information
     * @return a Page of EmergencyCalls matching the criteria
     */
    public Page<EmergencyCall> getByCriteria(Map<String,String> criteria, Pageable pageable){
        Specification<EmergencyCall> emergencyCallSpecification = DynamicSpecificationEmergencyCall.byFilters(criteria);
        return  repo.findAll(emergencyCallSpecification,pageable);
    }
}
