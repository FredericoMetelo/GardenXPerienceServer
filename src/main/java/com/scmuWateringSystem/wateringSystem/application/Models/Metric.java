package com.scmuWateringSystem.wateringSystem.application.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@Table(name = "metric")
public class Metric {
    private String date;
    private String value;
    private String magnitudeType;
    @Id
    private String id;

    public Metric(){}

}
