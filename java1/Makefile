all: build

run:
    java -Xms256m -Xmx512m -cp src EvidenceParser
    
pack:
    zip hw1.zip -r Makefile src lib

clean:
    rm -rf out
    rm -f src/parser/*.java
    rm -f src/proof/*.java
    rm -f src/*.java

build:
    javac -cp src src/matlog/expression/EvidenceParser.java
    
out:
    mkdir -p out