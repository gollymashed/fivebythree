package com.studiomashed.fivebythree.model;

import com.studiomashed.fivebythree.feature.FreeSpinStatus;

public record SpinResult(
        long amountChargedInCoins,
        long totalBetInCoins,
        long payoutInCoins,
        SpinOutcome outcome,
        FreeSpinStatus freeSpinStatus) {
    public SpinResult {
        if (payoutInCoins < 0) {
            throw new IllegalArgumentException(
                    "Payout cannot be negative");
        }
    }
}