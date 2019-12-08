package ru.ifmo.parser.expression

import ru.ifmo.parser.Node
import ru.ifmo.parser.Parse
import ru.ifmo.parser.expression.lexer.Lexer
import ru.ifmo.parser.expression.lexer.LexerLambdaExpression
import ru.ifmo.parser.expression.lexer.Token
import ru.ifmo.parser.expression.operations.Application
import ru.ifmo.parser.expression.operations.Lambda
import ru.ifmo.parser.expression.values.Variable


class ParserLambdaExpression : Parse {
    private lateinit var lexer: Lexer

    override fun parse(input: String): Node {
        lexer = LexerLambdaExpression(input)
        return lambda()
    }

    private fun lambda(): Node {
        return if (lexer.token() == Token.LAMBDA) {
            lexer.next()
            var lambdaTitle = lexer.context()
            lexer.next()
            lexer.next()
            var lambdaBody = lambda()
            Lambda(Variable(lambdaTitle), lambdaBody)
        } else {
            expression()
        }
    }

    private fun expression(): Node {
        var left: Node? = null
        var right: Node? = null
        var ans: Node? = null

        wh@ while (true) {
            when (lexer.token()) {
                Token.LPAREN -> {
                    lexer.next()
                    var expr = lambda()
                    lexer.next()
                    if (left == null) {
                        left = expr
                    } else {
                        right = expr
                        ans = Application(left, right)
                        left = ans
                    }
                }
                Token.VARIABLE -> {
                    var variable = Variable(lexer.context())
                    lexer.next()
                    if (left == null) {
                        left = variable
                    } else {
                        right = variable
                        ans = Application(left, right)
                        left = ans
                    }
                }
                Token.LAMBDA -> {
                    val lambda = lambda()
                    if (left == null) {
                        left = lambda
                    } else {
                        right = lambda
                        ans = Application(left, right)
                        left = ans
                    }
                }
                else -> {
                    break@wh
                }
            }
        }
        if (left != null && right == null) {
            return left
        }
        return ans!!
    }
}