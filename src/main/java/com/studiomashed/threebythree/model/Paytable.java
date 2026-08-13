package com.studiomashed.threebythree.model;

import java.util.EnumMap;
import java.util.Map;

public final class Paytable {

    private final Map<Symbol, Integer> payouts;

    public Paytable(Map<Symbol, Integer> payouts) {
        this.payouts = new EnumMap<>(payouts);
    }

    public int payoutMultiplierFor(Symbol symbol) {
        return payouts.getOrDefault(symbol, 0);
    }
}