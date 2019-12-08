SRC_DIR = src
OUT_DIR = out
SOURCES = $(shell find ${SRC_DIR} -type f -name "*.kt" -not -path "*/test/*")
PACK_OUT_FILE = Kotlin_TT_3.zip
MAINCLASS = ru.ifmo.MainKt

.PHONY: pack

all: compile

compile: ${SOURCES}
	kotlinc -cp ${SRC_DIR} -d ${OUT_DIR} $^

run:
	kotlin -cp .:${OUT_DIR} ${MAINCLASS}

clean:
	rm -rf ${OUT_DIR}

pack:
	rm ${PACK_OUT_FILE} || echo "nothing to delete"
	zip ${PACK_OUT_FILE} -r Makefile src