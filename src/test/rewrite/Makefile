# SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
# SPDX-License-Identifier: MIT

.ONESHELL:
.SHELLFLAGS := -e -o pipefail -c
SHELL := bash
.SECONDARY:
.PHONY: all clean after back test

RULE_FILES=$(wildcard rules/*.yml)
RULES=$(sort $(subst rules/,,$(RULE_FILES)))
RULE_NAMES=$(subst .yml,,$(RULES))
TEST_FILES=$(wildcard tests/*.phi)
TESTS=$(subst .phi,,$(subst tests/,,$(TEST_FILES)))

JEO_VERSION=0.12.0

all: test run

run: after/Foo.bc after/Foo.out

test: $(TEST_FILES)
	mkdir -p tests-output
	for t in $(TESTS); do
		n=$$(echo "$${t}" | sed 's/-.*$$//g')
		rule=$$(find rules/ -name "$${n}-*.yml")
		phino rewrite --sweet --nothing "tests/$${t}.phi" > /dev/null
		printf "\nTesting \e[1m%s\e[0m against \e[1m%s\e[0m...\n" "$${t}" "$${rule}"
		phino rewrite --sweet --must=1 --rule "$${rule}" "tests/$${t}.phi" > tests-output/$${t}.phi
	done

before/%.class: %.java Makefile
	javac -d "$$(dirname $@)" $<

before/%.xmir: before/%.class Makefile | before
	rm -f $@
	mvn "org.eolang:jeo-maven-plugin:$(JEO_VERSION):disassemble" --quiet "-Djeo.disassemble.sourcesDir=$$(dirname $<)" "-Djeo.disassemble.outputDir=$$(dirname $@)"

before/%.phi: before/%.xmir Makefile
	phino rewrite --sweet --input=xmir --nothing < $< > $@

after/%.phi: before/%.phi Makefile $(RULE_FILES) | after
	phino rewrite --sweet --nothing < $< > $@
	for r in $(RULE_NAMES); do
		n="$$(dirname $@)/$$(echo "$${r}" | sed 's/-.*$$//g').phi"
		phino rewrite --sweet --max-depth=10 --rule "rules/$${r}.yml" < $@ > "$${n}"
	    printf 'Applied \e[1m%s\e[0m rule:\n' "$${r}"
		if diff --color=auto -u $@ "$${n}"; then
			echo "No changes made by $${r}"
		fi
		cp "$${n}" $@
	done

after/%.xmir: after/%.phi Makefile | after
	phino rewrite --nothing --output=xmir < $< > $@

after/%.class: after/%.xmir Makefile | back
	mkdir -p tmp/assemble
	rm -rf tmp/assemble/*
	cp $< tmp/assemble
	mvn "org.eolang:jeo-maven-plugin:$(JEO_VERSION):assemble" --quiet -Djeo.assemble.sourcesDir=tmp/assemble -Djeo.assemble.outputDir=tmp/assemble -Djeo.assemble.skip.verification
	cp tmp/assemble/Foo.class $@

after/%.bc: after/%.class Makefile | after
	javap -c -s -l -constants $< > $@

after/%.out: after/%.class Makefile | after
	mkdir -p tmp/exec
	rm -rf tmp/exec/*
	cp $< tmp/exec/Foo.class
	cd tmp/exec
	java -enableassertions Foo

clean:
	rm -rf tmp/ before/ after/

before:
	mkdir -p before

after:
	mkdir -p after
