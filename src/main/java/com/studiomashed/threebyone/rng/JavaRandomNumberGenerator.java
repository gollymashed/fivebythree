package com.studiomashed.threebyone.rng;

import java.util.random.RandomGenerator;

public final class JavaRandomNumberGenerator
        implements RandomNumberGenerator {

    private final RandomGenerator randomGenerator;

    public JavaRandomNumberGenerator() {
        this(RandomGenerator.getDefault());
    }

    public JavaRandomNumberGenerator(
            RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public int nextInt(int bound) {
        return randomGenerator.nextInt(bound);
    }
}