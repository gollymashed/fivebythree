package com.studiomashed.fivebythree.model;

public final class GameState {

    private int freeSpinsRemaining;
    private long freeSpinBetInPence;

    public int freeSpinsRemaining() {
        return freeSpinsRemaining;
    }

    public long freeSpinBetInPence() {
        return freeSpinBetInPence;
    }

    public boolean hasFreeSpins() {
        return freeSpinsRemaining > 0;
    }

    public void awardFreeSpins(
            int freeSpins,
            long betInPence) {
        if (freeSpins <= 0) {
            return;
        }

        if (!hasFreeSpins()) {
            freeSpinBetInPence = betInPence;
        }

        freeSpinsRemaining += freeSpins;
    }

    public void consumeFreeSpin() {
        if (!hasFreeSpins()) {
            throw new IllegalStateException(
                    "No free spins remaining");
        }

        freeSpinsRemaining--;

        if (freeSpinsRemaining == 0) {
            freeSpinBetInPence = 0;
        }
    }
}
