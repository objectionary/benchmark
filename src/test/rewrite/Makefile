# SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
# SPDX-License-Identifier: MIT

.ONESHELL:
.SHELLFLAGS := -e -o pipefail -c
SHELL := bash
.SECONDARY:
.PHONY: all clean after back

RULES=$(sort $(wildcard *.yml))
RULE_NAMES=$(subst .yml,,$(RULES))

JEO_VERSION=0.11.4

all: after/Foo.out

before/%.class: %.java Makefile
	javac -d "$$(dirname $@)" $<

before/%.xmir: before/%.class Makefile | before
	rm -f $@
	mvn "org.eolang:jeo-maven-plugin:$(JEO_VERSION):disassemble" --quiet "-Djeo.disassemble.sourcesDir=$$(dirname $<)" "-Djeo.disassemble.outputDir=$$(dirname $@)"

before/%.phi: before/%.xmir Makefile
	phino rewrite --sweet --input=xmir --nothing < $< > $@

after/%.phi: before/%.phi Makefile $(RULES) | after
	phino rewrite --sweet --nothing < $< > $@
	for r in $(RULE_NAMES); do
		n="$$(dirname $@)/$$(echo "$${r}" | sed 's/-.*$$//g').phi"
		phino rewrite --sweet --rule "$${r}.yml" < $@ > "$${n}"
	    printf 'Applied \e[1m%s\e[0m rule:\n' "$${r}"
		diff --color=auto -u "$${n}" $@ || true
		cp "$${n}" $@
	done

after/%.xmir: after/%.phi Makefile $(RULES) | after
	phino rewrite --nothing --output=xmir < $< > $@

after/%.class: after/%.xmir Makefile | back
	mkdir -p tmp/assemble
	rm -rf tmp/assemble/*
	cp $< tmp/assemble
	mvn "org.eolang:jeo-maven-plugin:$(JEO_VERSION):assemble" --quiet -Djeo.assemble.sourcesDir=tmp/assemble -Djeo.assemble.outputDir=tmp/assemble -Djeo.assemble.skip.verification
	cp tmp/assemble/Foo.class $@

after/%.out: after/%.class Makefile | after
	mkdir -p tmp/exec
	rm -rf tmp/exec/*
	cp $< tmp/exec/Foo.class
	cd tmp/exec
	java Foo

clean:
	rm -rf tmp/ before/ after/

before:
	mkdir -p before

after:
	mkdir -p after
