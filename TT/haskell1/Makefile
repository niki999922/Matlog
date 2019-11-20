SOURCES=src/haskell/Main.hs src/haskell/Grammar.hs
GEN_SOURCES=src/haskell/Lexer.x src/haskell/Parser.y
GENERATED=src/haskell/Lexer.hs src/haskell/Parser.hs
PACKAGE=haskell1.zip

.PHONY: pack all run

all: parser

run: parser
	./parser

parser: $(GENERATED) $(SOURCES)
	ghc -i./src/haskell -tmpdir . ./src/haskell/Main.hs -o parser

$(GENERATED): $(GEN_SOURCES) $(SOURCES)
	alex src/haskell/Lexer.x -o src/haskell/Lexer.hs
	happy src/haskell/Parser.y -o src/haskell/Parser.hs

pack: $(GENERATED)
	zip $(PACKAGE) -r Makefile src/haskell/