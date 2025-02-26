package com.example.emergencyapp.emergencycall.repository;

import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmergencyCallRepository extends JpaRepository<EmergencyCall,Long>,
        JpaSpecificationExecutor<EmergencyCall> {
    @Query("SELECT c FROM EmergencyCall c LEFT JOIN EmergencyResource r ON r.emergencyCall = c" +
            " WHERE r IS NULL ORDER BY c.severityType DESC, c.time ASC ")
    List<EmergencyCall> findAllOrderBySeverityTypeDescTimeAsc(Pageable pageable);

}
