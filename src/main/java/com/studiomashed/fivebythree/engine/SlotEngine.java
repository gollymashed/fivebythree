package com.studiomashed.fivebythree.engine;

import java.util.ArrayList;
import java.util.List;

import com.studiomashed.fivebythree.config.GameConfiguration;
import com.studiomashed.fivebythree.feature.FreeSpinMode;
import com.studiomashed.fivebythree.feature.FreeSpinStatus;
import com.studiomashed.fivebythree.feature.StickyWildHandler;
import com.studiomashed.fivebythree.model.*;
import com.studiomashed.fivebythree.state.FreeSpinState;
import com.studiomashed.fivebythree.state.GameState;

public final class SlotEngine {

    private final List<Reel> reels;
    private final List<Payline> paylines;
    private final OutcomeGenerator outcomeGenerator;
    private final WinEvaluator winEvaluator;
    private final ScatterEvaluator scatterEvaluator;
    private final StickyWildHandler stickyWildHandler;

    public SlotEngine(List<Reel> reels, List<Payline> paylines, OutcomeGenerator outcomeGenerator, WinEvaluator winEvaluator, ScatterEvaluator scatterEvaluator, StickyWildHandler stickyWildHandler) {
        if (reels.size() != 5) {
            throw new IllegalArgumentException("A 5x3 slot must contain exactly 5 reels");
        }

        this.reels = List.copyOf(reels);
        this.paylines = List.copyOf(paylines);
        this.outcomeGenerator = outcomeGenerator;
        this.winEvaluator = winEvaluator;
        this.scatterEvaluator = scatterEvaluator;
        this.stickyWildHandler = stickyWildHandler;
    }

    public SpinResult spin(long requestedBetInCoins, GameState gameState) {
        boolean isFreeSpin = gameState.hasFreeSpins();

        FreeSpinMode activeFreeSpinMode = isFreeSpin ? gameState.freeSpinState().mode() : null;

        if (!isFreeSpin) {
            gameState.setBetInCoins(requestedBetInCoins);
        }

        long totalBetInCoins = gameState.betInCoins();

        if (totalBetInCoins <= 0) {
            throw new IllegalArgumentException("Bet must be positive");
        }

        if (totalBetInCoins % paylines.size() != 0) {
            throw new IllegalArgumentException("Bet must be divisible by number of paylines");
        }

        long amountChargedInCoins = isFreeSpin ? 0 : totalBetInCoins;

        SpinGrid grid = outcomeGenerator.generate(reels);

        if (activeFreeSpinMode == FreeSpinMode.STICKY_WILDS) {
            grid = stickyWildHandler.apply(grid, gameState.lastGrid());
        }

        gameState.setLastGrid(grid);

        List<Win> wins = winEvaluator.evaluate(grid, paylines);

        long paylinesPayout = calculatePaylinesPayout(wins, totalBetInCoins);

        ScatterResult scatterResult = scatterEvaluator.evaluate(grid);

        int totalFreeSpinsToAward = scatterResult.freeSpins();

        if (scatterResult.freeSpinMode() == FreeSpinMode.RESPIN_PER_WILD
                || activeFreeSpinMode == FreeSpinMode.RESPIN_PER_WILD) {
            totalFreeSpinsToAward += countWilds(grid);
        }

        long scatterPayout = totalBetInCoins * scatterResult.payoutMultiplier();

        if (totalFreeSpinsToAward > 0) {

            if (gameState.hasFreeSpins()) {

                gameState.freeSpinState().award(totalFreeSpinsToAward, scatterResult.freeSpinMode());

            } else {

                gameState.startFreeSpins(totalFreeSpinsToAward, scatterResult.freeSpinMode());

            }

        }

        if (isFreeSpin) {

            FreeSpinState freeSpinState = gameState.freeSpinState();

            freeSpinState.consume();

            if (freeSpinState.isComplete()) {

                gameState.clearFreeSpins();

            }
        }

        long totalPayoutInCoins = paylinesPayout + scatterPayout;

        FreeSpinStatus freeSpinStatus = null;

        if (gameState.hasFreeSpins()) {

            FreeSpinState freeSpinState = gameState.freeSpinState();

            freeSpinStatus = new FreeSpinStatus(freeSpinState.mode(), freeSpinState.spinsRemaining());

        }

        SpinOutcome outcome = new SpinOutcome(grid, wins, scatterResult);

        return new SpinResult(amountChargedInCoins, totalBetInCoins, totalPayoutInCoins, outcome, freeSpinStatus);
    }

    private long calculatePaylinesPayout(List<Win> wins, long totalBetInCoins) {
        int paytablePayoutInCoins = 0;

        for (Win win : wins) {
            paytablePayoutInCoins += win.payoutCoins();
        }

        return totalBetInCoins * paytablePayoutInCoins / GameConfiguration.COINS_PER_BET;
    }

    private int countWilds(SpinGrid grid) {
        int wildCount = 0;

        for (ReelWindow reel : grid.reels()) {
            for (Symbol symbol : reel.symbols()) {
                if (symbol == Symbol.WILD) {
                    wildCount++;
                }
            }
        }

        return wildCount;
    }
}