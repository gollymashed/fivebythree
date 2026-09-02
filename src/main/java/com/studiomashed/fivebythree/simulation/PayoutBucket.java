package com.studiomashed.fivebythree.simulation;

public enum PayoutBucket {
    ZERO,
    UP_TO_0_2X,
    UP_TO_0_5X,
    UP_TO_1X,
    UP_TO_2X,
    UP_TO_5X,
    UP_TO_10X,
    UP_TO_50X,
    UP_TO_100X,
    UP_TO_500X,
    OVER_500X;

    public static PayoutBucket from(double payoutMultiple) {
        if (payoutMultiple == 0) return ZERO;
        if (payoutMultiple <= 0.2) return UP_TO_0_2X;
        if (payoutMultiple <= 0.5) return UP_TO_0_5X;
        if (payoutMultiple <= 1.0) return UP_TO_1X;
        if (payoutMultiple <= 2.0) return UP_TO_2X;
        if (payoutMultiple <= 5.0) return UP_TO_5X;
        if (payoutMultiple <= 10.0) return UP_TO_10X;
        if (payoutMultiple <= 50.0) return UP_TO_50X;
        if (payoutMultiple <= 100.0) return UP_TO_100X;
        if (payoutMultiple <= 500.0) return UP_TO_500X;

        return OVER_500X;
    }
}