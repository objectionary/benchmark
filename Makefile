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
.SHELLFLAGS: -e -o pipefail -c
.PHONY: all clean
.PRECIOUS: %.jar after before
.EXPORT_ALL_VARIABLES:

SHELL=bash
TOTAL=10000000000

EO_VERSION=0.34.4
JEO_VERSION=0.2.16
OPEO_VERSION=0.0.5

all: results.md src/main/perl/inject-into-readme.pl src/main/perl/create-html-summary.pl
	./src/main/perl/inject-into-readme.pl
	./src/main/perl/create-html-summary.pl

results.md: before.time after.time Makefile
	echo -e "| | Seconds |\n| --- | --: |\n| Before optimization | $$(cat before.time) |\n| After optimization | $$(cat after.time) |" > results.md

%.time: %.jar Makefile
	time=$$({ time -p java -cp $< org.eolang.benchmark.Main "${TOTAL}" > /dev/null ; } 2>&1 | head -1 | cut -f2 -d' ')
	echo "$${time}" > $@

%.jar: pom.xml Makefile
	base=$(basename $@)
	mvn --activate-profiles "$${base}" --update-snapshots clean package "-DfinalName=$${base}" "-Ddirectory=$${base}" -Djeo.version=${JEO_VERSION} -Deo.version=${EO_VERSION}  -Dopeo.version=${OPEO_VERSION}
	cp "$${base}/$${base}.jar" "$${base}.jar"

clean:
	rm -f *.time
	rm -f *.jar
	rm -f results.md
	rm -rf before
	rm -rf after
	rm -rf target