package com.studiomashed.fivebythree.model;

import com.studiomashed.fivebythree.feature.FreeSpinMode;

public record ScatterResult(
        int count,
        int payoutMultiplier,
        int freeSpins,
        FreeSpinMode freeSpinMode
) {
}