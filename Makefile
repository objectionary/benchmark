# The MIT License (MIT)
#
# Copyright (c) 2023-2024 Objectionary.com
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:

# The above copyright notice and this permission notice shall be included
# in all copies or substantial portions of the Software.

# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.

.ONESHELL:
.SHELLFLAGS := -e -o pipefail -c
SHELL := bash

.PHONY: all clean
.PRECIOUS: %.jar after before
.EXPORT_ALL_VARIABLES:

SHELL=bash

HONE_VERSION=0.0.26

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
