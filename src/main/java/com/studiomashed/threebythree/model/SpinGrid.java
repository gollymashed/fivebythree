package com.studiomashed.threebythree.model;

import java.util.ArrayList;
import java.util.List;

public record SpinGrid(List<ReelWindow> reels) {

    public SpinGrid {
        reels = List.copyOf(reels);

        if (reels.size() != 3) {
            throw new IllegalArgumentException(
                    "A 3x3 grid must contain exactly 3 reels");
        }
    }

    public List<Symbol> symbolsFor(Payline payline) {
        List<Symbol> paylineSymbols = new ArrayList<>();

        for (int reel = 0; reel < reels.size(); reel++) {
            int row = payline.rowAt(reel);
            Symbol symbol = reels.get(reel).symbolAt(row);

            paylineSymbols.add(symbol);
        }

        return paylineSymbols;
    }
}
