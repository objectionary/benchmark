/*
 * SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 *
 * The code below was copied almost "as is" from this GitHub repository:
 * https://github.com/biboudis/clashofthelambdas
 */
package org.eolang.benchmark;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/**
 * Clash of the Lambdas (COTL).
 *
 * <p>The code was taken from the
 * <a href="https://github.com/biboudis/clashofthelambdas">biboudis/clashofthelambdas</a>
 * repository, which is a benchmark suite. The original code is licensed
 * under the Apache License, Version 2.0.</p>
 *
 * <p>We expect that the usage of Stream API in this class will be replaced
 * by imperative alternatives, thus making it faster.</p>
 *
 * @see <a href="https://github.com/biboudis/clashofthelambdas">biboudis/clashofthelambdas</a>
 * @since 0.2
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 10, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class Cart {

    public static final int N = 10_000_000;

    private static final long[] LOW = IntStream.range(0, 10).mapToLong(i -> i).toArray();

    private static final long[] HIGH = IntStream.range(0, N).mapToLong(i -> i).toArray();

    @Benchmark
    public long loop() {
        long cart = 0;
        for (int d = 0; d < HIGH.length; d++) {
            for (int dp = 0; dp < LOW.length; dp++) {
                cart += HIGH[d] * LOW[dp];
            }
        }
        return cart;
    }

    @Benchmark
    public long stream() {
        long cart = LongStream.of(HIGH)
            .flatMap(d -> LongStream.of(LOW).map(dP -> dP * d))
            .sum();
        return cart;
    }
}
