package com.studiomashed.fivebythree.model;

import java.util.EnumMap;
import java.util.Map;

public final class Paytable {

    private final int[][] payouts;

    public Paytable(int[][] payouts) {
        this.payouts = payouts;
    }

    public int payoutMultiplierFor(
            int symbol,
            int matchCount) {

        return payouts[symbol][matchCount];
    }
}