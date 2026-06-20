package org.example.powerplant.domain.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record NeedDescriptionDTO(
        @JsonProperty("load")
        Integer load,
        @JsonProperty("fuels")
        FuelsDTO fuels,
        @JsonProperty("powerplants")
        List<PowerPlantDTO> powerplants
) {
}


