package com.studiomashed.threebythree.model;

import java.util.List;

public record Payline(
        int id,
        List<Integer> rows) {
    public Payline {
        rows = List.copyOf(rows);

        if (rows.size() != 3) {
            throw new IllegalArgumentException(
                    "A 3x3 payline must contain exactly 3 rows");
        }

        for (int row : rows) {
            if (row < 0 || row > 2) {
                throw new IllegalArgumentException(
                        "Payline rows must be between 0 and 2");
            }
        }
    }

    public int rowAt(int reel) {
        return rows.get(reel);
    }
}
