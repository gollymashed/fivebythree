package com.studiomashed.fivebythree.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studiomashed.fivebythree.simulation.SimulationResult;
import com.studiomashed.fivebythree.simulation.SimulationRunner;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SimulationController {

    private final SimulationRunner simulationRunner;

    public SimulationController(
            SimulationRunner simulationRunner) {
        this.simulationRunner = simulationRunner;
    }

    @PostMapping("/simulate")
    public SimulationResult simulate(
            @RequestParam(defaultValue = "1000000") long spins,
            @RequestParam(defaultValue = "100") long betInCoins) {

        return simulationRunner.run(
                spins,
                betInCoins);
    }
}