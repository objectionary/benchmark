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
import java.util.stream.Stream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/**
 * MegamorphicMaps benchmark.
 *
 * <p>The code was taken from the
 * <a href="https://dl.acm.org/doi/abs/10.1145/3428236">this paper</a>.</p>
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
public class Megamophic {

    public static final int N = 10_000_000;

    static long[] v = IntStream.range(0, N).mapToLong(i -> i % 1000).toArray();

    @Benchmark
    public long loop() {
        long acc = 0;
        for (int i = 0; i < v.length; i++)
            acc += v[i] * 1 * 2 * 3 * 4 * 5 * 6 * 7;
        return acc;
    }

    @Benchmark
    public long stream() {
        return LongStream.of(v)
            .map(d -> d * 1)
            .map(d -> d * 2)
            .map(d -> d * 3)
            .map(d -> d * 4)
            .map(d -> d * 5)
            .map(d -> d * 6)
            .map(d -> d * 7)
            .sum();
    }
}
