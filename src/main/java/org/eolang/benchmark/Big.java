/*
 * SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
package org.eolang.benchmark;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;
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
 * Bigger tests.
 *
 * <p>We expect that the usage of Stream API in this class will be replaced
 * by imperative alternatives, thus making it faster.</p>
 *
 * @since 0.2
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(1)
public class Big {

    private static final long EXPECTED = 2249512L;

    private static final Long[] VALUES = LongStream.range(0L, 10_000_000L)
        .boxed()
        .toArray(Long[]::new);

    @Benchmark
    public long plain() throws IOException {
        long count = 0;
        for (int idx = 0; idx < Big.VALUES.length; idx++) {
            long num = Big.VALUES[idx] + 1;
            if (num % 13 == 0) {
                continue;
            }
            num = num * num / 17;
            if (num % 7 == 0) {
                count += 1;
            }
        }
        assert count == Big.EXPECTED;
        return count;
    }

    @Benchmark
    public long streams() {
       final long count = Stream.of(Big.VALUES)
           .map(num -> num + 1)
           .filter(num -> num % 13 != 0)
           .map(num -> num * num)
           .map(num -> num / 17)
           .filter(num -> num % 7 == 0)
           .count();
       assert count == Big.EXPECTED;
       return count;
    }

}
