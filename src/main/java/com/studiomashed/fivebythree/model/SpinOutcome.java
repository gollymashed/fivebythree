package com.studiomashed.fivebythree.model;

import java.util.List;

public record SpinOutcome(
        SpinGrid grid,
        List<Win> wins) {
    public SpinOutcome {
        if (grid == null) {
            throw new IllegalArgumentException(
                    "Spin grid cannot be null");
        }

        if (wins == null) {
            throw new IllegalArgumentException(
                    "Wins cannot be null");
        }

        wins = List.copyOf(wins);
    }

    public boolean isWin() {
        return !wins.isEmpty();
    }
}