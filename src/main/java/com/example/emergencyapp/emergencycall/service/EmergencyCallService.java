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

    public EmergencyCall save(EmergencyCall emergencyCall){
        return repo.save(emergencyCall);
    }

    public Page<EmergencyCall> getByCriteria(Map<String,String> criteria, Pageable pageable){
        Specification<EmergencyCall> emergencyCallSpecification = DynamicSpecificationEmergencyCall.byFilters(criteria);
        return  repo.findBySpecificCriteria(emergencyCallSpecification,pageable);
    }
}
