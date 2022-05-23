package com.scmuWateringSystem.wateringSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan("com.scmuWateringSystem.wateringSystem.mqtt")
public class WateringSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(WateringSystemApplication.class, args);
	}

}
