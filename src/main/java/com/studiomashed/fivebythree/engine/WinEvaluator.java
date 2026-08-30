package com.studiomashed.fivebythree.engine;

import java.util.ArrayList;
import java.util.List;

import com.studiomashed.fivebythree.model.Payline;
import com.studiomashed.fivebythree.model.PaylinePaytable;
import com.studiomashed.fivebythree.model.SpinGrid;
import com.studiomashed.fivebythree.model.Symbol;
import com.studiomashed.fivebythree.model.Win;

public final class WinEvaluator {

    private final PaylinePaytable paylinePaytable;

    public WinEvaluator(PaylinePaytable paylinePaytable) {
        this.paylinePaytable = paylinePaytable;
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

        if (symbols.size() != 5) {
            throw new IllegalArgumentException(
                    "A payline must contain exactly 5 symbols");
        }

        Symbol targetSymbol = Symbol.WILD;

        for (Symbol symbol : symbols) {
            if (symbol != Symbol.WILD) {
                targetSymbol = symbol;
                break;
            }
        }

        int matchCount = 0;

        for (Symbol symbol : symbols) {
            if (symbol == targetSymbol || symbol == Symbol.WILD) {
                matchCount++;
            } else {
                break;
            }
        }

        int multiplier = paylinePaytable.payoutMultiplierFor(targetSymbol, matchCount);

        if (multiplier > 0) {
            return new Win(
                    payline.id(),
                    targetSymbol,
                    multiplier);
        }


        return null;
    }
}