package com.studiomashed.threebyone.api;

import com.studiomashed.threebyone.simulation.SimulationResult;
import com.studiomashed.threebyone.simulation.SimulationRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimulationController {

    private final SimulationRunner simulationRunner;

    public SimulationController(
            SimulationRunner simulationRunner) {
        this.simulationRunner = simulationRunner;
    }

    @PostMapping("/simulate")
    public SimulationResult simulate(
            @RequestParam(defaultValue = "1000000") long spins,

            @RequestParam(defaultValue = "100") long stakeInPence) {
        return simulationRunner.run(
                spins,
                stakeInPence);
    }
}