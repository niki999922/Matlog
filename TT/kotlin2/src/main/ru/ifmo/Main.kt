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
        print(tree.printNode())
        return
    }

    tree = NodeWrapper(tree)
    normalizeRoot(tree)

    tree.normalizeNamesLambda(mutableMapOf())
    var redux = tree.getBReduction()
    var counter = 1
    var borderCounter = 1
    var flag = true
    print(tree.printNode())
    while (redux != null) {
        redux.bReduction()
        if (counter == k) {
            print("\n${tree.printNode()}")
            counter = 0
        }
        if (borderCounter == m) {
            if (counter != 0 ) {
                print("\n${tree.printNode()}")
            }
            flag = false
            break
        }
        redux = tree.getBReduction()
        ++counter
        ++borderCounter
    }
    if (flag && counter != 1) {
        print("\n${tree.printNode()}")
    }
}