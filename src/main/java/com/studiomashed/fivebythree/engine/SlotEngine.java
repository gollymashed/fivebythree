package com.studiomashed.fivebythree.engine;

import java.util.List;

import com.studiomashed.fivebythree.config.GameConfiguration;
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

    public SpinResult spin(long requestedBetInCoins, GameState gameState) {

        boolean isFreeSpin = gameState.hasFreeSpins();

        long totalBetInCoins = isFreeSpin ? gameState.freeSpinBetInPence() : requestedBetInCoins;

        if (totalBetInCoins <= 0) {
            throw new IllegalArgumentException(
                    "Stake must be positive");
        }

        if (totalBetInCoins % GameConfiguration.COINS_PER_BET != 0) {
            throw new IllegalArgumentException(
                    "Bet must be divisible by number of paylines");
        }

        long amountChargedInPence = isFreeSpin
                ? 0
                : totalBetInCoins;

        if (isFreeSpin) {
            gameState.consumeFreeSpin();
        }

        SpinGrid grid = outcomeGenerator.generate(reels);

        List<Win> wins = winEvaluator.evaluate(grid, paylines);

        int paytablePayoutInCoins = 0;

        for (Win win : wins) {
            paytablePayoutInCoins += win.payoutCoins();
        }

        long paylinesPayout = totalBetInCoins
                * paytablePayoutInCoins
                / GameConfiguration.COINS_PER_BET;

        ScatterResult scatterResult = scatterEvaluator.evaluate(grid);

        long scatterPayout = totalBetInCoins * scatterResult.payoutMultiplier();

        if (scatterResult.freeSpins() > 0) {
            gameState.awardFreeSpins(
                    scatterResult.freeSpins(),
                    totalBetInCoins);
        }

        long totalPayoutInCoins = scatterPayout + paylinesPayout;

        SpinOutcome outcome = new SpinOutcome(
                grid,
                wins,
                scatterResult);

        return new SpinResult(
                amountChargedInPence,
                totalBetInCoins,
                totalPayoutInCoins,
                outcome);
    }
}