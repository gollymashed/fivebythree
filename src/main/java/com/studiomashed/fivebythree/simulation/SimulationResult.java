package com.studiomashed.fivebythree.simulation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Map;

@JsonPropertyOrder({
        "totalSpins",
        "paidSpins",
        "freeSpins",
        "winningSpins",
        "totalStaked",
        "totalPaid",
        "baseGamePaid",
        "freeSpinPaid",
        "largestPayout",
        "largestCyclePayout",
        "longestFreeSpinRun",
        "rtpPercentage",
        "hitFrequencyPercentage",
        "payoutDistribution"
})
public record SimulationResult(
        long totalSpins,
        long paidSpins,
        long freeSpins,
        @JsonIgnore
        long totalStakedInCoins,
        @JsonIgnore
        long totalPaidInCoins,
        @JsonIgnore
        long baseGamePaidInCoins,
        @JsonIgnore
        long freeSpinPaidInCoins,
        long winningSpins,
        @JsonIgnore
        long largestPayoutInCoins,
        @JsonIgnore
        long largestCyclePayoutInCoins,
        long longestFreeSpinRun,
        Map<PayoutBucket, Long> payoutDistribution) {

    @JsonProperty
    public String rtpPercentage() {
        if (totalStakedInCoins == 0) {
            return "0.00%";
        }

        double rtp =
                (double) totalPaidInCoins
                        / totalStakedInCoins
                        * 100.0;

        return String.format("%.2f%%", rtp);
    }

    @JsonProperty
    public String hitFrequencyPercentage() {
        if (totalSpins == 0) {
            return "0.00%";
        }

        double hitFrequency =
                (double) winningSpins
                        / totalSpins
                        * 100.0;

        return String.format("%.2f%%", hitFrequency);
    }

    @JsonProperty
    public String totalStaked() {
        return formatCoins(totalStakedInCoins);
    }

    @JsonProperty
    public String totalPaid() {
        return formatCoins(totalPaidInCoins);
    }

    @JsonProperty
    public String baseGamePaid() {
        return formatCoins(baseGamePaidInCoins);
    }

    @JsonProperty
    public String freeSpinPaid() {
        return formatCoins(freeSpinPaidInCoins);
    }

    @JsonProperty
    public String largestPayout() {
        return formatCoins(largestPayoutInCoins);
    }

    @JsonProperty
    public String largestCyclePayout() {
        return formatCoins(largestCyclePayoutInCoins);
    }

    private static String formatCoins(long coins) {
        return String.format("%,d coins", coins);
    }
}