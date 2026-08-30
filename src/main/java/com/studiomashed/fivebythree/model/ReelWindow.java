package com.studiomashed.fivebythree.model;

import java.util.List;

public record ReelWindow(List<Integer> symbols) {

    public ReelWindow {
        symbols = List.copyOf(symbols);

        if (symbols.size() != 3) {
            throw new IllegalArgumentException(
                    "Reel window must contain exactly 3 symbols");
        }
    }

    public int symbolAt(int row) {
        return symbols.get(row);
    }
}