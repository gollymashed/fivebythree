package com.studiomashed.fivebythree.model;

import com.studiomashed.fivebythree.feature.FreeSpinMode;

public final class ScatterPaytable {

    private final int[] payoutMultipliers;
    private final int[] freeSpins;
    private final FreeSpinMode[] freeSpinModes;

    public ScatterPaytable(
            int[] payoutMultipliers,
            int[] freeSpins,
            FreeSpinMode[] freeSpinModes) {

        this.payoutMultipliers = payoutMultipliers.clone();
        this.freeSpins = freeSpins.clone();
        this.freeSpinModes = freeSpinModes.clone();
    }

    public int payoutMultiplierFor(int scatterCount) {
        return payoutMultipliers[scatterCount];
    }

    public int freeSpinsFor(int scatterCount) {
        return freeSpins[scatterCount];
    }

    public FreeSpinMode freeSpinModeFor(int scatterCount) {
        return freeSpinModes[scatterCount];
    }
}