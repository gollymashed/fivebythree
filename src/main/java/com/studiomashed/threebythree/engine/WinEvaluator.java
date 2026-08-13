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

        Symbol targetSymbol = Symbol.WILD;

        for (Symbol symbol : symbols) {
            if (symbol != Symbol.WILD) {
                targetSymbol = symbol;
                break;
            }
        }

        boolean allMatchTarget = true;

        for (Symbol symbol : symbols) {
            if (symbol != targetSymbol && symbol != Symbol.WILD) {
                allMatchTarget = false;
                break;
            }
        }

        if (!allMatchTarget) {
            return 0;
        }

        return paytable.payoutMultiplierFor(targetSymbol);
    }
}