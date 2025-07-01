# SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
# SPDX-License-Identifier: MIT

.ONESHELL:
.SHELLFLAGS := -e -o pipefail -c
SHELL := bash
.PHONY: all

all: Loop.phi Stream.phi

%.phi: %.xmir Makefile
	phino rewrite --sweet --input=xmir --nothing < $< > $@

%.xmir: %.class Makefile
	mkdir -p target/classes
	mvn org.eolang:jeo-maven-plugin:0.10.0:disassemble -Djeo.disassemble.sourcesDir=. -Djeo.disassemble.outputDir=.

%.class: %.java Makefile
	javac $<

clean:
	rm -rf *.phi *.class *.xmir
