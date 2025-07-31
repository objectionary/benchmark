# Stream Fusion Benchmark

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](https://www.rultor.com/b/objectionary/eo)](https://www.rultor.com/p/objectionary/eo)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![mvn](https://github.com/objectionary/benchmark/actions/workflows/mvn.yml/badge.svg)](https://github.com/objectionary/benchmark/actions/workflows/mvn.yml)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE.txt)

This benchmark combines:

* [eo-maven-plugin](https://github.com/objectionary/eo)
* [jeo-maven-plugin](https://github.com/objectionary/jeo-maven-plugin)
* [phino](https://github.com/objectionary/phino)

The goal is to test the performance of the Java Stream API before
and after the "stream fusion" optimization, and then compare the results.
Intermediate artifacts are available in this
[`summary.html`](https://www.objectionary.com/benchmark/summary.html).
We partially reuse the benchmark suggested by
[Biboudis et al.](https://arxiv.org/abs/1406.6631)
([sources](https://github.com/biboudis/clashofthelambdas))
and used by
[Møller et al.](https://dl.acm.org/doi/abs/10.1145/3428236)
([sources](https://github.com/cs-au-dk/streamliner)).

<!-- benchmark_begin -->
This is the summary of the tests performed
by openjdk 23.0.2,
on 2025-07-28
at 13:21,
on Linux with 4 CPUs,
in [this GHA run][benchmark-gha]
(the numbers are in milliseconds):

| Test method | Before | After | Diff | Gain | Ratio |
| --- | --: | --: | --: | --: | --: |
| [`Big.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `45.57` | `45.73` | `+0.16` |  |  |
| [`Big.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `181.04` | `48.12` | `-132.92` | `+73.4%` | `3.76x` |
| [`Cart.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cart.java) | `31.93` | `31.61` | `-0.33` |  |  |
| [`Cart.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cart.java) | `334.26` | `337.28` | `+3.02` |  |  |
| [`Even.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `71.00` | `66.00` | `-5.00` |  |  |
| [`Even.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `68.59` | `68.31` | `-0.28` |  |  |
| [`Megamorphic.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Megamorphic.java) | `4.01` | `4.13` | `+0.12` |  |  |
| [`Megamorphic.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Megamorphic.java) | `496.93` | `497.00` | `+0.07` |  |  |
| [`Ref.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Ref.java) | `12.19` | `12.24` | `+0.05` |  |  |
| [`Ref.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Ref.java) | `24.63` | `14.98` | `-9.65` | `+39.2%` | `1.64x` |
| [`Squares.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `39.09` | `40.38` | `+1.29` |  |  |
| [`Squares.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `39.36` | `40.24` | `+0.89` |  |  |
| [`Sum.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `37.29` | `38.80` | `+1.51` |  |  |
| [`Sum.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `38.69` | `37.85` | `-0.84` |  |  |

The entire test took 508 seconds.
<!-- benchmark_end -->

Important numbers are in the "Gain" and "Ratio" columns.
Empty cells in the "Gain" column mean that the performance
did not change significantly, while the "Ratio" column
shows how many times the performance improved
after the optimization.

## How to Contribute

Fork the repository, make your changes, and then send us
a [pull request](https://www.yegor256.com/2014/04/15/github-guidelines.html).
We will review your changes and apply them to the `master` branch shortly,
provided they don't violate our quality standards. To avoid frustration,
before sending us your pull request, please run a full build:

```bash
make
```

You will need [Maven 3.3+](https://maven.apache.org) and Java 11+ installed.

[benchmark-gha]: https://github.com/objectionary/benchmark/actions/runs/16569995152
