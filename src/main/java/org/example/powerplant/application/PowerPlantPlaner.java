package org.example.powerplant.application;

import org.example.powerplant.domain.DTO.FuelsDTO;
import org.example.powerplant.domain.DTO.NeedDescriptionDTO;
import org.example.powerplant.domain.DTO.POWER_PLANT_TYPE;
import org.example.powerplant.domain.PowerplantPlan;
import org.example.powerplant.domain.PowerplantWithCostByMWh;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PowerPlantPlaner {

    public List<PowerplantPlan> planification(NeedDescriptionDTO input) {
        double totalLoad = input.load();
        FuelsDTO fuelsPrice = input.fuels();
        List<PowerplantWithCostByMWh> powerPlantsWithCostByMWh = input.powerplants()
                .stream()
                .map(powerplant -> new PowerplantWithCostByMWh(powerplant, fuelsPrice))
                .sorted(Comparator
                        .comparingDouble(PowerplantWithCostByMWh::getCostByMWh)
                        .thenComparing(PowerplantWithCostByMWh::getPmax,
                                Comparator.reverseOrder()))
                .toList();

        AtomicReference<Double> power = new AtomicReference<>(0.0);

        return powerPlantsWithCostByMWh.stream().map(powerPlant -> {
            double maxPowerGenerated = powerPlant.getType() == POWER_PLANT_TYPE.WINDTURBINE ? powerPlant.getPmax() * (fuelsPrice.wind() / 100) : powerPlant.getPmax();
            double p = (power.get() + maxPowerGenerated >= totalLoad) ? (totalLoad - power.get()) : maxPowerGenerated;
            power.updateAndGet(v -> v + p);
            return new PowerplantPlan(powerPlant.getName(), p);
        }).toList();
    }
}
