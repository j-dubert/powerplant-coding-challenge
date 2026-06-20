package org.example.powerplant.application;

import org.example.powerplant.domain.DTO.FuelsDTO;
import org.example.powerplant.domain.DTO.NeedDescriptionDTO;
import org.example.powerplant.domain.DTO.POWER_PLANT_TYPE;
import org.example.powerplant.domain.DTO.PowerPlantDTO;
import org.example.powerplant.domain.PowerplantPlan;
import org.example.powerplant.exposition.ProductionPlan;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PowerPlantPlanerTest {

    private static final Logger log = LoggerFactory.getLogger(PowerPlantPlanerTest.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void planificationReturnCorrectPlan() {
        FuelsDTO fuels = new FuelsDTO(10, 50.2, 20, 50); // gas=10, wind=50%
        PowerPlantDTO wind = new PowerPlantDTO("wind1", POWER_PLANT_TYPE.WINDTURBINE, 1.0, 0, 100);
        PowerPlantDTO gas = new PowerPlantDTO("gas1", POWER_PLANT_TYPE.GASFIRED, 0.5, 100, 260);

        var need = new NeedDescriptionDTO(250, fuels, List.of(wind, gas));

        PowerPlantPlaner planer = new PowerPlantPlaner();
        List<PowerplantPlan> plans = planer.planification(need);

        assertEquals(2, plans.size());
        assertEquals("wind1", plans.get(0).name());
        assertEquals(50.0, plans.get(0).p());
        assertEquals("gas1", plans.get(1).name());
        assertEquals(200.0, plans.get(1).p());
    }

    @Test
    void planificationWithIrregularNeed() {
        FuelsDTO fuels = new FuelsDTO(10, 50.2, 20, 50); // gas=10, wind=50%
        PowerPlantDTO wind = new PowerPlantDTO("wind1", POWER_PLANT_TYPE.WINDTURBINE, 1.0, 0, 100);
        PowerPlantDTO gas1 = new PowerPlantDTO("gas1", POWER_PLANT_TYPE.GASFIRED, 0.5, 100, 250);
        PowerPlantDTO gas2 = new PowerPlantDTO("gas2", POWER_PLANT_TYPE.GASFIRED, 0.5, 100, 260);
        PowerPlantDTO smallGas = new PowerPlantDTO("smallGas", POWER_PLANT_TYPE.GASFIRED, 0.5, 20, 50);


        var need = new NeedDescriptionDTO(320, fuels, List.of(wind, gas1, gas2, smallGas));

        PowerPlantPlaner planer = new PowerPlantPlaner();
        List<PowerplantPlan> plans = planer.planification(need);

        log.info(objectMapper.writeValueAsString(plans));

        assertEquals(4, plans.size());
        assertEquals("wind1", plans.get(0).name());
        assertEquals(50.0, plans.get(0).p());
        assertEquals("gas2", plans.get(1).name());
        assertEquals(170.0, plans.get(1).p());
        assertEquals("gas1", plans.get(2).name());
        assertEquals(100.0, plans.get(2).p());
        assertEquals("smallGas", plans.get(3).name());
        assertEquals(0.0, plans.get(3).p());
    }

    //Other test cases can be added here to cover more scenarios, such as edge cases, invalid inputs, etc.
}