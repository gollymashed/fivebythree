export type PayoutBucket =
    | "ZERO"
    | "OVER_0_TO_0_25X"
    | "OVER_0_25_TO_0_5X"
    | "OVER_0_5_TO_1X"
    | "OVER_1_TO_2X"
    | "OVER_2_TO_4X"
    | "OVER_4_TO_8X"
    | "OVER_8_TO_16X"
    | "OVER_16_TO_32X"
    | "OVER_32_TO_64X"
    | "OVER_64_TO_128X"
    | "OVER_128_TO_256X"
    | "OVER_256_TO_512X"
    | "OVER_512_TO_1024X"
    | "OVER_1024_TO_2048X"
    | "OVER_2048_TO_4096X"
    | "OVER_4096X";

export interface SimulationResult {
    totalSpins: number;
    paidSpins: number;
    freeSpins: number;

    totalStaked: string;
    totalPaid: string;
    baseGamePaid: string;
    freeSpinPaid: string;

    winningSpins: number;

    largestPayout: string;
    largestCyclePayout: string;

    longestFreeSpinRun: number;

    rtpPercentage: string;
    hitFrequencyPercentage: string;

    payoutDistribution: Record<PayoutBucket, number>;
}