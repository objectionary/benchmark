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
at 05:14,
on Linux with 4 CPUs,
in [this GHA run][benchmark-gha]
(the numbers are in milliseconds):

| Test method | Before | After | Diff | Gain | Ratio |
| --- | --: | --: | --: | --: | --: |
| [`Big.plain`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `46.00` | `45.74` | `-0.27` |  |  |
| [`Big.streams`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `170.17` | `47.95` | `-122.22` |  |  |
| [`Cotl.cartBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `45.84` | `46.07` | `+0.23` |  |  |
| [`Cotl.cartPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `152.22` | `148.47` | `-3.75` |  |  |
| [`Cotl.cartSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `345.86` | `351.58` | `+5.72` |  |  |
| [`Cotl.evenBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `7.20` | `7.08` | `-0.12` |  |  |
| [`Cotl.evenPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.94` | `3.81` | `-0.13` |  |  |
| [`Cotl.evenSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `6.88` | `6.89` | `+0.01` |  |  |
| [`Cotl.refBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `12.36` | `12.37` | `+0.00` |  |  |
| [`Cotl.refPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `12.94` | `7.71` | `-5.23` |  |  |
| [`Cotl.refSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `24.71` | `15.51` | `-9.20` |  |  |
| [`Cotl.squaresBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.88` | `4.10` | `+0.22` |  |  |
| [`Cotl.squaresPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `1.76` | `1.93` | `+0.16` |  |  |
| [`Cotl.squaresSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.91` | `3.98` | `+0.07` |  |  |
| [`Cotl.sumBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.81` | `3.96` | `+0.15` |  |  |
| [`Cotl.sumPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `1.89` | `1.87` | `-0.01` |  |  |
| [`Cotl.sumSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.83` | `3.86` | `+0.03` |  |  |

The entire test took 438 seconds.
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

[benchmark-gha]: https://github.com/objectionary/benchmark/actions/runs/16560568363
