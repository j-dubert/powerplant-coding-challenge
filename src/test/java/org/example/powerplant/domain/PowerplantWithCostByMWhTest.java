package org.example.powerplant.domain;

import org.example.powerplant.domain.DTO.FuelsDTO;
import org.example.powerplant.domain.DTO.POWER_PLANT_TYPE;
import org.example.powerplant.domain.DTO.PowerPlantDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerplantWithCostByMWhTest {

    @Test
    void objectMustInitiateCorrectlyCostByMWh() {
        PowerPlantDTO source = new PowerPlantDTO("testName", POWER_PLANT_TYPE.GASFIRED, 0.5, 0, 100);
        FuelsDTO fuels = new FuelsDTO(10, 50.2, 20, 5);

        PowerplantWithCostByMWh result = new PowerplantWithCostByMWh(source, fuels);

        assertEquals(20.0, result.getCostByMWh());
        assertEquals("testName", result.getName());
        assertEquals(POWER_PLANT_TYPE.GASFIRED, result.getType());
        assertEquals(0.5, result.getEfficiency());
        assertEquals(0, result.getPmin());
        assertEquals(100, result.getPmax());
    }
}