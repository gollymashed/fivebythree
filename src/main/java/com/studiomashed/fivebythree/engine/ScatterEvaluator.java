package com.studiomashed.fivebythree.engine;

import com.studiomashed.fivebythree.model.*;

public class ScatterEvaluator {

    private final ScatterPaytable scatterPaytable;

    public ScatterEvaluator(ScatterPaytable scatterPaytable) {
        this.scatterPaytable = scatterPaytable;
    }

    public ScatterResult evaluate(SpinGrid grid) {
        int scatterCount = scatterCount(grid);

        int payoutMultiplier =
                scatterPaytable.payoutMultiplierFor(scatterCount);

        int freeSpins =
                scatterPaytable.freeSpinsFor(scatterCount);

        return new ScatterResult(
                scatterCount,
                payoutMultiplier,
                freeSpins
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
