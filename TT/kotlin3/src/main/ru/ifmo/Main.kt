package ru.ifmo

import ru.ifmo.parser.expression.ParserLambdaExpression
import ru.ifmo.parser.expression.values.Variable
import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    var input = BufferedReader(InputStreamReader(System.`in`)).readLines().joinToString("\n")
    val parser = ParserLambdaExpression()
    var tree = parser.parse(input)

    tree.normalizeNamesLambda(mutableMapOf())
    tree.initSystem()


    val result = SystemTypes.solveSystem()
    if (!result) {
        println("Expression has no type")
        return
    }
    tree.printProof(ArrayList(SystemTypes.freeVariables),0)

}