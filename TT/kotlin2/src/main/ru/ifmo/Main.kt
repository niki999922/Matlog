package ru.ifmo

import ru.ifmo.parser.expression.ParserLambdaExpression
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.file.Files
import kotlin.system.exitProcess


fun main() {
//    var input = BufferedReader(InputStreamReader(System.`in`)).readLines().joinToString("\n")
    val parser = ParserLambdaExpression()
//    var tree = parser.parse(input)
//    println(tree.printNode())

    var tree = parser.parse("\\a.\\b.a b c (\\d.e \\f.g) h")
    println("was: \\a.\\b.a b c (\\d.e \\f.g) h")
    println("expected: (\\a.(\\b.((((a b) c) (\\d.(e (\\f.g)))) h)))")
    println("get     : ${tree.printNode()}")

//    var tree = parser.parse("((a\\bbb.c)d)e \nf g")
//    println("was: ((a\\bbb.c)d)e \nf g")
//    println("expected: (((((a (\\bbb.c)) d) e) f) g)")
//    println("get     : ${tree.printNode()}")
}