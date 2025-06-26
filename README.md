# Stream Fusion Benchmark

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](https://www.rultor.com/b/objectionary/eo)](https://www.rultor.com/p/objectionary/eo)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![mvn](https://github.com/objectionary/benchmark/actions/workflows/mvn.yml/badge.svg)](https://github.com/objectionary/benchmark/actions/workflows/mvn.yml)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE.txt)

It's a benchmark that puts together:

* [eo-maven-plugin](https://github.com/objectionary/eo)
* [jeo-maven-plugin](https://github.com/objectionary/jeo-maven-plugin)
* [phino](https://github.com/objectionary/phino)

The goal is to test the performance of Java Stream API before
the "stream fusion" optimization
and after it, then compare the results. Intermediate artifacts are in this
[`summary.html`](https://www.objectionary.com/benchmark/summary.html).
We partially reuse [the benchmark](https://github.com/biboudis/clashofthelambdas)
earlier suggested by
[Biboudis et al.](https://arxiv.org/abs/1406.6631)
([sources](https://github.com/biboudis/clashofthelambdas))
and used by
[Møller et al.](https://dl.acm.org/doi/abs/10.1145/3428236).

<!-- benchmark_begin -->
This is the summary of the tests performed
by openjdk 23.0.2,
on 2025-06-26
at 08:57,
on Linux with 4 CPUs,
in [this GHA run][benchmark-gha]
(the numbers are in milliseconds):

| Test method | Before | After | Diff | Gain |
| --- | --: | --: | --: | --: |
| [`Big.plain`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `35.17` | `26.32` | `-8.85` | `-25%` |
| [`Big.streams`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `125.45` | `123.82` | `-1.62` | `-1%` |
| [`Cotl.cartBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `46.52` | `49.50` | `+2.98` | `+6%` |
| [`Cotl.cartPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `151.16` | `152.17` | `+1.01` | `+0%` |
| [`Cotl.cartSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `338.82` | `341.00` | `+2.18` | `+0%` |
| [`Cotl.refBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `12.82` | `12.26` | `-0.57` | `-4%` |
| [`Cotl.refPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `14.47` | `14.21` | `-0.27` | `-1%` |
| [`Cotl.refSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `24.92` | `24.64` | `-0.29` | `-1%` |
| [`Cotl.sumBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.77` | `3.91` | `+0.15` | `+3%` |
| [`Cotl.sumOfSquaresBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `4.06` | `4.02` | `-0.03` | `+0%` |
| [`Cotl.sumOfSquaresEvenBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `7.68` | `7.39` | `-0.28` | `-3%` |
| [`Cotl.sumOfSquaresEvenPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `4.44` | `4.49` | `+0.05` | `+1%` |
| [`Cotl.sumOfSquaresEvenSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `7.02` | `7.19` | `+0.17` | `+2%` |
| [`Cotl.sumOfSquaresPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `1.98` | `1.89` | `-0.09` | `-4%` |
| [`Cotl.sumOfSquaresSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `4.07` | `3.97` | `-0.11` | `-2%` |
| [`Cotl.sumPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `1.97` | `1.98` | `+0.01` | `+0%` |
| [`Cotl.sumSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `3.99` | `4.08` | `+0.10` | `+2%` |
| [`Even.plain`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `50.50` | `47.38` | `-3.12` | `-6%` |
| [`Even.streams`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `68.10` | `67.77` | `-0.33` | `+0%` |
| [`Sum.plain`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `39.19` | `38.31` | `-0.87` | `-2%` |
| [`Sum.streams`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `43.51` | `38.97` | `-4.54` | `-10%` |

The entire test took 359 seconds.
<!-- benchmark_end -->

## How to Contribute

Fork repository, make changes, then send us
a [pull request](https://www.yegor256.com/2014/04/15/github-guidelines.html).
We will review your changes and apply them to the `master` branch shortly,
provided they don't violate our quality standards. To avoid frustration,
before sending us your pull request please run full build:

```bash
make
```

You will need [Maven 3.3+](https://maven.apache.org) and Java 11+ installed.

[benchmark-gha]: https://github.com/objectionary/benchmark/actions/runs/15897280264
