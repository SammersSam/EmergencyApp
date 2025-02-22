package com.example.emergencyapp.emergencycall.repository;

import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmergencyCallRepository extends JpaRepository<EmergencyCall,Long>, JpaSpecificationExecutor<EmergencyCall> {

     Page<EmergencyCall> findBySpecificCriteria(Specification<EmergencyCall> spec, Pageable pageable);
}
