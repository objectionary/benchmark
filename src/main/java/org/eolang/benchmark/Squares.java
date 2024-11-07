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

import java.util.stream.*;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.*;

/**
 * Squares.
 *
 * We expect that the usage of Stream API in this class will be replaced
 * by imperative alternatives, thus making it faster.
 *
 * @since 0.2
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class Squares {

    private static final long[] VALUES = IntStream.range(0, 100_000_000)
        .mapToLong(i -> i % 1000)
        .toArray();

    @Benchmark
    public long sumOfSquares() {
        long acc = 0;
        for (int i =0 ; i < VALUES.length ; i++) {
            acc += VALUES[i] * VALUES[i];
        }
        return acc;
    }

//    @Benchmark
//    public long sumOfSquaresSeq() {
//        return LongStream.of(VALUES)
//            .map(d -> d * d)
//            .sum();
//    }
//
//    @Benchmark
//    public long sumOfSquaresPar() {
//        return LongStream.of(VALUES)
//            .parallel()
//            .map(d -> d * d)
//            .sum();
//    }
//
//    @Benchmark
//    public long sum() {
//        long acc = 0;
//        for (int i =0 ; i < VALUES.length ; i++) {
//            acc += VALUES[i];
//        }
//        return acc;
//    }
//
//    @Benchmark
//    public long sumSeq() {
//        return LongStream.of(VALUES).sum();
//    }
//
//    @Benchmark
//    public long sumPar() {
//        return LongStream.of(VALUES).parallel().sum();
//    }
//
//    @Benchmark
//    public long sumOfEvenSquares() {
//        long acc = 0;
//        for (int i =0 ; i < VALUES.length ; i++) {
//            if (i % 2 == 0) {
//                acc += VALUES[i] * VALUES[i];
//            }
//        }
//        return acc;
//    }
//
//    @Benchmark
//    public long sumOfEvenSquaresSeq() {
//        return LongStream.of(VALUES)
//            .filter(x -> x % 2 == 0)
//            .map(x -> x * x)
//            .sum();
//    }
//
//    @Benchmark
//    public long sumOfEvenSquaresPar() {
//        return LongStream.of(VALUES)
//            .parallel()
//            .filter(x -> x % 2 == 0)
//            .map(x -> x * x)
//            .sum();
//    }
}
