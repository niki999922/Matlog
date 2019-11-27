package ru.ifmo.parser.expression

import ru.ifmo.parser.Node
import ru.ifmo.parser.Parse
import ru.ifmo.parser.expression.lexer.Lexer
import ru.ifmo.parser.expression.lexer.LexerLambdaExpression
import ru.ifmo.parser.expression.lexer.Token
import ru.ifmo.parser.expression.operations.Application
import ru.ifmo.parser.expression.operations.Expression
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
            Lambda(lambdaTitle, lambdaBody)
        } else {
            Expression(expression())
        }
    }

    private fun expression(): Node {
        var left: Node? = null
        var right: Node? = null
        var ans: Node = Application(null,null)

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
        return ans
    }
}
//            val token: Token = stream.next()

//            if (token.get() === tokens.L_BRACE) {
//            if (lexer.token() == Token.LPAREN) {
//                lexer.next()
//                var expr = expression()
//                ans.addSon(expression())
//                lexer.next()
//            } else if (lexer.token() === tokens.VARIABLE) {
//                ans.addSon(Node("variable", token.getName()))
//            } else if (token.get() === tokens.LAMBDA) {
//                stream.prev()
//                ans.addSon(expression())
//            } else if (token.get() === tokens.END || token.get() === tokens.R_BRACE) {
//                stream.prev()
//                break
//            }
//        }
//        return ans
//    }


//    private fun buildExpression(): Node {
//        return if (lexer.token() == Token.LAMBDA) {
//            lexer.next()
//            var name = lexer.context()
//            lexer.next()
//            lexer.next()
//            Lambda(name, buildExpression())
//        } else {
//            buildAtom()
//        }
////        var result = buildApplication()
////        while (true) {
////            if (lexer.token() == Token.LAMBDA) {
////                lexer.next()
////                var varExpr = lexer.context()
////                lexer.next()
////                lexer.next()
////                result = Expression(result, Lambda(varExpr, buildApplication()))
////            } else {
////                return result
////            }
////        }
//    }


////    private fun buildApplication(): Node {
////        var result: Node = buildAtom()!!
////        while (true) {
////            when (lexer.token()) {
////                Token.VARIABLE, Token.LPAREN -> {
////                    result = Application(result, buildAtom())
////                }
////                else -> {
////                    return result
////                }
////            }
////        }
////    }

//    private fun buildAtom(): Node {
//        var node: Node? = Application
//        while (true) {
//            when (lexer.token()) {
//                Token.LPAREN -> {
//                    lexer.next()
//                    var e = Expression(buildExpression())
//                    lexer.next()
//                    return e
//                }
//                Token.LAMBDA -> {
//                    lexer.next()
//                    var name = lexer.context()
//                    lexer.next()
//                    lexer.next()
//                    var lambda = Lambda(name, buildExpression())
//                    return lambda
//                }
//                Token.VARIABLE -> {
//                    var v = Variable(lexer.context())
//                    lexer.next()
//                    return v
//                }
//            }
//            }
//        }

//    private fun buildVariable(): Node {
//        when(lexer.token()) {
//            Token.VARIABLE -> {
//                var v =  Variable(lexer.context())
//                lexer.next()
//                return v
//            }
//        }
//    }
//        return when (lexer.token()) {
//            Token.VARIABLE -> {
//                Variable(lexer.context())
//            }
//            Token.LPAREN -> {
//                buildExpression()
//            }
//            else -> {
//                throw Exception("Unexpected token in Atom: ${lexer.tokenDescription()}")
//            }
//        }
//    }
//    }