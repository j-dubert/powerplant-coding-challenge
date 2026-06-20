package org.example.powerplant.domain.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PowerPlantDTO(
        @JsonProperty("name")
        String name,
        @JsonProperty("type")
        POWER_PLANT_TYPE type,
        @JsonProperty("efficiency")
        double efficiency,
        @JsonProperty("pmin")
        double pmin,
        @JsonProperty("pmax")
        double pmax) {
}