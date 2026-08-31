package com.studiomashed.fivebythree.simulation;

import com.studiomashed.fivebythree.engine.SlotEngine;
import com.studiomashed.fivebythree.model.GameState;
import com.studiomashed.fivebythree.model.SpinResult;

public final class SimulationRunner {

    private final SlotEngine slotEngine;

    public SimulationRunner(SlotEngine slotEngine) {
        this.slotEngine = slotEngine;
    }

    public SimulationResult run(
            long numberOfSpins,
            long betInPence) {

        if (numberOfSpins <= 0) {
            throw new IllegalArgumentException(
                    "Number of spins must be positive");
        }

        if (betInPence <= 0) {
            throw new IllegalArgumentException(
                    "Bet must be positive");
        }

        GameState gameState = new GameState();

        long totalSpentInPence = 0;
        long totalPaidInPence = 0;
        long winningSpins = 0;

        long largestPayoutInPence = 0;
        long largestCyclePayoutInPence = 0;

        long currentCyclePayoutInPence = 0;

        long currentFreeSpinRun = 0;
        long longestFreeSpinRun = 0;

        long baseGamePaidInPence = 0;

        long paidSpins = 0;
        long totalSpins = 0;

        while (paidSpins < numberOfSpins || gameState.hasFreeSpins()) {

            boolean isFreeSpin = gameState.hasFreeSpins();

            SpinResult result =
                    slotEngine.spin(betInPence, gameState);

            totalSpins++;

            if (isFreeSpin) {
                currentFreeSpinRun++;

                longestFreeSpinRun = Math.max(
                        longestFreeSpinRun,
                        currentFreeSpinRun);
            } else {
                currentFreeSpinRun = 0;
            }

            totalSpentInPence = Math.addExact(
                    totalSpentInPence,
                    result.amountChargedInPence());

            totalPaidInPence = Math.addExact(
                    totalPaidInPence,
                    result.payoutInPence());

            currentCyclePayoutInPence = Math.addExact(
                    currentCyclePayoutInPence,
                    result.payoutInPence());

            if (!isFreeSpin) {
                paidSpins++;

                baseGamePaidInPence = Math.addExact(
                        baseGamePaidInPence,
                        result.payoutInPence());
            }

            if (result.outcome().isWin()) {
                winningSpins++;
            }

            largestPayoutInPence = Math.max(
                    largestPayoutInPence,
                    result.payoutInPence());

            if (!gameState.hasFreeSpins()) {

                largestCyclePayoutInPence = Math.max(
                        largestCyclePayoutInPence,
                        currentCyclePayoutInPence);

                currentCyclePayoutInPence = 0;
            }
        }

        long freeSpins = totalSpins - paidSpins;

        long freeSpinPaidInPence =
                totalPaidInPence - baseGamePaidInPence;

        return new SimulationResult(
                totalSpins,
                paidSpins,
                freeSpins,
                totalSpentInPence,
                totalPaidInPence,
                baseGamePaidInPence,
                freeSpinPaidInPence,
                winningSpins,
                largestPayoutInPence,
                largestCyclePayoutInPence,
                longestFreeSpinRun);
    }
}