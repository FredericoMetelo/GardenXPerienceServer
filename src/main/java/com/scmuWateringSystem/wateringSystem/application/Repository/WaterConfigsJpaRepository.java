package com.scmuWateringSystem.wateringSystem.application.Repository;

import com.scmuWateringSystem.wateringSystem.application.Models.WaterConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterConfigsJpaRepository extends JpaRepository<WaterConfig,String> {
}
