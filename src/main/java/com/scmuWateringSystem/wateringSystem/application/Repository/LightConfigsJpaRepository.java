package com.scmuWateringSystem.wateringSystem.application.Repository;

import com.scmuWateringSystem.wateringSystem.application.Models.LightConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightConfigsJpaRepository extends JpaRepository<LightConfig,String> {
}
