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
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class Cotl {

    public static class Ref {
        public int num;
        public Ref(int num) {
            this.num = num;
        }
    }

    public static final int N = 10_000_000;

    static long[] v = IntStream.range(0, N).mapToLong(i -> i % 1000).toArray();

    static long[] valuesLo = IntStream.range(0, 10).mapToLong(i -> i).toArray();

    static long[] valuesHi = IntStream.range(0, N).mapToLong(i -> i).toArray();

    static Cotl.Ref[] refs = IntStream.range(0, N).mapToObj(n -> new Cotl.Ref(n)).toArray(size -> new Cotl.Ref[size]);

    @Benchmark
    public long sumBaseline() {
        long acc = 0;
        for (int i = 0; i < v.length; i++) {
            acc += v[i];
        }
        return acc;
    }

    @Benchmark
    public long sumOfSquaresBaseline() {
        long acc = 0;
        for (int i = 0; i < v.length; i++) {
            acc += v[i] * v[i];
        }
        return acc;
    }

    @Benchmark
    public long cartBaseline() {
        long cart = 0;
        for (int d = 0; d < valuesHi.length; d++) {
            for (int dp = 0; dp < valuesLo.length; dp++) {
                cart += valuesHi[d] * valuesLo[dp];
            }
        }
        return cart;
    }

    @Benchmark
    public long sumOfSquaresEvenBaseline() {
        long acc = 0;
        for (int i = 0; i < v.length; i++) {
            if (v[i] % 2 == 0) {
                acc += v[i] * v[i];
            }
        }
        return acc;
    }

    @Benchmark
    public long sumSeq() {
        long sum = LongStream.of(v).sum();
        return sum;
    }

    @Benchmark
    public long sumPar() {
        long sum = LongStream.of(v).parallel().sum();
        return sum;
    }

    @Benchmark
    public long sumOfSquaresSeq() {
        long sum = LongStream.of(v)
            .map(d -> d * d)
            .sum();
        return sum;
    }

    @Benchmark
    public long sumOfSquaresPar() {
        long sum = LongStream.of(v)
            .parallel()
            .map(d -> d * d)
            .sum();
        return sum;
    }

    @Benchmark
    public long cartSeq() {
        long cart = LongStream.of(valuesHi)
            .flatMap(d -> LongStream.of(valuesLo).map(dP -> dP * d))
            .sum();
        return cart;
    }

    @Benchmark
    public long cartPar() {
        long cart = LongStream.of(valuesHi)
            .parallel()
            .flatMap(d -> LongStream.of(valuesLo).map(dP -> dP * d))
            .sum();
        return cart;
    }

    @Benchmark
    public long sumOfSquaresEvenSeq() {
        long sum = LongStream.of(v)
            .filter(x -> x % 2 == 0)
            .map(x -> x * x)
            .sum();
        return sum;
    }

    @Benchmark
    public long sumOfSquaresEvenPar() {
        long sum = LongStream.of(v)
            .parallel()
            .filter(x -> x % 2 == 0)
            .map(x -> x * x)
            .sum();
        return sum;
    }

    @Benchmark
    public long refBaseline() {
        long count = 0;
        for (int i = 0; i < refs.length; i++) {
            if (refs[i].num % 5 == 0 && refs[i].num % 7 == 0) {
                count++;
            }
        }
        return count;
    }

    @Benchmark
    public long refSeq() {
        long length = Stream.of(refs)
            .filter(box -> box.num % 5 == 0)
            .filter(box -> box.num % 7 == 0)
            .count();
        return length;
    }

    @Benchmark
    public long refPar() {
        long length = Stream.of(refs)
            .parallel()
            .filter(box -> box.num % 5 == 0)
            .filter(box -> box.num % 7 == 0)
            .count();
        return length;
    }
}
