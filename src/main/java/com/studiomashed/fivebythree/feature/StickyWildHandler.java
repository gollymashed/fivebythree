package com.studiomashed.fivebythree.feature;

import com.studiomashed.fivebythree.model.ReelWindow;
import com.studiomashed.fivebythree.model.SpinGrid;
import com.studiomashed.fivebythree.model.Symbol;

import java.util.ArrayList;
import java.util.List;

public final class StickyWildHandler {

    public SpinGrid apply(
            SpinGrid newGrid,
            SpinGrid previousGrid
    ) {
        List<ReelWindow> updatedReels = new ArrayList<>();

        for (int reelIndex = 0;
             reelIndex < newGrid.reels().size();
             reelIndex++) {

            List<Symbol> symbols = new ArrayList<>(
                    newGrid.reels().get(reelIndex).symbols()
            );

            ReelWindow previousReel =
                    previousGrid.reels().get(reelIndex);

            for (int rowIndex = 0;
                 rowIndex < symbols.size();
                 rowIndex++) {

                if (previousReel.symbolAt(rowIndex) == Symbol.WILD) {
                    symbols.set(rowIndex, Symbol.WILD);
                }
            }

            updatedReels.add(new ReelWindow(symbols));
        }

        return new SpinGrid(updatedReels);
    }
}
