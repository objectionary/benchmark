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
            .filter(x -> x > 10)
            .map(x -> x + 1)
            .map(x -> x + 1)
            .mapMulti(
                (BiConsumer<Integer, Consumer<Integer>>) (x, consumer) -> {
                    if (bobobo(x)) {
                        consumer.accept(x);
                    }
                }
            )
            .mapToLong(x -> (long) x)
            .sum();
        assert r == 16;
        System.out.printf("result = %d\n", r);
    }

    private static boolean bobobo(Integer i) {
        return i > 10;
    }

}
