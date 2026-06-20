package org.example.powerplant.domain.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FuelsDTO(
        @JsonProperty("gas(euro/MWh)")
        double gas,
        @JsonProperty("kerosine(euro/MWh)")
        double kerosine,
        @JsonProperty("co2(euro/ton)")
        double co2,
        @JsonProperty("wind(%)")
        double wind
) {
}
