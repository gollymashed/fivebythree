package com.studiomashed.threebyone.engine;

import com.studiomashed.threebyone.model.Reel;
import com.studiomashed.threebyone.model.SpinOutcome;
import com.studiomashed.threebyone.model.SpinResult;
import com.studiomashed.threebyone.model.Symbol;

import java.util.List;

public final class SlotEngine {

    private final List<Reel> reels;
    private final OutcomeGenerator outcomeGenerator;
    private final WinEvaluator winEvaluator;

    public SlotEngine(
            List<Reel> reels,
            OutcomeGenerator outcomeGenerator,
            WinEvaluator winEvaluator) {
        if (reels.size() != 3) {
            throw new IllegalArgumentException(
                    "A 3x1 slot must contain exactly 3 reels");
        }

        this.reels = List.copyOf(reels);
        this.outcomeGenerator = outcomeGenerator;
        this.winEvaluator = winEvaluator;
    }

    public SpinResult spin(long stakeInPence) {
        if (stakeInPence <= 0) {
            throw new IllegalArgumentException(
                    "Stake must be positive");
        }

        List<Symbol> symbols = outcomeGenerator.generate(reels);

        int payoutMultiplier = winEvaluator.evaluate(symbols);

        long payoutInPence = Math.multiplyExact(
                stakeInPence,
                payoutMultiplier);

        SpinOutcome outcome = new SpinOutcome(
                symbols,
                payoutMultiplier);

        return new SpinResult(
                stakeInPence,
                payoutInPence,
                outcome);
    }
}