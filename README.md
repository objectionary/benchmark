[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE.txt)

It's a benchmark that puts together:

  * [eo-maven-plugin](https://github.com/objectionary/eo)
  * [jeo-maven-plugin](https://github.com/objectionary/jeo-maven-plugin)
  * [opeo-maven-plugin](https://github.com/objectionary/opeo-maven-plugin)
  * [ineo-maven-plugin](https://github.com/objectionary/ineo-maven-plugin)

The goal is to test the performance of Java code before optimization and after,
then comparing the results.

<!-- benchmark -->
...
<!-- benchmark -->


## How to Contribute

Fork repository, make changes, then send us
a [pull request](https://www.yegor256.com/2014/04/15/github-guidelines.html).
We will review your changes and apply them to the `master` branch shortly,
provided they don't violate our quality standards. To avoid frustration,
before sending us your pull request please run full Maven build:

```bash
$ mvn clean install -Pqulice
```

You will need [Maven 3.3+](https://maven.apache.org) and Java 11+ installed.

