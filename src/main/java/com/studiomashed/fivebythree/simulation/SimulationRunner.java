package com.studiomashed.fivebythree.simulation;

import com.studiomashed.fivebythree.engine.SlotEngine;
import com.studiomashed.fivebythree.state.GameState;
import com.studiomashed.fivebythree.model.SpinResult;

import java.util.EnumMap;
import java.util.Map;

public final class SimulationRunner {

    private final SlotEngine slotEngine;

    public SimulationRunner(SlotEngine slotEngine) {
        this.slotEngine = slotEngine;
    }

    public SimulationResult run(
            long numberOfSpins,
            long betInCoins) {

        if (numberOfSpins <= 0) {
            throw new IllegalArgumentException(
                    "Number of spins must be positive");
        }

        if (betInCoins <= 0) {
            throw new IllegalArgumentException(
                    "Bet must be positive");
        }

        GameState gameState = new GameState();

        long totalSpentInCoins = 0;
        long totalPaidInCoins = 0;
        long winningSpins = 0;

        long largestPayoutInCoins = 0;
        long largestCyclePayoutInCoins = 0;

        SpinResult largestPayoutResult = null;
        long largestPayoutSpinNumber = -1;

        long currentCyclePayoutInCoins = 0;

        long currentFreeSpinRun = 0;
        long longestFreeSpinRun = 0;

        long baseGamePaidInCoins = 0;

        long paidSpins = 0;
        long totalSpins = 0;

        Map<PayoutBucket, Long> payoutDistribution =
                new EnumMap<>(PayoutBucket.class);

        for (PayoutBucket bucket : PayoutBucket.values()) {
            payoutDistribution.put(bucket, 0L);
        }

        while (paidSpins < numberOfSpins || gameState.hasFreeSpins()) {

            boolean isFreeSpin =
                    gameState.hasFreeSpins();

            SpinResult result =
                    slotEngine.spin(
                            betInCoins,
                            gameState);

            totalSpins++;

            if (isFreeSpin) {

                currentFreeSpinRun++;

                longestFreeSpinRun = Math.max(
                        longestFreeSpinRun,
                        currentFreeSpinRun);

            } else {

                currentFreeSpinRun = 0;
            }

            totalSpentInCoins = Math.addExact(
                    totalSpentInCoins,
                    result.amountChargedInCoins());

            totalPaidInCoins = Math.addExact(
                    totalPaidInCoins,
                    result.payoutInCoins());

            currentCyclePayoutInCoins = Math.addExact(
                    currentCyclePayoutInCoins,
                    result.payoutInCoins());

            if (!isFreeSpin) {

                paidSpins++;

                baseGamePaidInCoins = Math.addExact(
                        baseGamePaidInCoins,
                        result.payoutInCoins());
            }

            if (result.outcome().isWin()) {
                winningSpins++;
            }

            /*
             * Keep the full result for the single largest
             * individual-spin payout seen in the simulation.
             */
            if (result.payoutInCoins() > largestPayoutInCoins) {

                largestPayoutInCoins =
                        result.payoutInCoins();

                largestPayoutResult =
                        result;

                largestPayoutSpinNumber =
                        totalSpins;
            }

            /*
             * If there are no free spins remaining,
             * this paid-spin cycle has finished.
             *
             * A cycle is:
             *
             * paid spin
             * + any free spins triggered
             * + any retriggered free spins
             */
            if (!gameState.hasFreeSpins()) {

                largestCyclePayoutInCoins = Math.max(
                        largestCyclePayoutInCoins,
                        currentCyclePayoutInCoins);

                double payoutMultiple =
                        (double) currentCyclePayoutInCoins
                                / betInCoins;

                PayoutBucket bucket =
                        PayoutBucket.from(payoutMultiple);

                payoutDistribution.merge(
                        bucket,
                        1L,
                        Long::sum);

                currentCyclePayoutInCoins = 0;
            }
        }

        long freeSpins =
                totalSpins - paidSpins;

        long freeSpinPaidInCoins =
                totalPaidInCoins
                        - baseGamePaidInCoins;

        /*
         * Print the largest individual spin once,
         * after the simulation is complete.
         */
        if (largestPayoutResult != null) {

            System.out.println();
            System.out.println("=== LARGEST PAYOUT SPIN ===");
            System.out.println(
                    "Spin number: "
                            + largestPayoutSpinNumber);

            System.out.println(
                    "Payout: "
                            + largestPayoutInCoins
                            + " coins");

            System.out.println(
                    "Grid: "
                            + largestPayoutResult
                            .outcome()
                            .grid());

            System.out.println(
                    "Wins: "
                            + largestPayoutResult
                            .outcome()
                            .wins());

            System.out.println(
                    "Scatter: "
                            + largestPayoutResult
                            .outcome()
                            .scatterResult());

            System.out.println(
                    "Free spin status: "
                            + largestPayoutResult
                            .freeSpinStatus());

            System.out.println("==========================");
            System.out.println();
        }

        return new SimulationResult(
                totalSpins,
                paidSpins,
                freeSpins,
                totalSpentInCoins,
                totalPaidInCoins,
                baseGamePaidInCoins,
                freeSpinPaidInCoins,
                winningSpins,
                largestPayoutInCoins,
                largestCyclePayoutInCoins,
                longestFreeSpinRun,
                payoutDistribution);
    }
}