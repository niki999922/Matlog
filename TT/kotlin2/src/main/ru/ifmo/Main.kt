package ru.ifmo

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.antlr.v4.runtime.tree.TerminalNode
import ru.ifmo.parser.expression.ParserLambdaExpression
import ru.ifmo.parserAntLr.LambdaExpressionBaseListener
import ru.ifmo.parserAntLr.LambdaExpressionLexer
import ru.ifmo.parserAntLr.LambdaExpressionListener
import ru.ifmo.parserAntLr.LambdaExpressionParser
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.file.Files
import kotlin.system.exitProcess


fun main() {
//    Read(System.`in`).

    var input = BufferedReader(InputStreamReader(System.`in`)).readLines().joinToString("")
    println("test kotlin classes")
    val parser = ParserLambdaExpression()
//    var tree = parser.parse("(a)")
    var tree = parser.parse(input)
    println(tree.printNode())

//    var tree = parser.parse("\\a.\\b.a b c (\\d.e \\f.g) h")
//    println("was: \\a.\\b.a b c (\\d.e \\f.g) h")
//    println("expected: (\\a.(\\b.((((a b) c) (\\d.(e (\\f.g)))) h)))")
//    println("get     : ${tree.printNode()}")

//    var tree = parser.parse("((a\\bbb.c)d)e \nf g")
//    println("was: ((a\\bbb.c)d)e \nf g")
//    println("expected: (((((a (\\bbb.c)) d) e) f) g)")
//    println("get     : ${tree.printNode()}")

//    val lexer = LambdaExpressionLexer(CharStreams.fromString("a a"))
//    val tokens = CommonTokenStream(lexer)
//    val parser = LambdaExpressionParser(tokens)
//    val tree: ParseTree = parser.expression()
//    val walker = ParseTreeWalker()
//    walker.walk(object : LambdaExpressionListener {
//
//        override fun enterExpression(ctx: LambdaExpressionParser.ExpressionContext) {
//            if (ctx.childCount == 2) {
//
//            }
//            print("${ctx.getChild(0)} ${ctx.getChild(1)}")
//            if (ctx.lambda() != null) {
//                return
//            }
//            if (ctx.LB() != null) {
//                return
//            }
//            if (ctx.expression() != null) {
//            }
//        }
//
//        override fun enterEveryRule(p0: ParserRuleContext) {
//        }
//
//        override fun enterLambda(ctx: LambdaExpressionParser.LambdaContext) {
//            print("(\\${ctx!!.WORD()}.")
//        }
//
//        override fun exitLambda(ctx: LambdaExpressionParser.LambdaContext) {
//            print(")")
//        }
//
//        override fun exitEveryRule(p0: ParserRuleContext) {
//        }
//
//        override fun visitErrorNode(p0: ErrorNode) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun visitTerminal(p0: TerminalNode) {
//            println(p0)
//        }
//
//        override fun exitExpression(ctx: LambdaExpressionParser.ExpressionContext) {
//            print(")")
//        }
//    }, tree)
//    var result = parser.parse("(\\a.(\\b.((((a b) c) (\\d.(e (\\f.g)))) h)))")
//    print("${result.printNode()}")
}