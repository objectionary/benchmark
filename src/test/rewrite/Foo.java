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
            .map(x -> Integer.toString(x))
            .map(x -> Integer.parseInt(x))
            .mapMulti(
                (BiConsumer<Integer, Consumer<String>>) (x, consumer) -> {
                    consumer.accept(Integer.toString(x));
                }
            )
            .map(x -> Integer.parseInt(x))
            .mapToLong(num -> (long) num)
            .sum();
        System.out.printf("result = %d\n", r);
    }

    // private static String first(Integer x) {
    //     return Integer.toString(x) + " hey";
    // }

    // private static void second(Integer x) {
    //     first(x);
    // }

}
