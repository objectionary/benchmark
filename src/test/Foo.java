/*
 * SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Foo {

    public static void main(String[] args) {
        final int[] input = { 1, 2, 4, 8, 16 };
        final long r = IntStream.of(input)
            .boxed()
            .map(String::valueOf)
            .map(String::toLowerCase)
            .filter(Foo::isNotEmpty)
            .filter(Foo::justTrue)
            .map(x -> Integer.valueOf(x) + 1)
            .map(Foo::foo)
            .map(Foo::bar)
            .map(Long::intValue)
            .filter(x -> x > 8)
            .map(Foo::retLong)
            .map(Foo::acceptsDouble)
            .map(x -> x + 1)
            .filter(x -> x > 8)
            .mapMulti(
                (BiConsumer<Integer, Consumer<Integer>>) (x, consumer) -> {
                    if (!bobobo(x)) {
                        return;
                    }
                    consumer.accept(x);
                }
            )
            .mapMulti(
                (BiConsumer<Integer, Consumer<Integer>>) (x, consumer) -> {
                    if (bobobo(x)) {
                        consumer.accept(x);
                    }
                }
            )
            .mapToLong(x -> (long) x)
            .sum();
        System.out.printf("%d\n", r);
    }

    private static boolean body(Integer x) {
        return x > 1;
    }

    private static boolean some(double x) {
        return x > 1.0;
    }

    private static Integer acceptsDouble(double x) {
        return Integer.valueOf(Double.valueOf(x).intValue());
    }

    private static long retLong(Integer x) {
        return x.longValue();
    }

    private static long bar(Integer x) {
        return x.longValue();
    }

    private static Integer foo(int x) {
        return x + 1;
    }

    private static boolean bobobo(Integer i) {
        return i > 10;
    }

    private static Boolean justTrue(Object str) {
        return true;
    }

    private static boolean isNotEmpty(Object str) {
        return !((String) str).isEmpty();
    }
}
