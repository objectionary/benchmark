.ONESHELL:
.SHELLFLAGS: -e -o pipefail -c
.PHONY: all clean
.PRECIOUS: %.jar after before

SHELL = bash

TOTAL=10000000000

all: results.md
	./inject-into-readme.pl

results.md: before.time after.time
	echo -e "| | Seconds |\n| --- | --: |\n| Before optimization | $$(cat before.time) |\n| After optimization | $$(cat after.time) |" > results.md

%.time: %.jar
	time=$$({ time -p java -cp $< org.eolang.benchmark.Main ${TOTAL} > /dev/null ; } 2>&1 | head -1 | cut -f2 -d' ')
	echo $${time} > $@

%.jar:
	base=$(basename $@)
	mvn --quiet clean package -DfinalName=$${base} -Ddirectory=$${base}
	cp $${base}/$${base}.jar $${base}.jar

clean:
	rm -f *.time
	rm -f *.jar
	rm -f results.md
	rm -rf before
	rm -rf after
	rm -rf target