/*
 * SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
package org.eolang.benchmark;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collector;
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
@Fork(2)
public class Big {

    private static final Object[] VALUES = LongStream.range(0L, 10_000_000L)
        .boxed()
        .map(x -> String.format("%04x", x))
        .toArray();

    @Benchmark
    public long plain() {
        long acc = 0L;
        for (int idx = 0; idx < Big.VALUES.length; idx++) {
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

    @Benchmark
    public long prefused() {
        return Stream.of(Big.VALUES)
            .mapMulti(
                (BiConsumer<Object, Consumer<String>>) (item, then) -> {
                    then.accept(step1(item));
                }
            )
            .mapMulti(
                (BiConsumer<String, Consumer<String>>) (item, then) -> {
                    then.accept(step2(item));
                }
            )
            .mapMulti(
                (BiConsumer<String, Consumer<String>>) (item, then) -> {
                    if (!step3(item)) {
                        return;
                    }
                    then.accept(item);
                }
            )
            .mapMulti(
                (BiConsumer<String, Consumer<Long>>) (item, then) -> {
                    final long i = step4(item);
                    then.accept(i);
                }
            )
            .mapToLong(num -> num)
            .sum();
    }

    @Benchmark
    public long fused1() {
        return Stream.of(Big.VALUES)
            .mapMulti(
                (BiConsumer<Object, Consumer<String>>) (item, then) -> {
                    then.accept(step2(step1(item)));
                }
            )
            .mapMulti(
                (BiConsumer<String, Consumer<String>>) (item, then) -> {
                    if (!step3(item)) {
                        return;
                    }
                    then.accept(item);
                }
            )
            .mapMulti(
                (BiConsumer<String, Consumer<Long>>) (item, then) -> {
                    final long i = step4(item);
                    then.accept(i);
                }
            )
            .mapToLong(num -> num)
            .sum();
    }

    @Benchmark
    public long fused2() {
        return Stream.of(Big.VALUES)
            .mapMulti(
                (BiConsumer<Object, Consumer<String>>) (item, then) -> {
                    final String i = step2(step1(item));
                    if (!step3(i)) {
                        return;
                    }
                    then.accept(i);
                }
            )
            .mapMulti(
                (BiConsumer<String, Consumer<Long>>) (item, then) -> {
                    final long i = step4(item);
                    then.accept(i);
                }
            )
            .mapToLong(num -> num)
            .sum();
    }

    @Benchmark
    public long fused3() {
        return Stream.of(Big.VALUES)
            .mapMulti(
                (BiConsumer<Object, Consumer<Long>>) (item, then) -> {
                    final String i1 = step2(step1(item));
                    if (!step3(i1)) {
                        return;
                    }
                    final long i2 = step4(i1);
                    then.accept(i2);
                }
            )
            .mapToLong(num -> num)
            .sum();
    }

    private static String step1(final Object item) {
        return (String) item;
    }

    private static String step2(final String item) {
        return item.trim();
    }

    private static boolean step3(final String item) {
        return item.length() == 4;
    }

    private static long step4(final String item) {
        return Long.parseLong(item, 16) + 1L;
    }

}
