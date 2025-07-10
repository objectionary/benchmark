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
            .map(String::trim)
            .mapMulti(
                (BiConsumer<String, Consumer<Integer>>) (str, consumer) -> {
                    if (str.length() != 1) {
                        return;
                    }
                    consumer.accept(Integer.parseInt(str));
                }
            )
            .map(x -> x * x)
            .mapToLong(num -> (long) num)
            .sum();
        System.out.printf("result = %d\n", r);
    }

}
