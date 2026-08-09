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
        if (totalStakedInPence == 0) {
            return 0.0;
        }

        double rtp = (double) totalPaidInPence / totalStakedInPence;

        return rtp * 100.0;
    }

    @JsonProperty
    public double hitFrequencyPercentage() {
        if (spins == 0) {
            return 0.0;
        }

        double hitFrequency = (double) winningSpins / spins;

        return hitFrequency * 100.0;
    }
}