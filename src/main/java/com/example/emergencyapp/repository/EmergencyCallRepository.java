package com.example.emergencyapp.repository;

import com.example.emergencyapp.model.EmergencyCall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyCallRepository extends JpaRepository<EmergencyCall,Long> {
}
