# SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
# SPDX-License-Identifier: MIT

.ONESHELL:
.SHELLFLAGS := -e -o pipefail -c
SHELL := bash
.SECONDARY:
.PHONY: all

RULES=$(wildcard *.yml)

all: Foo.after.phi

%.phi: %.xmir Makefile
	phino rewrite --sweet --input=xmir --nothing < $< > $@

%.after.phi: %.phi Makefile $(RULES)
	phino rewrite --sweet --rule invokeinterface-to-lambda.yml < $< > $@

%.back.phi: %.after.phi Makefile $(RULES)
	phino rewrite --sweet --rule lambda-to-invokeinterface.yml < $< > $@

%.xmir: %.class Makefile
	mkdir -p target/classes
	mvn org.eolang:jeo-maven-plugin:0.11.3:disassemble --quiet -Djeo.disassemble.sourcesDir=. -Djeo.disassemble.outputDir=.

%.class: %.java Makefile
	javac $<

clean:
	rm -rf *.phi *.class *.xmir target/
