/*
 * SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
package org.eolang.benchmark;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/**
 * Sum of even squares.
 *
 * <p>We expect that the usage of Stream API in this class will be replaced
 * by imperative alternatives, thus making it faster.</p>
 *
 * @since 0.2
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class Even {

    private static final long[] VALUES = IntStream.range(0, 10_000_000)
        .mapToLong(i -> (long) (i % 1000))
        .toArray();

    @Benchmark
    public long plain() {
        long acc = 0L;
        for (int idx = 0; idx < Even.VALUES.length; idx++) {
            if (idx % 2 == 0) {
                acc += Even.VALUES[idx] * Even.VALUES[idx];
            }
        }
        return acc;
    }

    @Benchmark
    public long streams() {
        return LongStream.of(Even.VALUES)
            .filter(x -> x % 2L == 0L)
            .map(x -> x * x)
            .sum();
    }
}
