package com.studiomashed.fivebythree.state;

import com.studiomashed.fivebythree.feature.FreeSpinMode;
import com.studiomashed.fivebythree.model.SpinGrid;

public final class GameState {

    private long betInCoins;
    private SpinGrid lastGrid;
    private FreeSpinState freeSpinState;

    public long betInCoins() {
        return betInCoins;
    }

    public void setBetInCoins(long betInCoins) {
        if (hasFreeSpins()) {
            throw new IllegalStateException(
                    "Cannot change bet during free spins"
            );
        }

        this.betInCoins = betInCoins;
    }

    public SpinGrid lastGrid() {
        return lastGrid;
    }

    public void setLastGrid(SpinGrid grid) {
        this.lastGrid = grid;
    }

    public boolean hasFreeSpins() {
        return freeSpinState != null;
    }

    public FreeSpinState freeSpinState() {
        return freeSpinState;
    }

    public void startFreeSpins(
            int spins,
            FreeSpinMode mode
    ) {
        if (hasFreeSpins()) {
            throw new IllegalStateException(
                    "Free spins already active"
            );
        }

        freeSpinState = new FreeSpinState(spins, mode);
    }

    public void clearFreeSpins() {
        freeSpinState = null;
    }
}