package org.example.powerplant.application;

import org.example.powerplant.domain.DTO.FuelsDTO;
import org.example.powerplant.domain.DTO.NeedDescriptionDTO;
import org.example.powerplant.domain.DTO.POWER_PLANT_TYPE;
import org.example.powerplant.domain.PowerPlantProduction;
import org.example.powerplant.domain.PowerplantWithCostByMWh;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PowerPlantPlaner {

    public List<PowerPlantProduction> planification(NeedDescriptionDTO input) {
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

        return getPowerplantPlans(totalLoad, powerPlantsWithCostByMWh, fuelsPrice);

    }

    private static @NonNull List<PowerPlantProduction> getPowerplantPlans(double totalLoad, List<PowerplantWithCostByMWh> powerPlantsWithCostByMWh, FuelsDTO fuelsPrice) {
        List<PowerPlantProduction> plans = new ArrayList<>();
        double remaining = totalLoad;

        for (var powerPlant : powerPlantsWithCostByMWh) {
            if (remaining <= 0) {
                plans.add(new PowerPlantProduction(powerPlant.getName(), 0));
                continue;
            }
            double maxPowerGenerated = powerPlant.getType() == POWER_PLANT_TYPE.WINDTURBINE
                    ? powerPlant.getPmax() * (fuelsPrice.wind() / 100)
                    : powerPlant.getPmax();

            double p = Math.max(0, Math.min(remaining, maxPowerGenerated));
            p = Math.round(p * 10) / 10.0;

            if (p < powerPlant.getPmin()) {
                PowerPlantProduction last = plans.getLast();
                plans.removeLast();
                plans.add(new PowerPlantProduction(last.name(), last.p() - (powerPlant.getPmin() - p)));
                p = powerPlant.getPmin();
            }

            plans.add(new PowerPlantProduction(powerPlant.getName(), p));
            remaining -= p;
        }
        return plans;
    }
}
