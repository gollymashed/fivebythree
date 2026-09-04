package com.studiomashed.fivebythree.state;

import com.studiomashed.fivebythree.feature.FreeSpinMode;

public final class FreeSpinState {

    private int spinsRemaining;
    private FreeSpinMode mode;

    public FreeSpinState(
            int spinsRemaining,
            FreeSpinMode mode
    ) {
        this.spinsRemaining = spinsRemaining;
        this.mode = mode;
    }

    public int spinsRemaining() {
        return spinsRemaining;
    }

    public FreeSpinMode mode() {
        return mode;
    }

    public void award(
            int spins,
            FreeSpinMode awardedMode
    ) {
        spinsRemaining += spins;

        if (awardedMode != null && awardedMode.isHigherThan(mode)) {
            mode = awardedMode;
        }
    }

    public void consume() {
        if (spinsRemaining <= 0) {
            throw new IllegalStateException(
                    "No free spins remaining"
            );
        }

        spinsRemaining--;
    }

    public boolean isComplete() {
        return spinsRemaining == 0;
    }
}