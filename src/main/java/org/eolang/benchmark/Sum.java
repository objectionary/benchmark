/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
package org.eolang.benchmark;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.LongUnaryOperator;
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
 * Sum of squares.
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
public class Sum {

    private static final long[] VALUES = IntStream.range(0, 100_000_000)
        .mapToLong(i -> (long) (i % 1000))
        .toArray();

    @Benchmark
    public long plain() {
        final LongUnaryOperator iface = num -> num * num;
        long acc = 0L;
        for (int idx = 0; idx < Sum.VALUES.length ; idx++) {
            acc += iface.applyAsLong(Sum.VALUES[idx]);
        }
        return acc;
    }

    @Benchmark
    public long streams() {
        return LongStream.of(Sum.VALUES)
            .map(num -> num * num)
            .sum();
    }
}
