package com.studiomashed.threebythree.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studiomashed.threebythree.simulation.SimulationResult;
import com.studiomashed.threebythree.simulation.SimulationRunner;

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
            @RequestParam(defaultValue = "20") long stakePerLineInPence,
            @RequestParam(defaultValue = "5") int numberOfPaylines) {

        return simulationRunner.run(
                spins,
                stakePerLineInPence,
                numberOfPaylines);
    }
}