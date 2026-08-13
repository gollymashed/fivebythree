package com.studiomashed.threebythree.engine;

import java.util.ArrayList;
import java.util.List;

import com.studiomashed.threebythree.model.Payline;
import com.studiomashed.threebythree.model.Paytable;
import com.studiomashed.threebythree.model.SpinGrid;
import com.studiomashed.threebythree.model.Symbol;
import com.studiomashed.threebythree.model.Win;

public final class WinEvaluator {

    private final Paytable paytable;

    public WinEvaluator(Paytable paytable) {
        this.paytable = paytable;
    }

    public List<Win> evaluate(SpinGrid grid,
            List<Payline> paylines) {

        List<Win> wins = new ArrayList<>();

        for (Payline payline : paylines) {
            List<Symbol> symbols = grid.symbolsFor(payline);

            Win win = evaluatePayline(symbols, payline);

            if (win != null) {
                wins.add(win);
            }
        }

        return wins;
    }

    private Win evaluatePayline(
            List<Symbol> symbols,
            Payline payline) {

        if (symbols.size() != 3) {
            throw new IllegalArgumentException(
                    "A payline must contain exactly 3 symbols");
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

        if (allMatchTarget) {
            int multiplier = paytable.payoutMultiplierFor(targetSymbol);

            if (multiplier > 0) {
                Win win = new Win(
                        payline.id(),
                        targetSymbol,
                        multiplier);

                return win;
            }
        }

        return null;
    }
}