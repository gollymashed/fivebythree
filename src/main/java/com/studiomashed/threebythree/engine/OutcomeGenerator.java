package com.studiomashed.threebyone.engine;

import com.studiomashed.threebyone.model.Reel;
import com.studiomashed.threebyone.model.Symbol;
import com.studiomashed.threebyone.rng.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;

public final class OutcomeGenerator {

    private final RandomNumberGenerator rng;

    public OutcomeGenerator(RandomNumberGenerator rng) {
        this.rng = rng;
    }

    public List<Symbol> generate(List<Reel> reels) {
        List<Symbol> symbols = new ArrayList<>();

        for (Reel reel : reels) {
            Symbol symbol = selectSymbol(reel);
            symbols.add(symbol);
        }

        return symbols;
    }

    private Symbol selectSymbol(Reel reel) {
        int position = rng.nextInt(reel.size());
        return reel.symbolAt(position);
    }
}