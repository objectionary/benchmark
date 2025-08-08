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
on 2025-08-08
at 03:33,
on Linux with 4 CPUs,
in [this GHA run][benchmark-gha]
(the numbers are in milliseconds):

| Test method | Before | After | Diff | Gain | Ratio |
| --- | --: | --: | --: | --: | --: |
| [`Big.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `45.78` | `45.79` | `+0.01` |  |  |
| [`Big.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `168.70` | `48.37` | `-120.33` | `+71.3%` | `3.49x` |
| [`Cart.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cart.java) | `31.74` | `31.64` | `-0.11` |  |  |
| [`Cart.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cart.java) | `341.37` | `335.15` | `-6.22` |  |  |
| [`Even.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `65.58` | `64.91` | `-0.67` |  |  |
| [`Even.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `69.50` | `301.11` | `+231.61` | `-333.2%` | `0.23x` |
| [`Megamorphic.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Megamorphic.java) | `5.07` | `4.21` | `-0.86` |  |  |
| [`Megamorphic.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Megamorphic.java) | `497.35` | `54.59` | `-442.76` | `+89.0%` | `9.11x` |
| [`Ref.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Ref.java) | `12.06` | `12.21` | `+0.16` |  |  |
| [`Ref.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Ref.java) | `24.96` | `15.21` | `-9.75` | `+39.1%` | `1.64x` |
| [`Squares.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `40.90` | `40.18` | `-0.72` |  |  |
| [`Squares.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `40.71` | `405.10` | `+364.39` | `-895.2%` | `0.10x` |
| [`Sum.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `44.81` | `38.32` | `-6.49` |  |  |
| [`Sum.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `39.51` | `38.41` | `-1.09` |  |  |

The entire test took 630 seconds.
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

[benchmark-gha]: https://github.com/objectionary/benchmark/actions/runs/16821097667
