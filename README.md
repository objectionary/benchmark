# Stream Fusion Benchmark

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/objectionary/eo)](http://www.rultor.com/p/objectionary/eo)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![mvn](https://github.com/objectionary/benchmark/actions/workflows/mvn.yml/badge.svg)](https://github.com/objectionary/benchmark/actions/workflows/mvn.yml)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE.txt)

It's a benchmark that puts together:

* [eo-maven-plugin](https://github.com/objectionary/eo)
* [jeo-maven-plugin](https://github.com/objectionary/jeo-maven-plugin)
* [eo-phi-normalizer](https://github.com/objectionary/eo-phi-normalizer)

The goal is to test the performance of Java Stream API before
the "stream fusion" optimization
and after it, then comparing the results. Intermediate artifacts are in this
[`summary.html`](https://www.objectionary.com/benchmark/summary.html).
We partially reuse [the benchmark](https://github.com/biboudis/clashofthelambdas)
earlier suggested by
[Biboudis et al.](https://arxiv.org/abs/1406.6631)
and used by
[Møller et al.](https://dl.acm.org/doi/abs/10.1145/3428236).

<!-- benchmark_begin -->
This is the summary of the tests performed
by openjdk 23.0.1
on 2024-11-06
at 14:18 on Linux with 4 CPUs
in [this GHA run][benchmark-gha]
(the numbers are in milliseconds):

| Test method | Before | After | Diff |
| --- | --: | --: | --: |
 | [`Squares.sum`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `39.33` | `38.97` | `0.36` |
 | [`Squares.sumOfEvenSquares`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `58.48` | `52.28` | `6.20` |
 | [`Squares.sumOfEvenSquaresPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `33.36` | `33.18` | `0.18` |
 | [`Squares.sumOfEvenSquaresSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `67.88` | `68.51` | `-0.63` |
 | [`Squares.sumOfSquares`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `40.12` | `40.64` | `-0.52` |
 | [`Squares.sumOfSquaresPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `20.26` | `20.92` | `-0.66` |
 | [`Squares.sumOfSquaresSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `39.28` | `40.69` | `-1.41` |
 | [`Squares.sumPar`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `19.86` | `21.25` | `-1.40` |
 | [`Squares.sumSeq`](https://github.com/objectionary/benchmark/blob/master/src/main/java/org/eolang/benchmark/Squares.java) | `39.19` | `40.74` | `-1.55` |

The entire test took 1145 seconds.
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

[benchmark-gha]: https://github.com/objectionary/benchmark/actions/runs/11705047269
