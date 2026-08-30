package com.studiomashed.fivebythree.model;

import java.util.List;

public record Reel(List<Integer> symbols) {

    public Reel {
        symbols = List.copyOf(symbols);

        if (symbols.isEmpty()) {
            throw new IllegalArgumentException("Reel cannot be empty");
        }
    }

    public int symbolAt(int position) {
        if (position < 0 || position >= size()) {
            throw new IndexOutOfBoundsException(
                    "Reel position out of range: " + position);
        }

        return symbols.get(position);
    }

    public int size() {
        return symbols.size();
    }

    public ReelWindow windowAt(int position) {
        if (position < 0 || position >= size()) {
            throw new IndexOutOfBoundsException(
                    "Reel position out of range: " + position);
        }

        List<Integer> reelWindow = List.of(
                symbols.get(Math.floorMod(position - 1, size())),
                symbols.get(position),
                symbols.get((position + 1) % size()));

        return new ReelWindow(reelWindow);
    }
}