# SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
# SPDX-License-Identifier: MIT

.ONESHELL:
.SHELLFLAGS := -e -o pipefail -c
SHELL := bash
.SECONDARY:
.PHONY: all

RULES=$(wildcard *.yml)

all: Foo.back.out

%.phi: %.xmir Makefile
	phino rewrite --sweet --input=xmir --nothing < $< > $@

%.after.phi: %.phi Makefile $(RULES)
	phino rewrite --sweet --nothing < $< > $@
	for r in invokeinterface-to-lambda map-to-mapMulti; do
		cp $@ target/intermediate.phi
		phino rewrite --sweet --rule "$${r}.yml" --log-level=DEBUG < target/intermediate.phi > $@
	done

%.back.phi: %.after.phi Makefile $(RULES)
	phino rewrite --sweet --rule lambda-to-invokeinterface.yml < $< > $@

%.back.xmir: %.back.phi Makefile $(RULES)
	phino rewrite --nothing --output=xmir < $< > $@

%.xmir: %.class Makefile
	mkdir -p target/classes
	mvn org.eolang:jeo-maven-plugin:0.11.3:disassemble --quiet -Djeo.disassemble.sourcesDir=. -Djeo.disassemble.outputDir=.

%.back.class: %.back.xmir Makefile
	mkdir -p target/tmp
	rm -rf target/tmp/*
	cp $< target/tmp
	mvn org.eolang:jeo-maven-plugin:0.11.3:assemble --quiet -Djeo.assemble.sourcesDir=target/tmp -Djeo.assemble.outputDir=target/tmp -Djeo.assemble.skip.verification
	cp target/tmp/Foo.class $@

%.class: %.java Makefile
	javac $<

%.back.out: %.back.class Makefile
	mkdir -p target/exec
	rm -rf target/exec/*
	cp $< target/exec/Foo.class
	cd target/exec
	java Foo

clean:
	rm -rf *.phi *.class *.xmir target/
