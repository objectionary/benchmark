# SPDX-FileCopyrightText: Copyright (c) 2023-2025 Objectionary.com
# SPDX-License-Identifier: MIT

.ONESHELL:
.SHELLFLAGS := -e -o pipefail -c
SHELL := bash
.SECONDARY:
.PHONY: all clean test diff jeo-test run

RULES_DIR=../main/rules
TESTS_DIR=expressions
TARGET=../../target/foo

RULE_FILES=$(wildcard $(RULES_DIR)/*.phr)
RULES=$(sort $(subst $(RULES_DIR)/,,$(RULE_FILES)))
RULE_NAMES=$(subst .phr,,$(RULES))
TEST_FILES=$(wildcard $(TESTS_DIR)/*.phi)
TESTS=$(subst .phi,,$(subst $(TESTS_DIR)/,,$(TEST_FILES)))

JEO_VERSION=0.12.0

all: test jeo-test run

run: $(TARGET)/after/Foo.bc diff

.SILENT:
diff: $(TARGET)/before/Foo.out $(TARGET)/after/Foo.out
	echo "BEFORE:"
	cat $(TARGET)/before/Foo.out
	echo "AFTER:"
	cat $(TARGET)/after/Foo.out
	diff $(TARGET)/before/Foo.out $(TARGET)/after/Foo.out
	echo 'ALL GOOD!'

.SILENT:
jeo-test: $(TARGET)/before/Foo.xmir
	mkdir -p "$(TARGET)/tmp/assemble"
	rm -rf "$(TARGET)/tmp/assemble"/*
	cp $< "$(TARGET)/tmp/assemble"
	mvn --quiet "org.eolang:jeo-maven-plugin:$(JEO_VERSION):assemble" \
		"-Djeo.assemble.sourcesDir=$(TARGET)/tmp/assemble" \
		"-Djeo.assemble.outputDir=$(TARGET)/tmp/assemble" \
		"-Djeo.assemble.skip.verification"

.SILENT:
test: $(TEST_FILES)
	mkdir -p "$(TARGET)/tests-output"
	for t in $(TESTS); do
		n=$$(echo "$${t}" | sed 's/-.*$$//g')
		rule=$$(find "$(RULES_DIR)/" -name "$${n}-*.phr")
		phino rewrite --sweet --nothing "$(TESTS_DIR)/$${t}.phi" > /dev/null
		printf "\nTesting \e[1m%s\e[0m against \e[1m%s\e[0m...\n" "$${t}" "$${rule}"
		phino rewrite --sweet --must=1 --rule "$${rule}" "$(TESTS_DIR)/$${t}.phi" > "$(TARGET)/tests-output/$${t}.phi"
	done

$(TARGET)/before/%.class: %.java Makefile
	javac -d "$$(dirname $@)" $<

$(TARGET)/before/%.xmir: $(TARGET)/before/%.class Makefile
	mvn --quiet "org.eolang:jeo-maven-plugin:$(JEO_VERSION):disassemble" \
		"-Djeo.disassemble.sourcesDir=$$(dirname $<)" \
		"-Djeo.disassemble.outputDir=$$(dirname $@)"

$(TARGET)/before/%.phi: $(TARGET)/before/%.xmir Makefile
	phino rewrite --sweet --input=xmir --nothing < $< > $@

.SILENT:
$(TARGET)/before/%.out: $(TARGET)/before/%.class Makefile
	./exec-java.sh $< $(TARGET)/exec $@

.SILENT:
$(TARGET)/after/%.phi: $(TARGET)/before/%.phi Makefile $(RULE_FILES) | $(TARGET)/after
	phino rewrite --sweet --nothing < $< > $@
	for r in $(RULE_NAMES); do
		n="$$(dirname $@)/$$(echo "$${r}" | sed 's/-.*$$//g').phi"
	    printf 'Applying %s rule...\n' "$${r}"
		phino rewrite --sweet --max-depth=10 --rule "$(RULES_DIR)/$${r}.phr" < $@ > "$${n}"
	    printf 'Applied \e[1m%s\e[0m rule:\n' "$${r}"
		if diff --color=auto -u $@ "$${n}"; then
			echo "No changes made by $${r}"
		fi
		cp "$${n}" $@
	done

$(TARGET)/after/%.xmir: $(TARGET)/after/%.phi Makefile
	phino rewrite --nothing --output=xmir --omit-comments --omit-listing < $< > $@

.SILENT:
$(TARGET)/after/%.class: $(TARGET)/after/%.xmir Makefile
	mkdir -p "$(TARGET)/tmp/assemble"
	rm -rf "$(TARGET)/tmp/assemble"/*
	cp $< "$(TARGET)/tmp/assemble"
	mvn --quiet "org.eolang:jeo-maven-plugin:$(JEO_VERSION):assemble" \
		"-Djeo.assemble.sourcesDir=$(TARGET)/tmp/assemble" \
		"-Djeo.assemble.outputDir=$(TARGET)/tmp/assemble" \
		"-Djeo.assemble.skip.verification"
	cp "$(TARGET)/tmp/assemble/Foo.class" $@

$(TARGET)/after/%.bc: $(TARGET)/after/%.class Makefile
	javap -p -c -s -l -constants $< > $@

.SILENT:
$(TARGET)/after/%.out: $(TARGET)/after/%.class Makefile
	./exec-java.sh $< "$(TARGET)/exec" $@

clean:
	rm -rf "$(TARGET)"

$(TARGET)/before:
	mkdir -p "$(TARGET)/before"

$(TARGET)/after:
	mkdir -p "$(TARGET)/after"
