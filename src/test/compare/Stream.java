/*
 * SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */

import java.util.stream.IntStream;

public class Stream {

    public long main() {
        final int[] input = { 1, 2, 3 };
        return IntStream.of(input)
            .mapToLong(num -> (long) num)
            .sum();
    }

}
