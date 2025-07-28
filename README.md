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
[Møller et al.](https://dl.acm.org/doi/abs/10.1145/3428236).

<!-- benchmark_begin -->
This is the summary of the tests performed
by openjdk 23.0.2,
on 2025-07-28
at 06:15,
on Linux with 4 CPUs,
in [this GHA run][benchmark-gha]
(the numbers are in milliseconds):

| Test method | Before | After | Diff | Gain | Ratio |
| --- | --: | --: | --: | --: | --: |
| [`Big.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `45.63` | `45.42` | `-0.22` |  |  |
| [`Big.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `173.63` | `48.05` | `-125.58` | `+72.3%` | `3.61x` |
| [`Cart.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cart.java) | `48.14` | `44.18` | `-3.95` | `+8.2%` | `1.09x` |
| [`Cart.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cart.java) | `339.79` | `339.36` | `-0.43` |  |  |
| [`Even.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `64.72` | `65.53` | `+0.81` |  |  |
| [`Even.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `68.15` | `68.07` | `-0.08` |  |  |
| [`Megamophic.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Megamophic.java) | `3.96` | `3.89` | `-0.07` |  |  |
| [`Megamophic.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Megamophic.java) | `299.29` | `299.08` | `-0.22` |  |  |
| [`Ref.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Ref.java) | `11.91` | `12.09` | `+0.17` |  |  |
| [`Ref.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Ref.java) | `24.67` | `15.29` | `-9.38` | `+38.0%` | `1.61x` |
| [`Squares.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `39.08` | `37.99` | `-1.09` |  |  |
| [`Squares.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `39.47` | `38.37` | `-1.10` |  |  |
| [`Sum.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `38.33` | `37.11` | `-1.22` |  |  |
| [`Sum.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `37.13` | `36.75` | `-0.38` |  |  |

The entire test took 476 seconds.
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

[benchmark-gha]: https://github.com/objectionary/benchmark/actions/runs/16561432402
