# SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
# SPDX-License-Identifier: MIT

.ONESHELL:
.SHELLFLAGS := -e -o pipefail -c
SHELL := bash

.PHONY: all clean test
.PRECIOUS: %.jar after before
.EXPORT_ALL_VARIABLES:

SHELL=bash

HONE_VERSION=0.1.2
JEO_VERSION=0.11.4
EO_VERSION=0.57.1

export

all: env results.md html/summary.html
	set -e

html/summary.html: ./src/main/perl/create-html-summary.pl before.csv after.csv
	mkdir -p html
	./src/main/perl/create-html-summary.pl
	echo "<html><body><a href='summary.html'>summary</a></body></html>" > html/index.html

.SILENT:
env:
	if [[ "$(MAKE_VERSION)" =~ ^[1-3] ]]; then
	    echo "Make must be 4+: $(MAKE_VERSION)"
	    exit 1
	fi

results.md: before.csv after.csv src/main/bash/assemble-results.sh
	src/main/bash/assemble-results.sh > results.md

%.csv: %.jar Makefile
	java -jar $< -rf csv
	mv jmh-result.csv "$@"

%.jar: pom.xml Makefile src/main/java/org/eolang/benchmark/*.java
	set -e
	base=$(basename $@)
	mvn --activate-profiles "$${base}" --update-snapshots clean package \
		"-DfinalName=$${base}" "-Ddirectory=$${base}" \
		-Dhone.version=${HONE_VERSION} -Djeo.version=${JEO_VERSION} -Deo.version=${EO_VERSION}
	cp "$${base}/$${base}.jar" "$${base}.jar"

clean:
	rm -rf *.csv *.jar results.md before after target
