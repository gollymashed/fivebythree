package com.studiomashed.fivebythree.simulation;

public enum PayoutBucket {

    ZERO,
    OVER_0_TO_0_25X,
    OVER_0_25_TO_0_5X,
    OVER_0_5_TO_1X,
    OVER_1_TO_2X,
    OVER_2_TO_4X,
    OVER_4_TO_8X,
    OVER_8_TO_16X,
    OVER_16_TO_32X,
    OVER_32_TO_64X,
    OVER_64_TO_128X,
    OVER_128_TO_256X,
    OVER_256_TO_512X,
    OVER_512_TO_1024X,
    OVER_1024_TO_2048X,
    OVER_2048_TO_4096X,
    OVER_4096X;

    public static PayoutBucket from(double payoutMultiple) {

        if (payoutMultiple < 0) {
            throw new IllegalArgumentException(
                    "Payout multiple cannot be negative");
        }

        if (payoutMultiple == 0) return ZERO;

        if (payoutMultiple <= 0.25) {
            return OVER_0_TO_0_25X;
        }

        if (payoutMultiple <= 0.5) {
            return OVER_0_25_TO_0_5X;
        }

        if (payoutMultiple <= 1.0) {
            return OVER_0_5_TO_1X;
        }

        if (payoutMultiple <= 2.0) {
            return OVER_1_TO_2X;
        }

        if (payoutMultiple <= 4.0) {
            return OVER_2_TO_4X;
        }

        if (payoutMultiple <= 8.0) {
            return OVER_4_TO_8X;
        }

        if (payoutMultiple <= 16.0) {
            return OVER_8_TO_16X;
        }

        if (payoutMultiple <= 32.0) {
            return OVER_16_TO_32X;
        }

        if (payoutMultiple <= 64.0) {
            return OVER_32_TO_64X;
        }

        if (payoutMultiple <= 128.0) {
            return OVER_64_TO_128X;
        }

        if (payoutMultiple <= 256.0) {
            return OVER_128_TO_256X;
        }

        if (payoutMultiple <= 512.0) {
            return OVER_256_TO_512X;
        }

        if (payoutMultiple <= 1024.0) {
            return OVER_512_TO_1024X;
        }

        if (payoutMultiple <= 2048.0) {
            return OVER_1024_TO_2048X;
        }

        if (payoutMultiple <= 4096.0) {
            return OVER_2048_TO_4096X;
        }

        return OVER_4096X;
    }
}