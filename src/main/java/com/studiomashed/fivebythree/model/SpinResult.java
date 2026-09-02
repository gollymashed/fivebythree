package com.studiomashed.fivebythree.model;

public record SpinResult(
        long amountChargedInCoins,
        long totalBetInCoins,
        long payoutInCoins,
        SpinOutcome outcome) {
    public SpinResult {
        if (payoutInCoins < 0) {
            throw new IllegalArgumentException(
                    "Payout cannot be negative");
        }
    }
}