package com.studiomashed.fivebythree.engine;

import java.util.ArrayList;
import java.util.List;

import com.studiomashed.fivebythree.model.Reel;
import com.studiomashed.fivebythree.model.ReelWindow;
import com.studiomashed.fivebythree.model.SpinGrid;
import com.studiomashed.fivebythree.rng.RandomNumberGenerator;

public final class OutcomeGenerator {

    private final RandomNumberGenerator rng;

    public OutcomeGenerator(RandomNumberGenerator rng) {
        this.rng = rng;
    }

    public SpinGrid generate(List<Reel> reels) {
        List<ReelWindow> windows = new ArrayList<>();

        for (Reel reel : reels) {
            int position = rng.nextInt(reel.size());

            ReelWindow window = reel.windowAt(position);

            windows.add(window);
        }

        return new SpinGrid(windows);
    }
}