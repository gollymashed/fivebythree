package com.studiomashed.threebyone.engine;

import com.studiomashed.threebyone.model.Paytable;
import com.studiomashed.threebyone.model.Symbol;

import java.util.List;

public final class WinEvaluator {

    private final Paytable paytable;

    public WinEvaluator(Paytable paytable) {
        this.paytable = paytable;
    }

    public int evaluate(List<Symbol> symbols) {
        if (symbols.size() != 3) {
            throw new IllegalArgumentException(
                    "A 3x1 result must contain exactly 3 symbols");
        }

        Symbol target = symbols.stream()
                .filter(symbol -> symbol != Symbol.WILD)
                .findFirst()
                .orElse(Symbol.WILD);

        boolean allMatch = symbols.stream()
                .allMatch(symbol -> symbol == target || symbol == Symbol.WILD);

        if (!allMatch) {
            return 0;
        }

        return paytable.payoutMultiplierFor(target);
    }
}