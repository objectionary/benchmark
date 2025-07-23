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
            .map(x -> x + 1)
            .map(x -> Integer.toString(x))
            .map(x -> x.trim().trim())
            .filter(x -> x.length() > 1)
            .map(x -> Integer.parseInt(x))
            .filter(x -> x > 8)
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

    private static boolean bobobo(Integer i) {
        return i > 10;
    }

}
