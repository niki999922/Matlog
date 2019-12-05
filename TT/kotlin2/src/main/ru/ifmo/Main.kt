package ru.ifmo

import ru.ifmo.parser.expression.ParserLambdaExpression
import ru.ifmo.parser.expression.values.NodeWrapper
import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    var (m, k) = readLine()!!.split(" ").map { it.toInt() }
    var input = BufferedReader(InputStreamReader(System.`in`)).readLines().joinToString("\n")
    val parser = ParserLambdaExpression()
    var tree = parser.parse(input)
    if (m == 1) {
        println(tree.printNode())
        return
    }

    tree = NodeWrapper(tree)
//    tree.normalizeLinks(mutableMapOf())
    normalizeRoot(tree)

//    tree.renameLambdaVariables()
    tree.normalizeNamesLambda(mutableMapOf())
//    Node.indexVariable = 0
// (\a.(\b.(\c.(\d.d)c4 )b)a) f

    var redux = tree.getBReduction()
    var counter = 1
    var borderCounter = 1
    var flag = true
    println(tree.printNode())
    while (redux != null) {
        redux.bReduction()
        if (counter == k) {
            println(tree.printNode())
            counter = 0
        }
        if (borderCounter == m) {
            if (counter != 0 ) {
                println(tree.printNode())
            }
            flag = false
            break
        }
        redux = tree.getBReduction()
        ++counter
        ++borderCounter
    }
    if (flag && counter != 1) {
        println(tree.printNode())
    }
}
