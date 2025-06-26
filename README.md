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
and after it, then comparing the results. Intermediate artifacts are in this
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
at 07:42,
on Linux with 4 CPUs,
in [this GHA run][benchmark-gha]
(the numbers are in milliseconds):

| Test method | Before | After | Diff | Gain |
| --- | --: | --: | --: | --: |
| [`Big.plain`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `43.83` | `38.73` | `-5.10` | `-11%` |
| [`Big.streams`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `134.64` | `127.59` | `-7.05` | `-5%` |
| [`Cotl.cartBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `47.12` | `48.27` | `+1.15` | `+2%` |
| [`Cotl.cartPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `154.10` | `154.82` | `+0.72` | `+0%` |
| [`Cotl.cartSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `349.14` | `342.69` | `-6.44` | `-1%` |
| [`Cotl.refBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `13.74` | `13.60` | `-0.14` | `-1%` |
| [`Cotl.refPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `14.50` | `15.86` | `+1.37` | `+9%` |
| [`Cotl.refSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `24.84` | `24.88` | `+0.04` | `+0%` |
| [`Cotl.sumBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `4.69` | `4.86` | `+0.17` | `+3%` |
| [`Cotl.sumOfSquaresBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `4.76` | `4.98` | `+0.22` | `+4%` |
| [`Cotl.sumOfSquaresEvenBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `7.34` | `7.36` | `+0.01` | `+0%` |
| [`Cotl.sumOfSquaresEvenPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `4.42` | `4.51` | `+0.10` | `+2%` |
| [`Cotl.sumOfSquaresEvenSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `7.71` | `8.02` | `+0.30` | `+3%` |
| [`Cotl.sumOfSquaresPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `2.21` | `2.40` | `+0.20` | `+8%` |
| [`Cotl.sumOfSquaresSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `4.75` | `4.55` | `-0.19` | `-4%` |
| [`Cotl.sumPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `2.24` | `2.25` | `+0.01` | `+0%` |
| [`Cotl.sumSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `4.29` | `4.68` | `+0.39` | `+9%` |
| [`Even.plain`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `60.52` | `52.53` | `-7.99` | `-13%` |
| [`Even.streams`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `70.69` | `68.29` | `-2.40` | `-3%` |
| [`Sum.plain`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `49.50` | `42.14` | `-7.36` | `-14%` |
| [`Sum.streams`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `47.05` | `45.73` | `-1.32` | `-2%` |

The entire test took 331 seconds.
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

[benchmark-gha]: https://github.com/objectionary/benchmark/actions/runs/15895852158
