SOURCES = $(shell find src -type f -name "*.java")
CLASSES = $(patsubst src/%.java,out/%.class,$(SOURCES))
MAINCLASS = EvidenceParser

all: $(CLASSES)

run:
	java -Xms256m -Xmx512m -cp out ${MAINCLASS}

pack:
	zip hw2.zip -r Makefile src

out/%.class: src/%.java out
	javac -cp src $< -d out

out:
	mkdir -p out
