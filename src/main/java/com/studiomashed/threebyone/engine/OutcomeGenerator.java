package com.studiomashed.threebyone.engine;

import com.studiomashed.threebyone.model.Reel;
import com.studiomashed.threebyone.model.Symbol;
import com.studiomashed.threebyone.rng.RandomNumberGenerator;

import java.util.List;

public final class OutcomeGenerator {

    private final RandomNumberGenerator rng;

    public OutcomeGenerator(RandomNumberGenerator rng) {
        this.rng = rng;
    }

    public List<Symbol> generate(List<Reel> reels) {
        return reels.stream()
                .map(this::selectSymbol)
                .toList();
    }

    private Symbol selectSymbol(Reel reel) {
        int position = rng.nextInt(reel.size());
        return reel.symbolAt(position);
    }
}