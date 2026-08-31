package com.studiomashed.fivebythree.model;

public record SpinResult(
        long amountChargedInPence,
        long totalBetInPence,
        long payoutInPence,
        SpinOutcome outcome) {
    public SpinResult {
        if (payoutInPence < 0) {
            throw new IllegalArgumentException(
                    "Payout cannot be negative");
        }
    }
}