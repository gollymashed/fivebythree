package com.studiomashed.threebyone.config;

import com.studiomashed.threebyone.engine.OutcomeGenerator;
import com.studiomashed.threebyone.engine.SlotEngine;
import com.studiomashed.threebyone.engine.WinEvaluator;
import com.studiomashed.threebyone.model.Paytable;
import com.studiomashed.threebyone.model.Reel;
import com.studiomashed.threebyone.model.Symbol;
import com.studiomashed.threebyone.rng.JavaRandomNumberGenerator;
import com.studiomashed.threebyone.rng.RandomNumberGenerator;
import com.studiomashed.threebyone.simulation.SimulationRunner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class GameConfiguration {

    @Bean
    public RandomNumberGenerator randomNumberGenerator() {
        return new JavaRandomNumberGenerator();
    }

    @Bean
    public OutcomeGenerator outcomeGenerator(
            RandomNumberGenerator rng) {
        return new OutcomeGenerator(rng);
    }

    @Bean
    public Paytable paytable() {
        return new Paytable(Map.of(
                Symbol.CHERRY, 1,
                Symbol.LEMON, 3,
                Symbol.BELL, 10,
                Symbol.SEVEN, 30,
                Symbol.WILD, 71));
    }

    @Bean
    public WinEvaluator winEvaluator(Paytable paytable) {
        return new WinEvaluator(paytable);
    }

    @Bean
    public SlotEngine slotEngine(
            OutcomeGenerator outcomeGenerator,
            WinEvaluator winEvaluator) {
        List<Symbol> reelStrip = List.of(
                Symbol.CHERRY,
                Symbol.CHERRY,
                Symbol.CHERRY,
                Symbol.LEMON,
                Symbol.LEMON,
                Symbol.BELL,
                Symbol.SEVEN,
                Symbol.WILD);

        List<Reel> reels = List.of(
                new Reel(reelStrip),
                new Reel(reelStrip),
                new Reel(reelStrip));

        return new SlotEngine(
                reels,
                outcomeGenerator,
                winEvaluator);
    }

    @Bean
    public SimulationRunner simulationRunner(
            SlotEngine slotEngine) {
        return new SimulationRunner(slotEngine);
    }
}