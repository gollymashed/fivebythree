package com.studiomashed.threebythree.engine;

import java.util.ArrayList;
import java.util.List;

import com.studiomashed.threebythree.model.Reel;
import com.studiomashed.threebythree.model.ReelWindow;
import com.studiomashed.threebythree.model.SpinGrid;
import com.studiomashed.threebythree.rng.RandomNumberGenerator;

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