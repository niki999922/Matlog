package ru.ifmo

import ru.ifmo.parser.expression.ParserLambdaExpression
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.file.Files
import kotlin.system.exitProcess


fun main() {
    var input = BufferedReader(InputStreamReader(System.`in`)).readLines().joinToString("\n")
    val parser = ParserLambdaExpression()
    var tree = parser.parse(input)
    println(tree.printNode())
}