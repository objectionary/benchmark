/*
 * SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */

public class Loop {

    public long main() {
        final int[] input = { 1, 2, 3 };
        long acc = 0L;
        for (final int idx : input) {
            acc += (long) idx;
        }
        return acc;
    }

}
