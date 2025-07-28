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
public class Sum {

    public static class Ref {
        public int num;
        public Ref(int num) {
            this.num = num;
        }
    }

    public static final int N = 100_000_000;

    static long[] v = IntStream.range(0, N).mapToLong(i -> i % 1000).toArray();

    @Benchmark
    public long loop() {
        long acc = 0;
        for (int i = 0; i < v.length; i++) {
            acc += v[i];
        }
        return acc;
    }

    @Benchmark
    public long stream() {
        long sum = LongStream.of(v).sum();
        return sum;
    }
}
