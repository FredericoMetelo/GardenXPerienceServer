package com.scmuWateringSystem.wateringSystem.application.arguments;

import lombok.Data;

@Data
public class WaterGardenParameters {
    private String startingDateTime;
    private String waterQuantity;
    private int wateringDuration;
}
