package com.studiomashed.fivebythree.engine;

import java.util.List;
import java.util.Set;

import com.studiomashed.fivebythree.model.*;

public final class SlotEngine {

    private final List<Reel> reels;
    private final List<Payline> paylines;
    private final OutcomeGenerator outcomeGenerator;
    private final WinEvaluator winEvaluator;
    private final ScatterEvaluator scatterEvaluator;

    public SlotEngine(
            List<Reel> reels,
            List<Payline> paylines,
            OutcomeGenerator outcomeGenerator,
            WinEvaluator winEvaluator,
            ScatterEvaluator scatterEvaluator) {
        if (reels.size() != 5) {
            throw new IllegalArgumentException(
                    "A 5x3 slot must contain exactly 5 reels");
        }

        this.reels = List.copyOf(reels);
        this.paylines = List.copyOf(paylines);
        this.outcomeGenerator = outcomeGenerator;
        this.winEvaluator = winEvaluator;
        this.scatterEvaluator = scatterEvaluator;
    }

    public SpinResult spin(long requestedBetInPence, GameState gameState) {

        boolean isFreeSpin = gameState.hasFreeSpins();

        long totalBetInPence = isFreeSpin ? gameState.freeSpinBetInPence() : requestedBetInPence;

        if (totalBetInPence <= 0) {
            throw new IllegalArgumentException(
                    "Stake must be positive");
        }

        if (totalBetInPence % paylines.size() != 0) {
            throw new IllegalArgumentException(
                    "Bet must be divisible by number of paylines");
        }

        long amountChargedInPence = isFreeSpin
                ? 0
                : totalBetInPence;

        if (isFreeSpin) {
            gameState.consumeFreeSpin();
        }

        SpinGrid grid = outcomeGenerator.generate(reels);

        List<Win> wins = winEvaluator.evaluate(grid, paylines);

        int multiplier = 0;

        for (Win win : wins) {
            multiplier += win.payoutMultiplier();
        }

        long paylinesPayout = totalBetInPence * multiplier / paylines.size();

        ScatterResult scatterResult = scatterEvaluator.evaluate(grid);

        long scatterPayout = totalBetInPence * scatterResult.payoutMultiplier();

        if (scatterResult.freeSpins() > 0) {
            gameState.awardFreeSpins(
                    scatterResult.freeSpins(),
                    totalBetInPence);
        }

        long totalPayoutInPence = scatterPayout + paylinesPayout;

        SpinOutcome outcome = new SpinOutcome(
                grid,
                wins,
                scatterResult);

        return new SpinResult(
                amountChargedInPence,
                totalBetInPence,
                totalPayoutInPence,
                outcome);
    }
}