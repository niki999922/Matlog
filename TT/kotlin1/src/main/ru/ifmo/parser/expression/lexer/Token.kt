package ru.ifmo.parser.expression.lexer

enum class Token(val title: String) {
    DOT("."),
    LAMBDA("\\"),
    LPAREN("("),
    RPAREN(")"),
    VARIABLE("variable"),
    END("end")
}