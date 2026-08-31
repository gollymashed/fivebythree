package com.studiomashed.fivebythree.simulation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
        "hitFrequencyPercentage"
})
public record SimulationResult(
        long totalSpins,
        long paidSpins,
        long freeSpins,
        @JsonIgnore
        long totalStakedInPence,
        @JsonIgnore
        long totalPaidInPence,
        @JsonIgnore
        long baseGamePaidInPence,
        @JsonIgnore
        long freeSpinPaidInPence,
        long winningSpins,
        @JsonIgnore
        long largestPayoutInPence,
        @JsonIgnore
        long largestCyclePayoutInPence,
        long longestFreeSpinRun) {

    @JsonProperty
    public String rtpPercentage() {
        if (totalStakedInPence == 0) {
            return "0.00%";
        }

        double rtp =
                (double) totalPaidInPence
                        / totalStakedInPence
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
        return formatCurrency(totalStakedInPence);
    }

    @JsonProperty
    public String totalPaid() {
        return formatCurrency(totalPaidInPence);
    }

    @JsonProperty
    public String baseGamePaid() {
        return formatCurrency(baseGamePaidInPence);
    }

    @JsonProperty
    public String freeSpinPaid() {
        return formatCurrency(freeSpinPaidInPence);
    }

    @JsonProperty
    public String largestPayout() {
        return formatCurrency(largestPayoutInPence);
    }

    @JsonProperty
    public String largestCyclePayout() {
        return formatCurrency(largestCyclePayoutInPence);
    }

    private static String formatCurrency(long pence) {
        return String.format("$%,.2f", pence / 100.0);
    }
}