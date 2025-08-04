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
on 2025-08-04
at 18:59,
on Linux with 4 CPUs,
in [this GHA run][benchmark-gha]
(the numbers are in milliseconds):

| Test method | Before | After | Diff | Gain | Ratio |
| --- | --: | --: | --: | --: | --: |
| [`Big.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `46.18` | `46.79` | `+0.61` |  |  |
| [`Big.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `171.82` | `48.25` | `-123.56` | `+71.9%` | `3.56x` |
| [`Cart.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cart.java) | `31.83` | `31.77` | `-0.06` |  |  |
| [`Cart.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cart.java) | `335.16` | `339.75` | `+4.59` |  |  |
| [`Even.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `66.61` | `69.22` | `+2.60` |  |  |
| [`Even.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `69.35` | `69.29` | `-0.07` |  |  |
| [`Megamorphic.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Megamorphic.java) | `4.14` | `4.36` | `+0.22` |  |  |
| [`Megamorphic.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Megamorphic.java) | `497.48` | `493.25` | `-4.22` |  |  |
| [`Ref.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Ref.java) | `12.19` | `13.09` | `+0.90` |  |  |
| [`Ref.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Ref.java) | `24.92` | `15.68` | `-9.25` | `+37.1%` | `1.59x` |
| [`Squares.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `41.71` | `42.46` | `+0.75` |  |  |
| [`Squares.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `43.23` | `45.24` | `+2.01` |  |  |
| [`Sum.loop`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `41.16` | `41.99` | `+0.83` |  |  |
| [`Sum.stream`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `41.71` | `42.37` | `+0.66` |  |  |

The entire test took 534 seconds.
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

[benchmark-gha]: https://github.com/objectionary/benchmark/actions/runs/16731588307
