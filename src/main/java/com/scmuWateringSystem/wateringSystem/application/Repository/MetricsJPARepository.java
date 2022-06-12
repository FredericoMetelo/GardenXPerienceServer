package com.scmuWateringSystem.wateringSystem.application.Repository;

import com.scmuWateringSystem.wateringSystem.application.Models.Metric;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetricsJPARepository extends JpaRepository<Metric,String> {

    Page<Metric> findAllByMagnitudeType(String magnitudeType, Pageable pagination);

}
