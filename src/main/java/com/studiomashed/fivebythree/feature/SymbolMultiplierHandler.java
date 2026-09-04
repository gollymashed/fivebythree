package com.studiomashed.fivebythree.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.studiomashed.fivebythree.model.Symbol;
import com.studiomashed.fivebythree.model.Win;

public final class SymbolMultiplierHandler {

    private final Map<Symbol, Integer> multipliers;

    public SymbolMultiplierHandler(
            Map<Symbol, Integer> multipliers
    ) {
        this.multipliers = multipliers;
    }

    public List<Win> apply(List<Win> wins) {
        List<Win> updatedWins = new ArrayList<>();

        for (Win win : wins) {

            int multiplier = multipliers.getOrDefault(
                    win.symbol(),
                    1
            );

            updatedWins.add(new Win(
                    win.paylineId(),
                    win.symbol(),
                    win.payoutCoins() * multiplier
            ));
        }

        return updatedWins;
    }
}