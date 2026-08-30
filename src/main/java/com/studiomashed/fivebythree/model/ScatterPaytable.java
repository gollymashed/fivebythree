package com.studiomashed.fivebythree.model;

public final class ScatterPaytable {

    private final int[] payoutMultipliers;
    private final int[] freeSpins;

    public ScatterPaytable(
            int[] payoutMultipliers,
            int[] freeSpins) {

        this.payoutMultipliers = payoutMultipliers.clone();
        this.freeSpins = freeSpins.clone();
    }

    public int payoutMultiplierFor(int scatterCount) {
        return payoutMultipliers[scatterCount];
    }

    public int freeSpinsFor(int scatterCount) {
        return freeSpins[scatterCount];
    }
}