package com.studiomashed.fivebythree.config;

import com.studiomashed.fivebythree.engine.ScatterEvaluator;
import com.studiomashed.fivebythree.feature.FreeSpinMode;
import com.studiomashed.fivebythree.feature.StickyWildHandler;
import com.studiomashed.fivebythree.feature.SymbolMultiplierHandler;
import com.studiomashed.fivebythree.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.studiomashed.fivebythree.engine.OutcomeGenerator;
import com.studiomashed.fivebythree.engine.SlotEngine;
import com.studiomashed.fivebythree.engine.WinEvaluator;
import com.studiomashed.fivebythree.rng.JavaRandomNumberGenerator;
import com.studiomashed.fivebythree.rng.RandomNumberGenerator;
import com.studiomashed.fivebythree.simulation.SimulationRunner;

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
    public PaylinePaytable paylinePaytable() {
        Map<Symbol, int[]> payouts = Map.of(
                Symbol.LP1, new int[]{0, 0, 0, 2, 4, 6},
                Symbol.LP2, new int[]{0, 0, 0, 2, 4, 6},
                Symbol.LP3, new int[]{0, 0, 0, 2, 4, 6},
                Symbol.LP4, new int[]{0, 0, 0, 2, 4, 6},
                Symbol.MP1, new int[]{0, 0, 0, 4, 12, 24},
                Symbol.MP2, new int[]{0, 0, 0, 4, 12, 24},
                Symbol.MP3, new int[]{0, 0, 0, 4, 12, 24},
                Symbol.HP1, new int[]{0, 0, 0, 24, 60, 250},
                Symbol.WILD, new int[]{0, 0, 0, 42, 250, 2500});

        return new PaylinePaytable(payouts);
    }

    public static final int COINS_PER_BET = 10;

    @Bean
    public WinEvaluator winEvaluator(PaylinePaytable paylinePaytable) {
        return new WinEvaluator(paylinePaytable);
    }

    @Bean
    public ScatterPaytable scatterPaytable() {
        int[] payoutMultipliers = {
                0, 0, 0, 0, 2, 5
        };

        int[] freeSpins = {
                0, 0, 0, 1, 4, 8
        };

        FreeSpinMode[] freeSpinModes = {
                null,
                null,
                null,
                FreeSpinMode.RESPIN_PER_WILD,
                FreeSpinMode.SYMBOL_MULTIPLIERS,
                FreeSpinMode.STICKY_WILDS
        };

        return new ScatterPaytable(
                payoutMultipliers,
                freeSpins,
                freeSpinModes
        );
    }

    @Bean
    public ScatterEvaluator scatterEvaluator(ScatterPaytable scatterPaytable) {
        return new ScatterEvaluator(scatterPaytable);
    }

    @Bean
    public StickyWildHandler stickyWildHandler() {
        return new StickyWildHandler();
    }

    @Bean
    public Map<Symbol, Integer> SymbolFeatureMultipliers() {
        return Map.of(
                Symbol.MP1, 2,
                Symbol.MP2, 3,
                Symbol.MP3, 4
        );
    }

    @Bean
    public SymbolMultiplierHandler symbolMultiplierHandler(
            Map<Symbol, Integer> symbolFeatureMultipliers
    ) {
        return new SymbolMultiplierHandler(
                symbolFeatureMultipliers
        );
    }

    @Bean
    public SlotEngine slotEngine(
            OutcomeGenerator outcomeGenerator,
            WinEvaluator winEvaluator,
            ScatterEvaluator scatterEvaluator,
            StickyWildHandler stickyWildHandler,
            SymbolMultiplierHandler symbolMultiplierHandler) {
        List<Symbol> reel1 = List.of(
                Symbol.LP1,
                Symbol.MP1,
                Symbol.LP2,
                Symbol.HP1,
                Symbol.MP2,
                Symbol.LP3,
                Symbol.WILD,
                Symbol.LP4,
                Symbol.HP1,
                Symbol.MP3,
                Symbol.LP1,
                Symbol.MP1,
                Symbol.MP2,
                Symbol.HP1,
                Symbol.LP2,
                Symbol.LP3,
                Symbol.SCATTER,
                Symbol.LP4);

        List<Symbol> reel2 = List.of(
                Symbol.LP2,
                Symbol.MP3,
                Symbol.LP4,
                Symbol.HP1,
                Symbol.MP2,
                Symbol.LP1,
                Symbol.MP1,
                Symbol.LP3,
                Symbol.HP1,
                Symbol.MP1,
                Symbol.LP2,
                Symbol.WILD,
                Symbol.MP3,
                Symbol.LP4,
                Symbol.HP1,
                Symbol.LP1,
                Symbol.SCATTER,
                Symbol.LP3);

        List<Symbol> reel3 = List.of(
                Symbol.LP1,
                Symbol.MP1,
                Symbol.LP4,
                Symbol.HP1,
                Symbol.MP3,
                Symbol.LP2,
                Symbol.WILD,
                Symbol.LP3,
                Symbol.MP2,
                Symbol.LP1,
                Symbol.MP3,
                Symbol.LP4,
                Symbol.HP1,
                Symbol.LP2,
                Symbol.MP1,
                Symbol.LP3,
                Symbol.SCATTER,
                Symbol.LP4);

        List<Symbol> reel4 = List.of(
                Symbol.LP2,
                Symbol.MP1,
                Symbol.LP4,
                Symbol.MP3,
                Symbol.LP1,
                Symbol.WILD,
                Symbol.LP3,
                Symbol.HP1,
                Symbol.LP2,
                Symbol.LP4,
                Symbol.LP1,
                Symbol.MP2,
                Symbol.LP3,
                Symbol.LP4,
                Symbol.MP3,
                Symbol.LP2,
                Symbol.SCATTER,
                Symbol.LP1);

        List<Symbol> reel5 = List.of(
                Symbol.LP1,
                Symbol.MP3,
                Symbol.LP4,
                Symbol.LP2,
                Symbol.LP3,
                Symbol.MP2,
                Symbol.LP1,
                Symbol.MP1,
                Symbol.LP4,
                Symbol.WILD,
                Symbol.LP2,
                Symbol.LP3,
                Symbol.LP4,
                Symbol.HP1,
                Symbol.MP3,
                Symbol.LP1,
                Symbol.SCATTER,
                Symbol.LP3);

        List<Reel> reels = List.of(
                new Reel(reel1),
                new Reel(reel2),
                new Reel(reel3),
                new Reel(reel4),
                new Reel(reel5));

        List<Payline> paylines = List.of(
                new Payline(1, List.of(1, 1, 1, 1, 1)),
                new Payline(2, List.of(0, 0, 0, 0, 0)),
                new Payline(3, List.of(2, 2, 2, 2, 2)),
                new Payline(4, List.of(0, 1, 2, 1, 0)),
                new Payline(5, List.of(2, 1, 0, 1, 2)),
                new Payline(6, List.of(1, 0, 1, 0, 1)),
                new Payline(7, List.of(1, 2, 1, 2, 1)),
                new Payline(8, List.of(0, 0, 1, 2, 2)),
                new Payline(9, List.of(2, 2, 1, 0, 0)),
                new Payline(10, List.of(1, 2, 1, 0, 1)),
                new Payline(11, List.of(1, 0, 1, 2, 1)),
                new Payline(12, List.of(0, 1, 1, 1, 0)),
                new Payline(13, List.of(2, 1, 1, 1, 2)),
                new Payline(14, List.of(0, 1, 0, 1, 0)),
                new Payline(15, List.of(2, 1, 2, 1, 2)),
                new Payline(16, List.of(1, 1, 0, 1, 1)),
                new Payline(17, List.of(1, 1, 2, 1, 1)),
                new Payline(18, List.of(0, 0, 2, 0, 0)),
                new Payline(19, List.of(2, 2, 0, 2, 2)),
                new Payline(20, List.of(0, 2, 2, 2, 0))

        );

        return new SlotEngine(
                reels,
                paylines,
                outcomeGenerator,
                winEvaluator,
                scatterEvaluator,
                stickyWildHandler,
                symbolMultiplierHandler);
    }

    @Bean
    public SimulationRunner simulationRunner(
            SlotEngine slotEngine) {
        return new SimulationRunner(slotEngine);
    }
}