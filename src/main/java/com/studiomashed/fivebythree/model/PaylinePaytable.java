package com.studiomashed.fivebythree.model;

import java.util.Map;

public final class PaylinePaytable {

    private final Map<Symbol, int[]> payouts;

    public PaylinePaytable(Map<Symbol, int[]> payouts) {
        this.payouts = payouts;
    }

    public int payoutMultiplierFor(
            Symbol symbol,
            int matchCount) {

        int[] multipliers = payouts.get(symbol);

        if (multipliers == null) {
            return 0;
        }

        return multipliers[matchCount];
    }
}