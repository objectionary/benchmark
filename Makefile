# SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
# SPDX-License-Identifier: MIT

.ONESHELL:
.SHELLFLAGS := -e -o pipefail -c
SHELL := bash

.PHONY: all clean
.PRECIOUS: %.jar after before
.EXPORT_ALL_VARIABLES:

SHELL=bash

HONE_VERSION=0.0.32

all: env results.md html/summary.html
	set -e

html/summary.html: ./src/main/perl/create-html-summary.pl
	mkdir -p html
	./src/main/perl/create-html-summary.pl
	echo "<html><body><a href='summary.html'>summary</a></body></html>" > html/index.html

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
		-Dhone.version=${HONE_VERSION}
	cp "$${base}/$${base}.jar" "$${base}.jar"

clean:
	set -e
	rm -f *.csv
	rm -f *.jar
	rm -f results.md
	rm -rf before
	rm -rf after
	rm -rf target
