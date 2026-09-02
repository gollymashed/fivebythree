export type PayoutBucket =
    | "ZERO"
    | "UP_TO_0_2X"
    | "UP_TO_0_5X"
    | "UP_TO_1X"
    | "UP_TO_2X"
    | "UP_TO_5X"
    | "UP_TO_10X"
    | "UP_TO_50X"
    | "UP_TO_100X"
    | "UP_TO_500X"
    | "OVER_500X";

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