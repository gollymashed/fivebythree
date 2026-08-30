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

        return payouts.get(symbol)[matchCount];
    }
}