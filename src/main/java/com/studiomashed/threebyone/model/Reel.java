package com.studiomashed.threebyone.model;

import java.util.List;

public record Reel(List<Symbol> symbols) {

    public Reel {
        symbols = List.copyOf(symbols);

        if (symbols.isEmpty()) {
            throw new IllegalArgumentException("Reel cannot be empty");
        }
    }

    public Symbol symbolAt(int position) {
        return symbols.get(position);
    }

    public int size() {
        return symbols.size();
    }
}