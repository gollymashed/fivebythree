package com.studiomashed.threebyone.model;

import java.util.List;

public record SpinOutcome(
        List<Symbol> symbols,
        int payoutMultiplier) {
    public SpinOutcome {
        symbols = List.copyOf(symbols);

        if (symbols.size() != 3) {
            throw new IllegalArgumentException(
                    "A 3x1 spin must contain exactly 3 symbols");
        }

        if (payoutMultiplier < 0) {
            throw new IllegalArgumentException(
                    "Payout multiplier cannot be negative");
        }
    }

    public boolean isWin() {
        return payoutMultiplier > 0;
    }
}