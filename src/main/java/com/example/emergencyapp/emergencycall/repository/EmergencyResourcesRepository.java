package com.example.emergencyapp.emergencycall.repository;

import com.example.emergencyapp.emergencycall.model.EmergencyResource;
import com.example.emergencyapp.emergencycall.model.ResourcesType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmergencyResourcesRepository extends JpaRepository<EmergencyResource,Long> {

    @Query("SELECT e FROM EmergencyResource e " +
            "WHERE e.resourcesStatusType = 'AVAILABLE' AND  e.type = :type")
    List<EmergencyResource> findByResourcesStatusType(ResourcesType type);
}
