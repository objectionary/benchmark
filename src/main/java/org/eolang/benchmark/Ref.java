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
public class Ref {

    public static class Rec {
        public int num;
        public Rec(int num) {
            this.num = num;
        }
    }

    public static final int N = 10_000_000;

    static Rec[] recs = IntStream.range(0, N).mapToObj(n -> new Rec(n)).toArray(size -> new Rec[size]);

    @Benchmark
    public long loop() {
        long count = 0;
        for (int i = 0; i < recs.length; i++) {
            if (recs[i].num % 5 == 0 && recs[i].num % 7 == 0) {
                count++;
            }
        }
        return count;
    }

    @Benchmark
    public long stream() {
        long length = Stream.of(recs)
            .filter(box -> box.num % 5 == 0)
            .filter(box -> box.num % 7 == 0)
            .count();
        return length;
    }
}
