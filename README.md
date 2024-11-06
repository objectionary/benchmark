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

The goal is to test the performance of Java code before optimization and after,
then comparing the results. Intermediate artifacts are in this 
[`summary.html`](https://www.objectionary.com/benchmark/summary.html).

<!-- benchmark -->
This is the summary of the tests performed with the TOTAL set to 1000000, at 2024-01-30 10:52, on Linux, with 4 CPUs:

| | Before | After | Diff |
| --- | --: | --: | --: |
| Time, msec (with JIT, ×40 cycles) | 2128 | 1435 | -32% |
| Time, msec (no JIT) | 2239 | 948 | -57% |
| Total `.class` files | 2 | 3 | |
| Bytes in all `.class` files | 5593 | 5495 | |
| JAR file size, bytes | 5067 | 5227 | |

This table is updated on every successful run of the [make](https://github.com/objectionary/benchmark/actions/workflows/make.yml) job of GitHub Actions.
The following JDK is used:

```
java 21.0.2 2024-01-16 LTS
Java(TM) SE Runtime Environment (build 21.0.2+13-LTS-58)
Java HotSpot(TM) 64-Bit Server VM (build 21.0.2+13-LTS-58, mixed mode, sharing)
```

<!-- benchmark -->


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
