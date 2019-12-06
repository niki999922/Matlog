package ru.ifmo.parser.expression.lexer

interface Lexer {
    fun next(): Token
    fun token(): Token
    fun context(): String
    fun tokenDescription(): String
}