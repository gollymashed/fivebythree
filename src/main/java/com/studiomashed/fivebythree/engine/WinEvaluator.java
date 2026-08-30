package com.studiomashed.fivebythree.engine;

import java.util.ArrayList;
import java.util.List;

import com.studiomashed.fivebythree.model.Payline;
import com.studiomashed.fivebythree.model.Paytable;
import com.studiomashed.fivebythree.model.SpinGrid;
import com.studiomashed.fivebythree.model.Symbol;
import com.studiomashed.fivebythree.model.Win;

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
            List<Integer> symbols,
            Payline payline) {

        if (symbols.size() != 5) {
            throw new IllegalArgumentException(
                    "A payline must contain exactly 5 symbols");
        }

        int targetSymbol = Symbol.WILD;

        for (int symbol : symbols) {
            if (symbol != Symbol.WILD) {
                targetSymbol = symbol;
                break;
            }
        }

        boolean allMatchTarget = true;

        for (int symbol : symbols) {
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