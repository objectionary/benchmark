/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2023-2024 Objectionary.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.eolang.benchmark;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.*;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.*;

/**
 * Bigger tests.
 *
 * We expect that the usage of Stream API in this class will be replaced
 * by imperative alternatives, thus making it faster.
 *
 * @since 0.2
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(2)
public class Big {

    private static final Object[] VALUES = LongStream.range(0L, 10_000_000L)
        .boxed()
        .map(x -> String.format("%04x", x))
        .toArray();

    @Benchmark
    public long plain() {
        long acc = 0L;
        for (int idx = 0; idx < Big.VALUES.length ; idx++) {
            final String str = ((String) Big.VALUES[idx]).trim();
            if (str.length() != 4) {
                continue;
            }
            acc += Long.parseLong(str, 16) + 1L;
        }
        return acc;
    }

    @Benchmark
    public long streams() {
        return Stream.of(Big.VALUES)
            .map(obj -> (String) obj)
            .map(String::trim)
            .filter(str -> str.length() == 4)
            .map(str -> Long.parseLong(str, 16) + 1L)
            .mapToLong(num -> num)
            .sum();
    }

}
