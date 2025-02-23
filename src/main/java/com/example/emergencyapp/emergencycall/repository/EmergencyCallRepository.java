package com.example.emergencyapp.emergencycall.repository;

import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmergencyCallRepository extends JpaRepository<EmergencyCall,Long>,
        JpaSpecificationExecutor<EmergencyCall> {

    List<EmergencyCall> findAllByDispatchedFalseOrderBySeverityTypeDescTimeAsc(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ec FROM EmergencyCall ec WHERE ec.id = :id")
    EmergencyCall findByIdToUpdate(Long id);

}
