package com.studiomashed.threebythree.simulation;

import com.studiomashed.threebythree.engine.SlotEngine;
import com.studiomashed.threebythree.model.SpinResult;

public final class SimulationRunner {

    private final SlotEngine slotEngine;

    public SimulationRunner(SlotEngine slotEngine) {
        this.slotEngine = slotEngine;
    }

    public SimulationResult run(
            long numberOfSpins,
            long stakePerLineInPence,
            int numberOfPaylines) {

        if (numberOfSpins <= 0) {
            throw new IllegalArgumentException(
                    "Number of spins must be positive");
        }

        if (stakePerLineInPence <= 0) {
            throw new IllegalArgumentException(
                    "Stake per line must be positive");
        }

        long totalStakedInPence = 0;
        long totalPaidInPence = 0;
        long winningSpins = 0;
        long largestPayoutInPence = 0;

        for (long spin = 0; spin < numberOfSpins; spin++) {

            SpinResult result = slotEngine.spin(
                    stakePerLineInPence,
                    numberOfPaylines);

            totalStakedInPence = Math.addExact(
                    totalStakedInPence,
                    result.totalStakeInPence());

            totalPaidInPence = Math.addExact(
                    totalPaidInPence,
                    result.payoutInPence());

            if (result.outcome().isWin()) {
                winningSpins++;
            }

            largestPayoutInPence = Math.max(
                    largestPayoutInPence,
                    result.payoutInPence());
        }

        return new SimulationResult(
                numberOfSpins,
                totalStakedInPence,
                totalPaidInPence,
                winningSpins,
                largestPayoutInPence);
    }
}