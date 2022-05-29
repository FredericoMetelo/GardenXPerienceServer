package com.scmuWateringSystem.wateringSystem.application.Repository;

import com.scmuWateringSystem.wateringSystem.application.Models.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricsJPARepository extends JpaRepository<Metric,String> {
}
