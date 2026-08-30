package com.studiomashed.fivebythree.model;

import java.util.List;

public record Payline(
        int id,
        List<Integer> rows) {
    public Payline {
        rows = List.copyOf(rows);

        if (rows.size() != 5) {
            throw new IllegalArgumentException(
                    "A 5x3 payline must contain exactly 5 rows");
        }

        for (int row : rows) {
            if (row < 0 || row > 4) {
                throw new IllegalArgumentException(
                        "Payline rows must be between 0 and 4");
            }
        }
    }

    public int rowAt(int reel) {
        return rows.get(reel);
    }
}
