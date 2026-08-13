package com.studiomashed.threebythree.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.studiomashed.threebythree.engine.OutcomeGenerator;
import com.studiomashed.threebythree.engine.SlotEngine;
import com.studiomashed.threebythree.engine.WinEvaluator;
import com.studiomashed.threebythree.model.Payline;
import com.studiomashed.threebythree.model.Paytable;
import com.studiomashed.threebythree.model.Reel;
import com.studiomashed.threebythree.model.Symbol;
import com.studiomashed.threebythree.rng.JavaRandomNumberGenerator;
import com.studiomashed.threebythree.rng.RandomNumberGenerator;
import com.studiomashed.threebythree.simulation.SimulationRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

        List<Payline> paylines = List.of(
                new Payline(1, List.of(1, 1, 1)), // middle
                new Payline(2, List.of(0, 0, 0)), // top
                new Payline(3, List.of(2, 2, 2)), // bottom
                new Payline(4, List.of(0, 1, 2)), // diagonal down
                new Payline(5, List.of(2, 1, 0)) // diagonal up
        );

        Set<Integer> validNumberOfPaylines = Set.of(1, 3, 5);

        return new SlotEngine(
                reels,
                paylines,
                validNumberOfPaylines,
                outcomeGenerator,
                winEvaluator);
    }

    @Bean
    public SimulationRunner simulationRunner(
            SlotEngine slotEngine) {
        return new SimulationRunner(slotEngine);
    }
}