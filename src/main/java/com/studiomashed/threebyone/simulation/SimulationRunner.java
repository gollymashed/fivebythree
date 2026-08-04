package com.studiomashed.threebyone.simulation;

import com.studiomashed.threebyone.engine.SlotEngine;
import com.studiomashed.threebyone.model.SpinResult;

public final class SimulationRunner {

    private final SlotEngine slotEngine;

    public SimulationRunner(SlotEngine slotEngine) {
        this.slotEngine = slotEngine;
    }

    public SimulationResult run(
            long numberOfSpins,
            long stakeInPence) {
        if (numberOfSpins <= 0) {
            throw new IllegalArgumentException(
                    "Number of spins must be positive");
        }

        if (stakeInPence <= 0) {
            throw new IllegalArgumentException(
                    "Stake must be positive");
        }

        long totalStakedInPence = 0;
        long totalPaidInPence = 0;
        long winningSpins = 0;
        long largestPayoutInPence = 0;

        for (long spin = 0; spin < numberOfSpins; spin++) {
            SpinResult result = slotEngine.spin(stakeInPence);

            totalStakedInPence = Math.addExact(
                    totalStakedInPence,
                    result.stakeInPence());

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