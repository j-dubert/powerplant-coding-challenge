package org.example.powerplant.exposition;

import org.example.powerplant.application.PowerPlantPlaner;
import org.example.powerplant.domain.DTO.NeedDescriptionDTO;
import org.example.powerplant.domain.PowerplantPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping
public class ProductionPlan {
    private static final Logger log = LoggerFactory.getLogger(ProductionPlan.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PowerPlantPlaner powerPlantPlaner = new PowerPlantPlaner();

    @PostMapping(value = "productionplan", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getProductionPlan(@RequestBody String payload) {
        log.info(payload);
        NeedDescriptionDTO obj = objectMapper.readValue(payload, NeedDescriptionDTO.class);
        List<PowerplantPlan> tmp = powerPlantPlaner.planification(obj);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tmp);
    }
}
