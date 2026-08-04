package com.studiomashed.threebyone.model;

public record SpinResult(
        long stakeInPence,
        long payoutInPence,
        SpinOutcome outcome) {
    public SpinResult {
        if (stakeInPence <= 0) {
            throw new IllegalArgumentException(
                    "Stake must be positive");
        }

        if (payoutInPence < 0) {
            throw new IllegalArgumentException(
                    "Payout cannot be negative");
        }
    }
}