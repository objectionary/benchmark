/*
 * SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */

import java.util.stream.IntStream;

public class Foo {

    public static void main(String[] args) {
        final int[] input = { 1, 2, 4, 8, 16 };
        final long r = IntStream.of(input)
            .boxed()
            .map(Foo::acceptsDouble)
            .map(String::valueOf)
            .filter(Foo::filterObject)
            .map(Integer::valueOf)
            .map(Foo::acceptsDouble)
            .filter(Foo::filterBoolean)
            .filter(Foo::filterBool)
            .map(x -> x.shortValue())
            .map(Foo::bothPrimitives)
            .map(Double::longValue)
            .filter(x -> x > 5)
            .map(Long::intValue)
            .map(Foo::returnsLong)
            .mapToLong(x -> (long) x)
            .sum();
        System.out.printf("%d\n", r);
    }

    private static long returnsLong(Integer x) {
        return x.longValue();
    }

    private static Integer acceptsDouble(double x) {
        return Double.valueOf(x).intValue();
    }

    private static boolean filterBool(Integer x) {
        return x > 1;
    }

    private static Boolean filterBoolean(long x) {
        return x > 1;
    }

    private static double bothPrimitives(short x) {
        return (double) x;
    }

    private static boolean filterObject(Object x) {
        return !((String) x).isEmpty();
    }
}
