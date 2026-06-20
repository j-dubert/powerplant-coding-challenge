package org.example.powerplant.domain;

import org.example.powerplant.domain.DTO.FuelsDTO;
import org.example.powerplant.domain.DTO.POWER_PLANT_TYPE;
import org.example.powerplant.domain.DTO.PowerPlantDTO;

public class PowerplantWithCostByMWh {
    private final PowerPlantDTO powerPlant;
    private final double costByMWh;

    public PowerplantWithCostByMWh(PowerPlantDTO powerPlant, FuelsDTO fuels) {
        this.powerPlant = powerPlant;
        this.costByMWh = calculateProfitability(powerPlant.type(), powerPlant.efficiency(), fuels);
    }


    private double calculateProfitability(POWER_PLANT_TYPE type, double efficiency, FuelsDTO fuels) {
        double fuelPrice = getFuelPrice(type, fuels);
        return fuelPrice / efficiency;
    }

    private double getFuelPrice(POWER_PLANT_TYPE type, FuelsDTO fuels) {
        switch (type) {
            case POWER_PLANT_TYPE.GASFIRED -> {
                return fuels.gas();
            }
            case POWER_PLANT_TYPE.TURBOJET -> {
                return fuels.kerosine();
            }
            case POWER_PLANT_TYPE.WINDTURBINE -> {
                return 0.0;
            }
            default -> throw new IllegalAccessError("Unknown type of power plant for cost calculation");
        }
    }

    public double getCostByMWh() {
        return costByMWh;
    }
    public String getName() { return powerPlant.name(); }
    public POWER_PLANT_TYPE getType() { return powerPlant.type(); }
    public double getEfficiency() { return powerPlant.efficiency(); }
    public double getPmin() { return powerPlant.pmin(); }
    public double getPmax() { return powerPlant.pmax(); }
}
