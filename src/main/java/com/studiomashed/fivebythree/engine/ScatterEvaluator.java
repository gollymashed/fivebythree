package com.studiomashed.fivebythree.engine;

import com.studiomashed.fivebythree.feature.FreeSpinMode;
import com.studiomashed.fivebythree.model.ReelWindow;
import com.studiomashed.fivebythree.model.ScatterPaytable;
import com.studiomashed.fivebythree.model.ScatterResult;
import com.studiomashed.fivebythree.model.SpinGrid;
import com.studiomashed.fivebythree.model.Symbol;

public final class ScatterEvaluator {

    private final ScatterPaytable scatterPaytable;

    public ScatterEvaluator(
            ScatterPaytable scatterPaytable
    ) {
        this.scatterPaytable = scatterPaytable;
    }

    public ScatterResult evaluate(SpinGrid grid) {
        int scatterCount = scatterCount(grid);

        int payoutMultiplier =
                scatterPaytable.payoutMultiplierFor(
                        scatterCount
                );

        int freeSpins =
                scatterPaytable.freeSpinsFor(
                        scatterCount
                );

        FreeSpinMode freeSpinMode =
                scatterPaytable.freeSpinModeFor(
                        scatterCount
                );

        return new ScatterResult(
                scatterCount,
                payoutMultiplier,
                freeSpins,
                freeSpinMode
        );
    }

    private int scatterCount(SpinGrid grid) {
        int scatterCount = 0;

        for (ReelWindow reel : grid.reels()) {
            for (Symbol symbol : reel.symbols()) {
                if (symbol == Symbol.SCATTER) {
                    scatterCount++;
                }
            }
        }

        return scatterCount;
    }
}