/*
 * SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */

import java.util.stream.IntStream;

public class Foo {

    public long main() {
        final int[] input = { 1, 2, 3 };
        return IntStream.of(input)
            .boxed()
            .map(x -> Integer.toString(x))
            .map(String::trim)
            .map(Integer::parseInt)
            .map(x -> x * x)
            .mapToLong(num -> (long) num)
            .sum();
    }

}
