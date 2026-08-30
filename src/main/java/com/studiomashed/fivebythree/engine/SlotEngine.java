package com.studiomashed.fivebythree.engine;

import java.util.List;
import java.util.Set;

import com.studiomashed.fivebythree.model.Payline;
import com.studiomashed.fivebythree.model.Reel;
import com.studiomashed.fivebythree.model.SpinGrid;
import com.studiomashed.fivebythree.model.SpinOutcome;
import com.studiomashed.fivebythree.model.SpinResult;
import com.studiomashed.fivebythree.model.Win;

public final class SlotEngine {

    private final List<Reel> reels;
    private final List<Payline> paylines;
    private final Set<Integer> validNumberOfPaylines;
    private final OutcomeGenerator outcomeGenerator;
    private final WinEvaluator winEvaluator;

    public SlotEngine(
            List<Reel> reels,
            List<Payline> paylines,
            Set<Integer> validNumberOfPaylines,
            OutcomeGenerator outcomeGenerator,
            WinEvaluator winEvaluator) {
        if (reels.size() != 5) {
            throw new IllegalArgumentException(
                    "A 5x3 slot must contain exactly 5 reels");
        }

        this.reels = List.copyOf(reels);
        this.paylines = List.copyOf(paylines);
        this.validNumberOfPaylines = Set.copyOf(validNumberOfPaylines);
        this.outcomeGenerator = outcomeGenerator;
        this.winEvaluator = winEvaluator;
    }

    public SpinResult spin(long stakePerLineInPence, int numberOfPaylines) {
        if (stakePerLineInPence <= 0) {
            throw new IllegalArgumentException(
                    "Stake must be positive");
        }

        if (!validNumberOfPaylines.contains(numberOfPaylines)) {
            throw new IllegalArgumentException(
                    "Invalid number of paylines");
        }

        List<Payline> activePaylines = paylines.subList(0, numberOfPaylines);
        
        long totalStakeInPence = Math.multiplyExact(stakePerLineInPence, activePaylines.size());
        
        SpinGrid grid = outcomeGenerator.generate(reels);
        
        List<Win> wins = winEvaluator.evaluate(grid, activePaylines);
        
        long payoutInPence = 0;

        for (Win win : wins) {
            long linePayout = Math.multiplyExact(
                    stakePerLineInPence,
                    win.payoutMultiplier());

            payoutInPence = Math.addExact(
                    payoutInPence,
                    linePayout);
        }

        SpinOutcome outcome = new SpinOutcome(
                grid,
                wins);

        return new SpinResult(
                stakePerLineInPence,
                totalStakeInPence,
                payoutInPence,
                outcome);
    }
}