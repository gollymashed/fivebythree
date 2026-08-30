package com.studiomashed.fivebythree.model;

public record SpinResult(
        long stakePerLineInPence,
        long totalStakeInPence,
        long payoutInPence,
        SpinOutcome outcome) {
    public SpinResult {
        if (stakePerLineInPence <= 0) {
            throw new IllegalArgumentException(
                    "Stake must be positive");
        }

        if (payoutInPence < 0) {
            throw new IllegalArgumentException(
                    "Payout cannot be negative");
        }
    }
}