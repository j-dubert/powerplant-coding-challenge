package org.example.powerplant.exposition;

import org.example.powerplant.application.PowerPlantPlaner;
import org.example.powerplant.domain.DTO.NeedDescriptionDTO;
import org.example.powerplant.domain.PowerPlantProduction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping
public class ProductionPlan {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PowerPlantPlaner powerPlantPlaner = new PowerPlantPlaner();

    @PostMapping(value = "productionplan", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getProductionPlan(@RequestBody String payload) {
        NeedDescriptionDTO obj = objectMapper.readValue(payload, NeedDescriptionDTO.class);
        List<PowerPlantProduction> tmp = powerPlantPlaner.planification(obj);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tmp);
    }
}
