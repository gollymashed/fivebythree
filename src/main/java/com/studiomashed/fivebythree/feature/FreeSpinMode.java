package com.studiomashed.fivebythree.feature;

public enum FreeSpinMode {

    RESPIN_PER_WILD(1),
    SYMBOL_MULTIPLIERS(2),
    STICKY_WILDS(3);

    private final int tier;

    FreeSpinMode(int tier) {
        this.tier = tier;
    }

    public int tier() {
        return tier;
    }

    public boolean isHigherThan(
            FreeSpinMode other
    ) {
        return tier > other.tier;
    }
}