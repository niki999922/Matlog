package ru.ifmo.parser.expression.lexer

import java.text.ParseException


class LexerLambdaExpression(private var input: String) : Lexer {
    override fun context() = tokens[tokenPosition].second
    override fun token(): Token = currentToken

    private val tokens = mutableListOf<Pair<Token, String>>()
    private var currentPosition = 0
    private var tokenPosition = 0
    private var context = ""

    private val currentChar: Char
        get() = input[currentPosition]

    private val currentToken: Token
        get() = tokens[tokenPosition].first

    init {
        input = input.trim()
        var token = processTokens()
        while (token != Token.END) {
            tokens.add(token to context)
            context = ""
            token = processTokens()
            tokenPosition++
        }
        tokens.add(Token.END to "")
        tokenPosition = 0
    }

    private fun processTokens(): Token {
        skipWhiteSpace()
        if (currentPosition == input.length) {
            return Token.END
        }
        return when (currentChar) {
            '\\' -> {
                currentPosition++
                Token.LAMBDA
            }
            '.' -> {
                currentPosition++
                Token.DOT
            }
            '(' -> {
                currentPosition++
                Token.LPAREN
            }
            ')' -> {
                currentPosition++
                Token.RPAREN
            }
            else -> {
                if (((currentChar in 'a'..'z') || Character.isDigit(currentChar) || currentChar == '\'' || currentChar == '`' || currentChar == '’')) {
                    var endString = currentPosition
                    while (endString < input.length && ((input[endString] in 'a'..'z') || Character.isDigit(input[endString]) || input[endString] == '\'' || input[endString] == '`' || input[endString] == '’')) {
                        endString++
                    }
                    context = input.substring(currentPosition, endString)
                    currentPosition = endString
                    return Token.VARIABLE
                } else {
                    throw ParseException("Illegal symbol $currentChar", currentPosition)
                }
            }
        }
    }

    private fun skipWhiteSpace() {
        if (currentPosition == input.length) return
        while (currentChar.isWhitespace() || currentChar == '\n' || currentChar == '\t' || currentChar == '\r') {
            currentPosition++
        }
    }

    override fun next(): Token {
        if (tokenPosition == tokens.size) {
            return Token.END
        }
        tokenPosition++
        return currentToken
    }

    override fun prev(): Token {
        tokenPosition--
        return currentToken
    }

    override fun tokenDescription() = "${currentToken.title} in $currentPosition position"
}