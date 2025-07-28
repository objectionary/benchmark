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
at 05:01,
on Linux with 4 CPUs,
in [this GHA run][benchmark-gha]
(the numbers are in milliseconds):

| Test method | Before | After | Diff | Gain | Ratio |
| --- | --: | --: | --: | --: | --: |
| [`Big.plain`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `45.63` | `45.77` | `+0.14` | `-0.3%` | `1.00x` |
| [`Big.streams`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `172.49` | `47.93` | `-124.56` | `+72.2%` | `3.60x` |
| [`Cotl.cartBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `46.54` | `46.85` | `+0.31` | `-0.7%` | `0.99x` |
| [`Cotl.cartPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `151.03` | `150.90` | `-0.13` | `+0.1%` | `1.00x` |
| [`Cotl.cartSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `347.23` | `350.46` | `+3.23` | `-0.9%` | `0.99x` |
| [`Cotl.evenBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `6.62` | `6.60` | `-0.02` | `+0.3%` | `1.00x` |
| [`Cotl.evenPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.88` | `3.86` | `-0.02` | `+0.5%` | `1.01x` |
| [`Cotl.evenSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `6.84` | `6.86` | `+0.02` | `-0.4%` | `1.00x` |
| [`Cotl.refBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `12.03` | `12.35` | `+0.32` | `-2.7%` | `0.97x` |
| [`Cotl.refPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `12.75` | `7.32` | `-5.43` | `+42.6%` | `1.74x` |
| [`Cotl.refSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `24.65` | `15.39` | `-9.26` | `+37.6%` | `1.60x` |
| [`Cotl.squaresBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.80` | `3.99` | `+0.19` | `-4.9%` | `0.95x` |
| [`Cotl.squaresPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `1.70` | `1.99` | `+0.29` | `-17.0%` | `0.85x` |
| [`Cotl.squaresSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.83` | `4.02` | `+0.20` | `-5.1%` | `0.95x` |
| [`Cotl.sumBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.70` | `3.90` | `+0.20` | `-5.5%` | `0.95x` |
| [`Cotl.sumPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `1.68` | `1.96` | `+0.28` | `-16.7%` | `0.86x` |
| [`Cotl.sumSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.70` | `4.20` | `+0.50` | `-13.4%` | `0.88x` |

The entire test took 442 seconds.
<!-- benchmark_end -->

Important numbers are in the "Gain" and "Ratio" columns.

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

[benchmark-gha]: https://github.com/objectionary/benchmark/actions/runs/16560391435
