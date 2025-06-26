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
at 11:55,
on Linux with 4 CPUs,
in [this GHA run][benchmark-gha]
(the numbers are in milliseconds):

| Test method | Before | After | Diff | Gain |
| --- | --: | --: | --: | --: |
| [`Big.plain`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `3.38` | `3.41` | `+0.04` | `+1%` |
| [`Big.streams`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Big.java) | `14.80` | `14.32` | `-0.48` | `-3%` |
| [`Cotl.cartBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `4.80` | `4.67` | `-0.13` | `-2%` |
| [`Cotl.cartPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `17.03` | `22.06` | `+5.03` | `+29%` |
| [`Cotl.cartSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `37.01` | `37.15` | `+0.14` | `+0%` |
| [`Cotl.refBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `1.18` | `1.18` | `-0.00` | `+0%` |
| [`Cotl.refPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `1.36` | `1.35` | `-0.01` | `+0%` |
| [`Cotl.refSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `2.55` | `2.51` | `-0.04` | `-1%` |
| [`Cotl.sumBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `0.32` | `0.31` | `-0.00` | `+0%` |
| [`Cotl.sumOfSquaresBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `0.19` | `0.19` | `-0.00` | `+0%` |
| [`Cotl.sumOfSquaresEvenBaseline`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `0.63` | `0.66` | `+0.03` | `+4%` |
| [`Cotl.sumOfSquaresEvenPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `0.56` | `0.51` | `-0.05` | `-8%` |
| [`Cotl.sumOfSquaresEvenSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `0.69` | `0.69` | `-0.00` | `+0%` |
| [`Cotl.sumOfSquaresPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `0.28` | `0.25` | `-0.03` | `-12%` |
| [`Cotl.sumOfSquaresSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `0.20` | `0.19` | `-0.00` | `+0%` |
| [`Cotl.sumPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `0.26` | `0.30` | `+0.04` | `+15%` |
| [`Cotl.sumSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Cotl.java) | `0.32` | `0.32` | `+0.00` | `+0%` |
| [`Even.plain`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `5.10` | `5.89` | `+0.79` | `+15%` |
| [`Even.streams`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Even.java) | `6.82` | `6.82` | `-0.00` | `+0%` |
| [`Sum.plain`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `39.48` | `38.98` | `-0.50` | `-1%` |
| [`Sum.streams`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Sum.java) | `39.05` | `38.34` | `-0.71` | `-1%` |

The entire test took 232 seconds.
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

[benchmark-gha]: https://github.com/objectionary/benchmark/actions/runs/15901067975
