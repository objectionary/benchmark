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
            .map(x -> x + 1)
            .map(Integer::doubleValue)
            .mapToLong(x -> x.longValue())
            .sum();
        System.out.printf("%d\n", r);
    }
}
