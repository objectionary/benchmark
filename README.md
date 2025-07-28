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
at 05:40,
on Linux with 4 CPUs,
in [this GHA run][benchmark-gha]
(the numbers are in milliseconds):

| Test method | Before | After | Diff | Gain | Ratio |
| --- | --: | --: | --: | --: | --: |
| [`Big.plain`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `45.69` | `45.67` | `-0.02` |  |  |
| [`Big.streams`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `171.61` | `47.95` | `-123.66` | `+72.1%` | `3.58x` |
| [`Cotl.cartBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `45.51` | `45.05` | `-0.46` |  |  |
| [`Cotl.cartPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `150.64` | `149.04` | `-1.60` |  |  |
| [`Cotl.cartSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `350.06` | `346.48` | `-3.58` |  |  |
| [`Cotl.evenBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `7.01` | `6.84` | `-0.17` |  |  |
| [`Cotl.evenPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.76` | `3.83` | `+0.07` |  |  |
| [`Cotl.evenSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `7.02` | `6.90` | `-0.12` |  |  |
| [`Cotl.refBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `12.61` | `12.25` | `-0.36` |  |  |
| [`Cotl.refPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `12.32` | `7.25` | `-5.06` | `+41.1%` | `1.70x` |
| [`Cotl.refSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `24.65` | `15.22` | `-9.44` | `+38.3%` | `1.62x` |
| [`Cotl.squaresBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.91` | `3.89` | `-0.02` |  |  |
| [`Cotl.squaresPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `1.97` | `1.88` | `-0.09` |  |  |
| [`Cotl.squaresSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `4.12` | `4.00` | `-0.11` |  |  |
| [`Cotl.sumBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.78` | `3.83` | `+0.05` |  |  |
| [`Cotl.sumPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `2.29` | `1.72` | `-0.57` | `+24.9%` | `1.33x` |
| [`Cotl.sumSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `4.21` | `3.83` | `-0.38` | `+9.0%` | `1.10x` |

The entire test took 445 seconds.
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

[benchmark-gha]: https://github.com/objectionary/benchmark/actions/runs/16560927150
