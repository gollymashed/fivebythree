package com.studiomashed.threebyone.simulation;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SimulationResult(
        long spins,
        long totalStakedInPence,
        long totalPaidInPence,
        long winningSpins,
        long largestPayoutInPence) {
    @JsonProperty
    public double rtpPercentage() {
        return totalStakedInPence == 0
                ? 0.0
                : (double) totalPaidInPence
                        / totalStakedInPence
                        * 100.0;
    }

    @JsonProperty
    public double hitFrequencyPercentage() {
        return spins == 0
                ? 0.0
                : (double) winningSpins
                        / spins
                        * 100.0;
    }
}