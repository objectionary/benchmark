.ONESHELL:
.SHELLFLAGS: -e -o pipefail -c
.PHONY: all

SHELL = bash

TOTAL=1000000000

all: results.md
	./inject-into-readme.pl

results.md: before.time after.time
	echo -e "Before optimization: $$(cat before.time)\nAfter optimization: $$(cat after.time)\n" > results.md

%.time: %.jar
	time=$$({ time -p java -cp $< org.eolang.benchmark.Main ${TOTAL} > /dev/null ; } 2>&1 | head -1 | cut -f2 -d' ')
	echo $${time} > $@

%.jar:
	base=$(basename $@)
	mvn clean package -DfinalName=$${base} -Ddirectory=$${base}
	cp $${base}/$${base}.jar $${base}.jar

